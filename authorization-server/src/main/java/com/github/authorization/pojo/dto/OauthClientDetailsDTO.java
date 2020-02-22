package com.github.authorization.pojo.dto;

import com.github.authorization.validator.groupvlidator.ClientDetailsGroupValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "有效期 不能为空", groups = ClientDetailsGroupValidator.class)
    @ApiModelProperty(value = "有效期 单位： 秒,   永久：-1", required = true)
    private Integer validity;




}

