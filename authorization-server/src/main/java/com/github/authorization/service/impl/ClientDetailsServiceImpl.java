package com.github.authorization.service.impl;

import com.github.authorization.config.AuthConfig;
import com.github.authorization.entity.OauthClientDetails;
import com.github.authorization.enums.ResponseEnum;
import com.github.authorization.exception.AuthorizationServerException;
import com.github.authorization.service.OauthClientDetailsService;
import com.github.core.collection.CollectionUtils;
import com.github.core.encryption.EncryptUtils;
import com.github.core.lang.Validator;
import com.github.core.utils.ArrayUtils;
import com.github.core.utils.CharUtils;
import com.github.core.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 *  客户端认证
 * @author Rong.Jia
 * @date 2020/02/21 16:41
 */
public class ClientDetailsServiceImpl extends JdbcClientDetailsService {

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    @Autowired
    private AuthConfig authConfig;

    public ClientDetailsServiceImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        OauthClientDetails oauthClientDetails = oauthClientDetailsService.findOauthClientDetailsByClientId(clientId);

        if (Validator.isNull(oauthClientDetails)) {
            throw new AuthorizationServerException(ResponseEnum.THE_CLIENT_DOES_NOT_E0XIST_OR_IS_DELETED);
        }

        BaseClientDetails baseClientDetails = new BaseClientDetails();
        baseClientDetails.setResourceIds(StringUtils.split(oauthClientDetails.getResourceIds(), CharUtils.COMMA));
        baseClientDetails.setClientId(oauthClientDetails.getClientId());
        baseClientDetails.setScope(Collections.singletonList(oauthClientDetails.getScope()));
        String bCrypt = new BCryptPasswordEncoder().encode(EncryptUtils.decodeAES(oauthClientDetails.getClientSecret(), authConfig.getEncryptAESKey()));
        baseClientDetails.setClientSecret(bCrypt);
        baseClientDetails.setAuthorizedGrantTypes(StringUtils.split(oauthClientDetails.getAuthorizedGrantTypes(), CharUtils.COMMA));
        baseClientDetails.setAccessTokenValiditySeconds(oauthClientDetails.getAccessTokenValidity());
        baseClientDetails.setRefreshTokenValiditySeconds(oauthClientDetails.getRefreshTokenValidity());
        baseClientDetails.setRegisteredRedirectUri(new HashSet<>(StringUtils.split(oauthClientDetails.getWebServerRedirectUri(), CharUtils.COMMA)));
        baseClientDetails.setAutoApproveScopes(StringUtils.split(oauthClientDetails.getAutoapprove(), CharUtils.COMMA));

        return baseClientDetails;
    }
}
