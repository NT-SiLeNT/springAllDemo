package com.wx.springBoot.demo01.service.impl;

import com.wx.springBoot.demo01.mapper.StudentMapper;
import com.wx.springBoot.demo01.model.pojo.Student;
import com.wx.springBoot.demo01.service.IStudentService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author wangxin
 * @Title
 * @Description
 * @date 2019-10-29 10:54
 */
@Service
public class StudentServiceImpl implements IStudentService {

    private static final Logger logger = LogManager.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<Student> studenetList() {
        logger.info("进入方法");
        List<Student> students = studentMapper.queryAll();
        logger.info("查询结果条数："+students.size());
        return students;
    }
}
