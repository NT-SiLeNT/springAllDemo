package com.wx.springBoot.demo01.model.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangxin
 * @Title
 * @Description
 * @date 2019-11-05 15:26
 */
@Data
public class SysLog implements Serializable {
    private Integer id;
    private String username;
    private String operation;
    private Integer time;
    private String method;
    private String params;
    private String ip;
    private Date createTime;
}
