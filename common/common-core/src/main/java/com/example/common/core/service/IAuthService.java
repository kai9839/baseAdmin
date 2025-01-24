package com.example.common.core.service;

import com.example.common.core.domain.dto.LoginDTO;
import com.example.common.core.domain.vo.LoginVO;

public interface IAuthService {
    
    /**
     * 登录
     */
    LoginVO login(LoginDTO loginDTO);
    
    /**
     * 登出
     */
    void logout();
} 