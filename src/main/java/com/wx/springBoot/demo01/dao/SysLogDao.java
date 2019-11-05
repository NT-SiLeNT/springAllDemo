package com.wx.springBoot.demo01.dao;

import com.wx.springBoot.demo01.model.pojo.SysLog;

/**
 * @author wangxin
 * @Title
 * @Description
 * @date 2019-11-05 16:54
 */
public interface SysLogDao {
    void saveSysLog(SysLog syslog);
}
