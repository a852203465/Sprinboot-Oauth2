package com.github.authorization.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 授权信息 vo 对象
 * @author Rong.Jia
 * @date 2020/02/21 18:39
 */
@Data
@ApiModel("授权信息")
public class AuthorizationVO implements Serializable {

    private static final long serialVersionUID = -8405341874021540764L;

    /**
     * 授权码
     */
    @ApiModelProperty("授权码")
    private String access_token;

    /**
     * 授权类型
     */
    @ApiModelProperty("授权类型")
    private String token_type;

    @ApiModelProperty(hidden = true)
    private String refresh_token;

    @ApiModelProperty(hidden = true)
    private Integer expires_in;

    @ApiModelProperty(hidden = true)
    private String scope;



}
