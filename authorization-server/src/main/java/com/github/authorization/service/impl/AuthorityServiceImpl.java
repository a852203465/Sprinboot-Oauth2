package com.github.authorization.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.authorization.entity.Authority;
import com.github.authorization.mapper.AuthorityMapper;
import com.github.authorization.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色信息业务层实现类
 * @author Rong.Jia
 * @date 2020/02/21 09:53
 */
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements AuthorityService {

    @Autowired
    private AuthorityMapper authorityMapper;

    @Override
    public List<Authority> findAuthoritiesByCredentialsId(Long credentialsId) {

        return authorityMapper.findAuthoritiesByCredentialsId(credentialsId);
    }
}
