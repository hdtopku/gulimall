package com.atguigu.gulimall.auth.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

/**
 * @author lxh
 * @createTime 2022-03-29 23:36:09
 */
@Data
public class UserRegisterVo {
    @Length(min = 6, max=18, message="用户名必须是6-18位字符")
    private String username;
    @Length(min = 6, max=18, message="密码必须是6-18位字符")
    private String password;
    @Pattern(regexp = "^[1]([3-9])[0-9]{9}$", message = "手机号格式不正确")
    private String phone;
    @NotBlank(message = "验证码必须填写")
    private String code;
}
