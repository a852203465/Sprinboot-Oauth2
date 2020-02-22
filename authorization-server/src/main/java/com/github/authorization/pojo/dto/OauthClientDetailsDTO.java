package com.github.authorization.pojo.dto;

import com.github.authorization.validator.groupvlidator.ClientDetailsGroupValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 客户端认证信息DTO
 * @author Rong.Jia
 * @date 2020/02/21 15:42
 */
@Data
@ApiModel("客户端认证 参数对象")
public class OauthClientDetailsDTO implements Serializable {

    private static final long serialVersionUID = 575032358567257891L;

    /**
     *  客户端名
     */
    @NotBlank(message = "客户端名 不能为空", groups = ClientDetailsGroupValidator.class)
    @ApiModelProperty(value = "客户端名", required = true)
    private String clientName;

    /**
     * 客户端秘钥源
     */
    @NotBlank(message = "客户端秘钥源 不能为空", groups = ClientDetailsGroupValidator.class)
    @ApiModelProperty(value = "客户端秘钥源", required = true)
    private String clientSecret;

    /**
     *  有效期 单位： 秒
     */
    @ApiModelProperty(value = "有效期 单位： 秒,   永久：-1")
    private Integer validity;

    /**
     * 认证模式
     */
    @ApiModelProperty(value = "认证模式 (1：密码模式，2：授权码模式，3：简化模式，4：客户端模式)", required = true)
    @NotBlank(message = "认证模式 不能为空", groups = ClientDetailsGroupValidator.class)
    private Integer mode;



}

