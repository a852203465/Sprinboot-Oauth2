package com.github.authorization.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.authorization.entity.Authority;

import java.util.List;

/**
 * 角色信息业务层
 * @author Rong.Jia
 * @date 2020/02/21 09:52
 */
public interface AuthorityService extends IService<Authority> {

    List<Authority> findAuthoritiesByCredentialsId(Long credentialsId);

}
