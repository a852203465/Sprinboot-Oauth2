package com.github.authorization.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.authorization.config.AuthConfig;
import com.github.authorization.config.ServerConfig;
import com.github.authorization.entity.OauthClientDetails;
import com.github.authorization.enums.AuthenticationModeEnum;
import com.github.authorization.enums.ResponseEnum;
import com.github.authorization.enums.URLEnum;
import com.github.authorization.exception.AuthorizationServerException;
import com.github.authorization.mapper.OauthClientDetailsMapper;
import com.github.authorization.pojo.dto.OauthClientDetailsDTO;
import com.github.authorization.pojo.dto.PageDTO;
import com.github.authorization.pojo.vo.*;
import com.github.authorization.service.OauthClientDetailsService;
import com.github.authorization.service.UserInfoService;
import com.github.authorization.utils.RestTemplateUtils;
import com.github.core.codec.Base64;
import com.github.core.collection.CollectionUtils;
import com.github.core.convert.Convert;
import com.github.core.encryption.EncryptUtils;
import com.github.core.lang.Validator;
import com.github.core.utils.CharUtils;
import com.github.core.utils.ObjectUtils;
import com.github.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

import static com.github.authorization.constants.Oauth2Constant.*;

/**
 *认证模式信息业务层 实现类
 * @author Rong.Jia
 * @date 2020/02/21 10:55
 */
@Service
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements OauthClientDetailsService {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private AuthConfig authConfig;

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public OauthClientDetails findOauthClientDetailsByClientId(String clientId) {

        OauthClientDetails where = new OauthClientDetails();
        where.setClientId(clientId);

        QueryWrapper<OauthClientDetails> queryWrapper = new QueryWrapper<>(where);
        return super.getOne(queryWrapper);

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean saveOauthClientDetails(OauthClientDetailsDTO oauthClientDetailsDTO) {

        OauthClientDetails oauthClientDetails = this.findOauthClientDetailsByClientId(oauthClientDetailsDTO.getClientName());
        if (Validator.isNotNull(oauthClientDetails)) {
            throw new AuthorizationServerException(ResponseEnum.THE_CLIENT_ALREADY_EXISTS);
        }

        oauthClientDetails = new OauthClientDetails();
        oauthClientDetails.setClientId(oauthClientDetailsDTO.getClientName());
        oauthClientDetails.setResourceIds(RESOURCE_IDS);
        oauthClientDetails.setClientSecret(EncryptUtils.encodeAES(oauthClientDetailsDTO.getClientSecret(),
                authConfig.getEncryptAESKey()));
        oauthClientDetails.setAuthorizedGrantTypes(AuthenticationModeEnum.getValue(4));
        oauthClientDetails.setScope(SCOPE_ALL);
        oauthClientDetails.setAccessTokenValidity(oauthClientDetailsDTO.getValidity());

        return super.save(oauthClientDetails);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean deleteOauthClientDetails(Long id) {

        OauthClientDetails oauthClientDetails = super.getById(id);

        if (Validator.isNull(oauthClientDetails)) {
            throw new AuthorizationServerException(ResponseEnum.THE_CLIENT_DOES_NOT_E0XIST_OR_IS_DELETED);
        }

        return super.removeById(id);

    }

    @Override
    public PageVO<OauthClientDetailsVO> findOauthClientDetails(PageDTO pageDTO) {

        PageVO<OauthClientDetailsVO> pageVO = new PageVO<>();

        if (pageDTO.getCurrentPage() < 0) {

            List<OauthClientDetails> clientDetailsList = super.list();
            Optional.ofNullable(clientDetailsList).ifPresent(a -> pageVO.setTotal(a.size()));
            pageVO.setRecords(this.objectConversion(clientDetailsList));
        }else {

            Page<OauthClientDetails> page = new Page<>();
            page.setCurrent(pageDTO.getCurrentPage());
            page.setSize(pageDTO.getPageSize());

            IPage<OauthClientDetails> iPage = super.page(page);
            pageVO.setCurrentPage(pageDTO.getCurrentPage());
            pageVO.setPageSize(pageDTO.getPageSize());
            pageVO.setTotal(Convert.toInt(iPage.getTotal()));
            pageVO.setTotalPages(Convert.toInt(iPage.getPages()));
            pageVO.setRecords(this.objectConversion(iPage.getRecords()));
        }

        return pageVO;
    }

    @Override
    public AuthorizationVO getAuthorizationByClientCredentials(Long clientId) {

        OauthClientDetails oauthClientDetails = super.getById(clientId);

        if (Validator.isNull(oauthClientDetails)) {
            throw new AuthorizationServerException(ResponseEnum.THE_CLIENT_DOES_NOT_E0XIST_OR_IS_DELETED);
        }

        String url = serverConfig.getUrl() + URLEnum.OAUTH_TOKEN.getValue();

        Map<String, String> headers = new HashMap<>();

        String decodeAES = EncryptUtils.decodeAES(oauthClientDetails.getClientSecret(), authConfig.getEncryptAESKey());
        String clientSecret = Base64.encode((oauthClientDetails.getClientId() + StringUtils.COLON + decodeAES));
        headers.put(AUTHORIZATION_HEADER, BASIC + clientSecret);

        MultiValueMap<String, String> requstBody = new LinkedMultiValueMap<>();
        requstBody.add("grant_type", oauthClientDetails.getAuthorizedGrantTypes());

        return RestTemplateUtils.post(url, headers, requstBody, AuthorizationVO.class, new Object[]{});
    }

    @Override
    public AuthorizationVO getAuthorizationByPassword(Long userId) {

        UserInfoVO userInfoVO = userInfoService.findOne(userId);

        if (ObjectUtils.isNull(userInfoVO)) {
            throw new AuthorizationServerException(ResponseEnum.THE_USER_DOES_NOT_EXIST);
        }

        OauthClientDetails oauthClientDetails = this.findOauthClientDetailsByClientId(userInfoVO.getUsername());

        String url = serverConfig.getUrl() + URLEnum.OAUTH_TOKEN.getValue();

        Map<String, String> headers = new HashMap<>();

        String decodeAES = EncryptUtils.decodeAES(oauthClientDetails.getClientSecret(), authConfig.getEncryptAESKey());
        String clientSecret = Base64.encode((oauthClientDetails.getClientId() + StringUtils.COLON + decodeAES));
        headers.put(AUTHORIZATION_HEADER, BASIC + clientSecret);

        MultiValueMap<String, String> requstBody = new LinkedMultiValueMap<>();
        requstBody.add("grant_type", StringUtils.split(AuthenticationModeEnum.getValue(1), CharUtils.COMMA).get(0));
        requstBody.add("username", userInfoVO.getUsername());
        requstBody.add("password", EncryptUtils.decodeAES(userInfoVO.getPassword(), authConfig.getEncryptAESKey()));

        return RestTemplateUtils.post(url, headers, requstBody, AuthorizationVO.class);

    }

    @Override
    public List<OauthClientDetailsVO> objectConversion(List<OauthClientDetails> oauthClientDetailsList) {

        if (CollectionUtils.isEmpty(oauthClientDetailsList)) {
            return null;
        }

        List<OauthClientDetailsVO> oauthClientDetailsVoList = new ArrayList<>();
        for (OauthClientDetails oauthClientDetails : oauthClientDetailsList) {
            OauthClientDetailsVO oauthClientDetailsVO = this.objectConversion(oauthClientDetails);
            Optional.ofNullable(oauthClientDetailsVO).ifPresent(oauthClientDetailsVoList::add);
        }

        return oauthClientDetailsVoList;
    }

    @Override
    public OauthClientDetailsVO objectConversion(OauthClientDetails oauthClientDetails) {

        if (ObjectUtils.isNull(oauthClientDetails)) {
            return null;
        }

        OauthClientDetailsVO oauthClientDetailsVO = new OauthClientDetailsVO();
        oauthClientDetailsVO.setId(oauthClientDetails.getId());
        oauthClientDetailsVO.setClientName(oauthClientDetails.getClientId());
        oauthClientDetailsVO.setValidity(oauthClientDetails.getAccessTokenValidity());

        return oauthClientDetailsVO;
    }


}
