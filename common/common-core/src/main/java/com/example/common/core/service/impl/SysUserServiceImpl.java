package com.example.common.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.core.domain.entity.SysUser;
import com.example.common.core.mapper.SysUserMapper;
import com.example.common.core.service.ISysUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Collection;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public SysUser getByUsername(String username) {
        return getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, username)
                .eq(SysUser::getDelFlag, "0"));
    }

    @Override
    @Transactional
    public boolean registerUser(SysUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreateTime(new Date());
        user.setDelFlag("0");
        user.setStatus("0");
        return save(user);
    }

    @Override
    public boolean checkUsernameUnique(String username) {
        return count(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, username)
                .eq(SysUser::getDelFlag, "0")) == 0;
    }

    @Override
    public boolean resetUserPwd(String username, String password) {
        return update(new SysUser().setPassword(password),
                new LambdaQueryWrapper<SysUser>()
                        .eq(SysUser::getUsername, username)
                        .eq(SysUser::getDelFlag, "0"));
    }

    @Override
    public boolean removeBatchByIds(Collection<?> idList) {
        SysUser user = new SysUser();
        user.setDelFlag("1");
        user.setUpdateTime(new Date());
        return update(user, new LambdaQueryWrapper<SysUser>()
                .in(SysUser::getUserId, idList));
    }
} 