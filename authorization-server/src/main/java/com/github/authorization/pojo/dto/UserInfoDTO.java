package com.github.authorization.pojo.dto;

import com.github.authorization.validator.groupvlidator.UserInfoGroupValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户信息 DTO对象
 * @author Rong.Jia
 * @date 2020/02/22 17:16
 */
@Data
@ApiModel("用户信息 参数对象")
public class UserInfoDTO implements Serializable {

    private static final long serialVersionUID = 2265176172967426826L;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名 不能为空", groups = UserInfoGroupValidator.class)
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码 不能为空", groups = UserInfoGroupValidator.class)
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    /**
     *  有效期 单位： 秒
     */
    @NotNull(message = "有效期 不能为空", groups = UserInfoGroupValidator.class)
    @ApiModelProperty(value = "有效期 单位： 秒,   永久：-1", required = true)
    private Integer accessTokenValidity;

    /**
     * 刷新有效期 单位： 秒
     */
    @NotNull(message = "有效期 不能为空", groups = UserInfoGroupValidator.class)
    @ApiModelProperty(value = "有效期 单位： 秒,   永久：-1", required = true)
    private Integer refreshTokenValidity;



}
