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
     * 成功 token
     */
    @ApiModelProperty("成功 token")
    private String access_token;

    /**
     * 授权类型
     */
    @ApiModelProperty("授权类型")
    private String token_type;

    /**
     * 刷新token
     */
    @ApiModelProperty("刷新token")
    private String refresh_token;

    /**
     * 有效时间
     */
    @ApiModelProperty("有效时间")
    private Integer expires_in;

    /**
     * 操作
     */
    @ApiModelProperty("操作")
    private String scope;



}
