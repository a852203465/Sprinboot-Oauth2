package com.github.authorization.controller;

import com.github.authorization.enums.ResponseEnum;
import com.github.authorization.pojo.dto.OauthClientDetailsDTO;
import com.github.authorization.pojo.dto.PageDTO;
import com.github.authorization.pojo.vo.AuthorizationVO;
import com.github.authorization.pojo.vo.OauthClientDetailsVO;
import com.github.authorization.pojo.vo.PageVO;
import com.github.authorization.service.OauthClientDetailsService;
import com.github.authorization.validator.groupvlidator.ClientDetailsGroupValidator;
import com.github.authorization.pojo.vo.ResponseVO;
import com.github.core.lang.Assert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * 监控基础平台WEB
 * @author Rong.Jia
 * @date 2020/02/17 14:30
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/web/")
@Api(value = "认证服务器", tags = "认证服务器 controller，对接页面")
public class AuthorizationController {

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    @ApiOperation("获取客户端授权信息")
    @GetMapping(value = "clientDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO<PageVO<OauthClientDetailsVO>> findClientDetails(PageDTO pageDTO) {

        log.info("findClientDetails {}", pageDTO.toString());

        PageVO<OauthClientDetailsVO> pageVO = oauthClientDetailsService.findOauthClientDetails(pageDTO);

        return ResponseVO.success(pageVO);
    }

    @ApiOperation("添加客户端授权信息")
    @PostMapping(value = "clientDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO saveClientDetails (@RequestBody @Validated(ClientDetailsGroupValidator.class) OauthClientDetailsDTO oauthClientDetailsDTO) {

        log.info("saveClientDetails {}", oauthClientDetailsDTO.toString());

        oauthClientDetailsService.saveOauthClientDetails(oauthClientDetailsDTO);

        return ResponseVO.success();
    }

    @ApiOperation("删除客户端授权信息")
    @DeleteMapping(value = "clientDetails/{clientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "clientId", dataType = "Long", value = "主键", required = true)
    })
    public ResponseVO deleteClientDetails (@PathVariable("clientId")
                                  @NotNull(message = "主键 不能为空", groups = ClientDetailsGroupValidator.class) Long clientId) {

        log.info("deleteClientDetails {}", clientId);

        Assert.notNull(clientId, ResponseEnum.THE_ID_CANNOT_BE_EMPTY.getMessage());

        oauthClientDetailsService.deleteOauthClientDetails(clientId);

        return ResponseVO.success();

    }

    @ApiOperation("获取客户端授权, 客户端模式")
    @GetMapping(value = "clientDetails/{clientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "clientId", dataType = "Long", value = "主键", required = true)
    })
    public ResponseVO<AuthorizationVO> getAuthorizationByClientCredentials (@PathVariable("clientId")
                                           @NotNull(message = "主键 不能为空", groups = ClientDetailsGroupValidator.class) Long clientId) {

        log.info("getAuthorizationByClientCredentials {}", clientId);

        Assert.notNull(clientId, ResponseEnum.THE_ID_CANNOT_BE_EMPTY.getMessage());

        AuthorizationVO authorizationVO = oauthClientDetailsService.getAuthorizationByClientCredentials(clientId);

        return ResponseVO.success(authorizationVO);

    }

    @ApiOperation("获取客户端授权, 密码模式")
    @GetMapping(value = "password/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "userId", dataType = "Long", value = "用户ID", required = true)
    })
    public ResponseVO<AuthorizationVO> getAuthorizationByPassword (@PathVariable("userId")
                                                                            @NotNull(message = "用户ID 不能为空", groups = ClientDetailsGroupValidator.class) Long userId) {

        log.info("getAuthorizationByPassword {}", userId);

        Assert.notNull(userId, ResponseEnum.THE_ID_CANNOT_BE_EMPTY.getMessage());

        AuthorizationVO authorizationVO = oauthClientDetailsService.getAuthorizationByPassword(userId);

        return ResponseVO.success(authorizationVO);

    }

}
