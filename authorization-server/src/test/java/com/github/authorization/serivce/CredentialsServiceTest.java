package com.github.authorization.serivce;

import com.github.authorization.AuthorizationServerApplicationTests;
import com.github.authorization.service.CredentialsService;
import com.github.authorization.pojo.vo.CredentialsVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 登录凭证测试类
 * @author Rong.Jia
 * @date 2020/02/21 09:56
 */
class CredentialsServiceTest extends AuthorizationServerApplicationTests {

    @Autowired
    private CredentialsService credentialsService;

    @Test
    void findCredentialsByNameTest(){

        CredentialsVO credentialsVO = credentialsService.findCredentialsByName("resource_admin");

        System.out.println(credentialsVO.toString());

    }

}
