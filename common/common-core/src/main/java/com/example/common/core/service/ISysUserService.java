package com.example.common.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.core.domain.entity.SysUser;

public interface ISysUserService extends IService<SysUser> {
    
    /**
     * 根据用户名查询用户
     */
    SysUser getByUsername(String username);
    
    /**
     * 注册用户信息
     */
    boolean registerUser(SysUser user);
    
    /**
     * 校验用户名称是否唯一
     */
    boolean checkUsernameUnique(String username);
    
    /**
     * 重置用户密码
     */
    boolean resetUserPwd(String username, String password);
} 