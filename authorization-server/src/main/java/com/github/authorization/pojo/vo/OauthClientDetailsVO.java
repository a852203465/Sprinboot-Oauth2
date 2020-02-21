package com.github.authorization.pojo.vo;

import com.github.authorization.pojo.dto.OauthClientDetailsDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 客户端信息VO 对象
 * @author Rong.Jia
 * @date 2020/02/21 18:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("客户端信息")
public class OauthClientDetailsVO extends OauthClientDetailsDTO implements Serializable {

    private static final long serialVersionUID = -3546065834077567866L;

    /**
     *  主键
     */
    @ApiModelProperty("主键")
    private Long id;



}
