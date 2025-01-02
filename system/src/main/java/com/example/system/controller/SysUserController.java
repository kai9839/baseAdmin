package com.example.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.core.domain.R;
import com.example.common.core.domain.dto.UserDTO;
import com.example.common.core.domain.entity.SysUser;
import com.example.common.core.service.ISysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

@Tag(name = "用户管理", description = "用户管理接口")
@RestController
@RequestMapping("/system/user")
public class SysUserController {

    @Resource
    private ISysUserService userService;

    @Resource
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
                .like(user.getPhone() != null, SysUser::getPhone, user.getPhone())
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
    public R<Boolean> add(@Validated @RequestBody UserDTO userDTO) {
        if (!userService.checkUsernameUnique(userDTO.getUsername())) {
            return R.fail("新增用户'" + userDTO.getUsername() + "'失败，登录账号已存在");
        }
        
        SysUser user = new SysUser();
        BeanUtils.copyProperties(userDTO, user);
        return R.ok(userService.registerUser(user));
    }

    @Operation(summary = "修改用户")
    @PutMapping
    public R<Boolean> edit(@Validated @RequestBody UserDTO userDTO) {
        SysUser user = new SysUser();
        BeanUtils.copyProperties(userDTO, user);
        return R.ok(userService.updateUserProfile(user));
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{userIds}")
    public R<Boolean> remove(@PathVariable Long[] userIds) {
        return R.ok(userService.removeBatchByIds(Arrays.asList(userIds)));
    }

    @Operation(summary = "重置密码")
    @PutMapping("/resetPwd")
    public R<Boolean> resetPwd(@RequestBody UserDTO userDTO) {
        return R.ok(userService.resetUserPwd(userDTO.getUsername(), userDTO.getPassword()));
    }

    @Operation(summary = "校验用户名是否唯一")
    @GetMapping("/checkUsername/{username}")
    public R<Boolean> checkUsernameUnique(@PathVariable String username) {
        return R.ok(userService.checkUsernameUnique(username));
    }
} 