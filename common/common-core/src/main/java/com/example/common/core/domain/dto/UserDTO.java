package com.example.common.core.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@Schema(description = "用户信息DTO")
public class UserDTO {
    
    @Schema(description = "用户ID")
    private Long userId;

    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 20, message = "用户名长度必须在2到20个字符之间")
    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @NotBlank(message = "昵称不能为空")
    @Schema(description = "昵称")
    private String nickName;

    @Email(message = "邮箱格式不正确")
    @Schema(description = "邮箱")
    private String email;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "性别 0=男,1=女,2=未知")
    private String sex;

    @Schema(description = "状态 0=正常,1=停用")
    private String status;

    @Schema(description = "备注")
    private String remark;
} 