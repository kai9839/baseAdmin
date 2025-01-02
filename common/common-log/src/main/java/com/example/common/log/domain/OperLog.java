package com.example.common.log.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_oper_log")
public class OperLog {
    
    @TableId
    private Long operId;
    
    /** 操作模块 */
    private String title;
    
    /** 操作类型 */
    private String action;
    
    /** 请求方法 */
    private String method;
    
    /** 请求方式 */
    private String requestMethod;
    
    /** 操作人员 */
    private String operName;
    
    /** 请求url */
    private String operUrl;
    
    /** 操作地址 */
    private String operIp;
    
    /** 请求参数 */
    private String operParam;
    
    /** 返回参数 */
    private String jsonResult;
    
    /** 操作状态（0正常 1异常） */
    private Integer status;
    
    /** 错误消息 */
    private String errorMsg;
    
    /** 操作时间 */
    private Date operTime;
} 