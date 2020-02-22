package com.github.authorization.service.impl;

import com.github.authorization.config.AuthConfig;
import com.github.authorization.pojo.vo.UserInfoVO;
import com.github.authorization.service.UserInfoService;
import com.github.core.encryption.EncryptUtils;
import com.github.core.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 用户验证实现类
 * @author Rong.Jia
 * @date 2020/02/21 09:18
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AuthConfig authConfig;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInfoVO userInfoVO = userInfoService.findUserInfoByUsername(username);
        if (ObjectUtils.isNull(userInfoVO)) {
            throw new UsernameNotFoundException("User '" + username + "' can not be found");
        }

        Boolean state = Boolean.FALSE;
        if (ObjectUtils.equal((byte)1, userInfoVO.getState())) {
            state = Boolean.TRUE;
        }

        String bCrypt = new BCryptPasswordEncoder().encode(EncryptUtils.decodeAES(userInfoVO.getPassword(), authConfig.getEncryptAESKey()));


        //此处授权也可以用credentials.getAuthorities(),但是在资源服务器解析的时候要引入自定义的com.oauth2.authorization.userdetails.Authority类否则会报找不到类对象而解析失败
        return new User(userInfoVO.getUsername(), bCrypt, state, true, true, true, Collections.emptyList());
    }





}
