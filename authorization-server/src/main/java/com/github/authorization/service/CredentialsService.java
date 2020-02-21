package com.github.authorization.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.authorization.entity.Credentials;
import com.github.authorization.vo.CredentialsVO;

/**
 * 登录凭证业务层
 * @author Rong.Jia
 * @date 2020/02/21 09:31
 */
public interface CredentialsService extends IService<Credentials> {

    CredentialsVO findCredentialsByName(String name);





}
