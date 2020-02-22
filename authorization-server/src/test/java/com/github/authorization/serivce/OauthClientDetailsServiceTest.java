package com.github.authorization.serivce;

import com.github.authorization.AuthorizationServerApplicationTests;
import com.github.authorization.entity.OauthClientDetails;
import com.github.authorization.pojo.dto.OauthClientDetailsDTO;
import com.github.authorization.pojo.dto.PageDTO;
import com.github.authorization.pojo.vo.AuthorizationVO;
import com.github.authorization.pojo.vo.OauthClientDetailsVO;
import com.github.authorization.pojo.vo.PageVO;
import com.github.authorization.service.OauthClientDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 认证模式测试类
 *
 * @author Rong.Jia
 * @date 2020/02/21 10:56
 */
class OauthClientDetailsServiceTest extends AuthorizationServerApplicationTests {

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    @Test
    void saveOauthClientDetailsTest(){

        OauthClientDetailsDTO oauthClientDetailsDTO = new OauthClientDetailsDTO();
        oauthClientDetailsDTO.setClientName("client_6");
        oauthClientDetailsDTO.setClientSecret("123456");
        oauthClientDetailsDTO.setValidity(-1);

        oauthClientDetailsService.saveOauthClientDetails(oauthClientDetailsDTO);

    }

    @Test
    void findOauthClientDetailsByClientIdTest(){

        OauthClientDetails oauthClientDetails = oauthClientDetailsService.findOauthClientDetailsByClientId("user_client");

        System.out.println(oauthClientDetails.toString());
    }

    @Test
    void getAuthorizationTest(){

        AuthorizationVO authorizationVO = oauthClientDetailsService.getAuthorizationByClientCredentials(2L);

        System.out.println(authorizationVO.toString());

    }

    @Test
    void findOauthClientDetailsTest(){

        PageDTO pageDTO = new PageDTO();
        pageDTO.setCurrentPage(-1);

        PageVO<OauthClientDetailsVO> pageVO = oauthClientDetailsService.findOauthClientDetails(pageDTO);

        System.out.println(pageVO.toString());


    }


}
