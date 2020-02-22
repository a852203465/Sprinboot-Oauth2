package com.github.authorization.service.impl;

import com.github.authorization.service.CredentialsService;
import com.github.authorization.pojo.vo.CredentialsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户验证实现类
 * @author Rong.Jia
 * @date 2020/02/21 09:18
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CredentialsService credentialsService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        CredentialsVO credentialsVO = credentialsService.findCredentialsByName(username);
        if (credentialsVO == null) {
            throw new UsernameNotFoundException("User '" + username + "' can not be found");
        }

        //此处授权也可以用credentials.getAuthorities(),但是在资源服务器解析的时候要引入自定义的com.oauth2.authorization.userdetails.Authority类否则会报找不到类对象而解析失败
        return new User(credentialsVO.getName(), credentialsVO.getPassword(), credentialsVO.isEnabled(), true, true, true, credentialsVO.getGrantedAuthorities());
    }






}
