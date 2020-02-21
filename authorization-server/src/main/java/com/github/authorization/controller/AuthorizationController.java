package com.github.authorization.controller;

import com.github.authorization.enums.ResponseEnum;
import com.github.authorization.pojo.dto.OauthClientDetailsDTO;
import com.github.authorization.pojo.dto.PageDTO;
import com.github.authorization.pojo.vo.AuthorizationVO;
import com.github.authorization.pojo.vo.OauthClientDetailsVO;
import com.github.authorization.pojo.vo.PageVO;
import com.github.authorization.service.OauthClientDetailsService;
import com.github.authorization.validator.groupvlidator.ClientDetailsGroupValidator;
import com.github.authorization.vo.ResponseVO;
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
import java.util.List;

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

    @ApiOperation("获取客户端授权")
    @GetMapping(value = "clientDetails/{clientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "clientId", dataType = "Long", value = "主键", required = true)
    })
    public ResponseVO<AuthorizationVO> findClientDetails (@PathVariable("clientId")
                                           @NotNull(message = "主键 不能为空", groups = ClientDetailsGroupValidator.class) Long clientId) {

        log.info("findClientDetails {}", clientId);

        Assert.notNull(clientId, ResponseEnum.THE_ID_CANNOT_BE_EMPTY.getMessage());

        AuthorizationVO authorizationVO = oauthClientDetailsService.getAuthorization(clientId);

        return ResponseVO.success(authorizationVO);

    }

}
