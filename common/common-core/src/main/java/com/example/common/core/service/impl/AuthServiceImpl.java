package com.example.common.core.service.impl;

import com.example.common.core.domain.dto.LoginDTO;
import com.example.common.core.domain.entity.SysUser;
import com.example.common.core.domain.vo.LoginVO;
import com.example.common.core.domain.vo.UserVO;
import com.example.common.core.exception.user.UserException;
import com.example.common.core.service.IAuthService;
import com.example.common.core.service.ISysUserService;
import com.example.common.security.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private ISysUserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        // 根据用户名查询用户
        SysUser user = userService.getByUsername(loginDTO.getUsername());
        if (user == null) {
            throw new UserException("用户名或密码错误");
        }
        
        // 校验密码
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new UserException("用户名或密码错误");
        }
        
        // 校验用户状态
        if ("1".equals(user.getStatus())) {
            throw new UserException("账号已停用");
        }
        
        // 生成token
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        String token = jwtUtils.generateToken(claims);
        
        // 构建返回结果
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        
        UserVO userVO = new UserVO();
        userVO.setUserId(user.getUserId());
        userVO.setUsername(user.getUsername());
        userVO.setAvatar(user.getAvatar());
        // TODO: 设置角色和权限
        
        loginVO.setUser(userVO);
        return loginVO;
    }

    @Override
    public void logout() {
        // TODO: 清除token等
    }
} 