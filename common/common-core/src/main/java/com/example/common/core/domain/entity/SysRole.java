package com.example.common.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_role")
public class SysRole {
    
    @TableId
    private Long roleId;
    
    private String roleName;
    
    private String roleKey;
    
    private Integer roleSort;
    
    private String status;
    
    private String delFlag;
    
    private String createBy;
    
    private Date createTime;
    
    private String updateBy;
    
    private Date updateTime;
    
    private String remark;
} 