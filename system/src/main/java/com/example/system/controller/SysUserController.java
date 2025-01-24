package com.example.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.core.domain.R;
import com.example.common.core.domain.entity.SysUser;
import com.example.common.core.service.ISysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Tag(name = "用户管理")
@RestController
@RequestMapping("/system/user")
public class SysUserController {

    @Autowired
    private ISysUserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "获取用户列表")
    @GetMapping("/list")
    public R<Page<SysUser>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            SysUser user) {
        
        Page<SysUser> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>()
                .like(user.getUsername() != null, SysUser::getUsername, user.getUsername())
                .eq(SysUser::getDelFlag, "0")
                .orderByDesc(SysUser::getCreateTime);
                
        return R.ok(userService.page(page, wrapper));
    }

    @Operation(summary = "获取用户详细信息")
    @GetMapping("/{userId}")
    public R<SysUser> getInfo(@PathVariable Long userId) {
        return R.ok(userService.getById(userId));
    }

    @Operation(summary = "新增用户")
    @PostMapping
    public R<Void> add(@RequestBody SysUser user) {
        if (!userService.checkUsernameUnique(user.getUsername())) {
            return R.fail("新增用户'" + user.getUsername() + "'失败，登录账号已存在");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.save(user) ? R.ok() : R.fail("新增用户失败");
    }

    @Operation(summary = "修改用户")
    @PutMapping
    public R<Void> edit(@RequestBody SysUser user) {
        user.setPassword(null); // 不允许修改密码
        return userService.updateById(user) ? R.ok() : R.fail("修改用户失败");
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{userIds}")
    public R<Void> remove(@PathVariable Long[] userIds) {
        return userService.removeBatchByIds(Arrays.asList(userIds)) ? R.ok() : R.fail("删除用户失败");
    }

    @Operation(summary = "重置密码")
    @PutMapping("/resetPwd")
    public R<Void> resetPwd(@RequestBody SysUser user) {
        return userService.resetUserPwd(user.getUsername(), 
                passwordEncoder.encode(user.getPassword())) ? R.ok() : R.fail("重置密码失败");
    }
} 