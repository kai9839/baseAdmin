package com.example.common.core.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户信息")
public class UserVO {
    
    @Schema(description = "用户ID")
    private Long userId;
    
    @Schema(description = "用户名")
    private String username;
    
    @Schema(description = "头像")
    private String avatar;
    
    @Schema(description = "角色列表")
    private String[] roles;
    
    @Schema(description = "权限列表")
    private String[] permissions;
} 