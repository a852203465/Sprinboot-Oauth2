package com.github.authorization.config;

import com.github.authorization.service.impl.ClientDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 授权服务器配置
 *
 * @author Rong.Jia
 * @date 2020/02/21 09:13
 */
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    public ClientDetailsService clientDetails() {
        return new ClientDetailsServiceImpl(dataSource);
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }


    /**
     * 配置 token 节点的安全策略
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()");  // 获取 token 的策略
        security.checkTokenAccess("isAuthenticated()");
    }

    /**
     * 配置客户端信息
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        /*
        默认值InMemoryTokenStore对于单个服务器是完全正常的
        （即，在发生故障的情况下，低流量和热备份备份服务器）。
        大多数项目可以从这里开始，也可以在开发模式下运行，
        以便轻松启动没有依赖关系的服务器。

        这JdbcTokenStore是同一件事的JDBC版本，
        它将令牌数据存储在关系数据库中。如果您可以在服务器之间共享数据库，
        则可以使用JDBC版本，如果只有一个，则扩展同一服务器的实例，
        或者如果有多个组件，则授权和资源服务器。要使用JdbcTokenStore你需要“spring-jdbc”的类路径。
        存储在oauth_client_details表
         */

        clients.withClientDetails(clientDetails());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager) // 开启密码验证，来源于 WebSecurityConfigurerAdapter
                .userDetailsService(userDetailsService) // 读取验证用户的信息
                //.accessTokenConverter(accessTokenConverter())//jwt
                //从数据库查请求的路径
                 .tokenStore(tokenStore());
                //从jwt来数据
                //.tokenStore(jwtStore());

    }

    /**
     * 定义jwttoken的某些属性
     * @return
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {

        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter() {

            /**
             * 重写增强token的方法
             * 自定义返回相应的信息
             *
             */
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

                String userName = authentication.getUserAuthentication().getName();

                // 与登录时候放进去的UserDetail实现类一直查看link{SecurityConfiguration}
                User user = (User) authentication.getUserAuthentication().getPrincipal();

                //自定义一些token属性
                final Map<String, Object> additionalInformation = new HashMap<>();
                additionalInformation.put("username", userName);
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
                return super.enhance(accessToken, authentication);
            }

        };
        // 测试用,资源服务使用相同的字符达到一个对称加密的效果,生产时候使用RSA非对称加密方式
       // accessTokenConverter.setSigningKey("123");
        return accessTokenConverter;

    }

    @Bean
    public TokenStore jwtStore() {

        return new JwtTokenStore(accessTokenConverter());
    }

    /**
     * 创建一个默认的资源服务token
     *
     * @return
     */
    @Bean
    public ResourceServerTokenServices defaultTokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenEnhancer(accessTokenConverter());
        defaultTokenServices.setTokenStore(jwtStore());
        return defaultTokenServices;
    }
}
