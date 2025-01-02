package com.example.common.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_user")
public class SysUser {
    
    @TableId
    private Long userId;
    
    private String username;
    
    private String password;
    
    private String nickName;
    
    private String email;
    
    private String phone;
    
    private String sex;
    
    private String avatar;
    
    private String status;
    
    private String delFlag;
    
    private String loginIp;
    
    private Date loginDate;
    
    private String createBy;
    
    private Date createTime;
    
    private String updateBy;
    
    private Date updateTime;
    
    private String remark;
} 