package com.github.authorization.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.authorization.entity.Authority;
import com.github.authorization.entity.Credentials;
import com.github.authorization.mapper.CredentialsMapper;
import com.github.authorization.service.AuthorityService;
import com.github.authorization.service.CredentialsService;
import com.github.authorization.vo.CredentialsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 登录凭证业务层实现类
 * @author Rong.Jia
 * @date 2020/02/21 09:31
 */
@Service
public class CredentialsServiceImpl extends ServiceImpl<CredentialsMapper, Credentials> implements CredentialsService {

    @Autowired
    private AuthorityService authorityService;

    @Override
    public CredentialsVO findCredentialsByName(String name) {

        Credentials where = new Credentials();
        where.setName(name);
        Wrapper<Credentials> queryWrapper =  new QueryWrapper<>(where);

        Credentials credentials = super.getOne(queryWrapper);

        if (credentials == null) {
            return null;
        }

        CredentialsVO credentialsVO = new CredentialsVO();
        BeanUtils.copyProperties(credentials, credentialsVO);

        List<Authority> authorityList = authorityService.findAuthoritiesByCredentialsId(credentials.getId());
        credentialsVO.setAuthorities(authorityList);

        return credentialsVO;
    }
}
