package com.github.authorization.service.impl;

import com.github.authorization.config.AuthConfig;
import com.github.authorization.entity.OauthClientDetails;
import com.github.authorization.enums.ResponseEnum;
import com.github.authorization.exception.AuthorizationServerException;
import com.github.authorization.service.OauthClientDetailsService;
import com.github.core.encryption.EncryptUtils;
import com.github.core.lang.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;
import java.util.Collections;

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
        BeanUtils.copyProperties(oauthClientDetails, baseClientDetails);
        baseClientDetails.setScope(Collections.singletonList(oauthClientDetails.getScope()));
        baseClientDetails.setAuthorizedGrantTypes(Collections.singletonList(oauthClientDetails.getAuthorizedGrantTypes()));
        baseClientDetails.setAccessTokenValiditySeconds(oauthClientDetails.getAccessTokenValidity());
        baseClientDetails.setRefreshTokenValiditySeconds(oauthClientDetails.getRefreshTokenValidity());

        String bCrypt = new BCryptPasswordEncoder().encode(EncryptUtils.decodeAES(oauthClientDetails.getClientSecret(), authConfig.getEncryptAESKey()));
        baseClientDetails.setClientSecret(bCrypt);

        return baseClientDetails;
    }
}
