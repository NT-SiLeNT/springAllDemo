package com.wx.springBoot.demo01.aspect;

import com.wx.springBoot.demo01.common.util.HttpContextUtils;
import com.wx.springBoot.demo01.common.util.IPUtils;
import com.wx.springBoot.demo01.dao.SysLogDao;
import com.wx.springBoot.demo01.model.pojo.SysLog;
import com.wx.springBoot.demo01.common.annotation.Log;
import com.wx.springBoot.demo01.service.impl.StudentServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author wangxin
 * @Title
 * @Description
 * @date 2019-11-05 17:20
 */
//切面
@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LogManager.getLogger(StudentServiceImpl.class);

    @Autowired
    private SysLogDao sysLogDao;

    /**
     * 切点
     */
    @Pointcut("@annotation(com.wx.springBoot.demo01.common.annotation.Log)")
    public void pointcut() {
        logger.info("进入方法pointcut");
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) {
        logger.info("进入方法around");
        Object result = null;
        long beginTime = System.currentTimeMillis();
        try {
            // 执行方法
            result = point.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        // 保存日志
        saveLog(point, time);
        return result;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog sysLog = new SysLog();
        Log logAnnotation = method.getAnnotation(Log.class);
        if (logAnnotation != null) {
            // 注解上的描述
            sysLog.setOperation(logAnnotation.value());
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        // 请求的方法参数值
        Object[] args = joinPoint.getArgs();
        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            String params = "";
            for (int i = 0; i < args.length; i++) {
                params += "  " + paramNames[i] + ": " + args[i];
            }
            sysLog.setParams(params);
        }
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));
        // 模拟一个用户名
        sysLog.setUsername("mrbird");
        sysLog.setTime((int) time);
        sysLog.setCreateTime(new Date());
        // 保存系统日志
        sysLogDao.saveSysLog(sysLog);
    }
}
