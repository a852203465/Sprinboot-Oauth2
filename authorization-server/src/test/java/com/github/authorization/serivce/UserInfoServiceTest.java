package com.github.authorization.serivce;

import com.github.authorization.AuthorizationServerApplicationTests;
import com.github.authorization.pojo.dto.UserInfoDTO;
import com.github.authorization.service.UserInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户信息测试类
 * @author Rong.Jia
 * @date 2020/02/22 17:43
 */
class UserInfoServiceTest extends AuthorizationServerApplicationTests {

    @Autowired
    private UserInfoService userInfoService;

    @Test
    void saveUserInfoTest(){

        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setPassword("123456");
        userInfoDTO.setUsername("admin");
        userInfoDTO.setAccessTokenValidity(1000);
        userInfoDTO.setRefreshTokenValidity(2000);

        Boolean aBoolean = userInfoService.saveUserInfo(userInfoDTO);

        System.out.println(aBoolean);

    }

    @Test
    void deleteUserInfoTest(){

        userInfoService.deleteUserInfo(2L);


    }



}
