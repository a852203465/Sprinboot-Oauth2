package com.github.authorization.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息vo  对象
 * @author Rong.Jia
 * @date 2020/02/22 17:09
 */
@Data
@ApiModel("用户信息")
public class UserInfoVO implements Serializable {

    private static final long serialVersionUID = -8278602445429070467L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 状态, 1:正常，0：删除
     */
    @ApiModelProperty("状态, 1:正常，0：删除")
    private Byte state;
}
