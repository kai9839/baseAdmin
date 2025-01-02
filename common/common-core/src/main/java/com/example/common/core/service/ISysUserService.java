package com.example.common.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.core.domain.entity.SysUser;

public interface ISysUserService extends IService<SysUser> {
    
    /**
     * 通过用户名查询用户
     */
    SysUser selectUserByUsername(String username);
    
    /**
     * 注册用户信息
     */
    boolean registerUser(SysUser user);
    
    /**
     * 校验用户名称是否唯一
     */
    boolean checkUsernameUnique(String username);
    
    /**
     * 校验手机号码是否唯一
     */
    boolean checkPhoneUnique(SysUser user);
    
    /**
     * 校验email是否唯一
     */
    boolean checkEmailUnique(SysUser user);
    
    /**
     * 修改用户基本信息
     */
    boolean updateUserProfile(SysUser user);
    
    /**
     * 重置用户密码
     */
    boolean resetUserPwd(String username, String password);
} 