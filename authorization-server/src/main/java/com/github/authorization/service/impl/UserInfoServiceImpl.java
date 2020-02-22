package com.github.authorization.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.authorization.config.AuthConfig;
import com.github.authorization.entity.OauthClientDetails;
import com.github.authorization.entity.UserInfo;
import com.github.authorization.enums.AuthenticationModeEnum;
import com.github.authorization.enums.ResponseEnum;
import com.github.authorization.exception.AuthorizationServerException;
import com.github.authorization.mapper.UserInfoMapper;
import com.github.authorization.pojo.dto.UserInfoDTO;
import com.github.authorization.pojo.vo.UserInfoVO;
import com.github.authorization.service.OauthClientDetailsService;
import com.github.authorization.service.UserInfoService;
import com.github.core.bean.BeanUtils;
import com.github.core.encryption.EncryptUtils;
import com.github.core.lang.Assert;
import com.github.core.lang.Validator;
import com.github.core.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.github.authorization.constants.Oauth2Constant.RESOURCE_IDS;
import static com.github.authorization.constants.Oauth2Constant.SCOPE_ALL;

/**
 * 角色信息业务层实现类
 * @author Rong.Jia
 * @date 2020/02/21 09:53
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private AuthConfig authConfig;

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    @Override
    public UserInfoVO findUserInfoByUsername(String username) {

        Assert.notBlank(username, ResponseEnum.THE_NAME_CANNOT_BE_EMPTY.getMessage());

        UserInfo userInfo = userInfoMapper.findUserInfoByUsername(username);

        return BeanUtils.copyProperties(userInfo, UserInfoVO.class);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean saveUserInfo(UserInfoDTO userInfoDTO) {

        UserInfo userInfo = userInfoMapper.findUserInfoByUsername(userInfoDTO.getUsername());
        if (ObjectUtils.isNotNull(userInfo)) {
            throw new AuthorizationServerException(ResponseEnum.USER_ALREADY_EXISTS);
        }

        userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoDTO, userInfo);
        userInfo.setState((byte)1);
        userInfo.setPassword(EncryptUtils.encodeAES(userInfoDTO.getPassword(), authConfig.getEncryptAESKey()));

        super.save(userInfo);

        OauthClientDetails oauthClientDetails = oauthClientDetailsService.findOauthClientDetailsByClientId(userInfoDTO.getUsername());
        if (Validator.isNotNull(oauthClientDetails)) {
            throw new AuthorizationServerException(ResponseEnum.THE_CLIENT_ALREADY_EXISTS);
        }

        oauthClientDetails = new OauthClientDetails();
        oauthClientDetails.setClientId(userInfoDTO.getUsername());
        oauthClientDetails.setResourceIds(RESOURCE_IDS);
        oauthClientDetails.setClientSecret(EncryptUtils.encodeAES(userInfoDTO.getPassword(), authConfig.getEncryptAESKey()));
        oauthClientDetails.setAuthorizedGrantTypes(AuthenticationModeEnum.getValue(1));
        oauthClientDetails.setScope(SCOPE_ALL);
        oauthClientDetails.setAccessTokenValidity(userInfoDTO.getAccessTokenValidity());
        oauthClientDetails.setRefreshTokenValidity(userInfoDTO.getRefreshTokenValidity());

        return oauthClientDetailsService.save(oauthClientDetails);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean deleteUserInfo(Long id) {

        UserInfo userInfo = super.getById(id);
        if (ObjectUtils.isNull(userInfo)) {
            throw new AuthorizationServerException(ResponseEnum.THE_USER_DOES_NOT_EXIST);
        }

        OauthClientDetails oauthClientDetails = oauthClientDetailsService.findOauthClientDetailsByClientId(userInfo.getUsername());
        if (ObjectUtils.isNotNull(oauthClientDetails)) {
            oauthClientDetailsService.deleteOauthClientDetails(oauthClientDetails.getId());
        }

        return super.removeById(id);

    }

    @Override
    public UserInfoVO findOne(Long userId) {

        Assert.notNull(userId, ResponseEnum.THE_ID_CANNOT_BE_EMPTY.getMessage());

        return BeanUtils.copyProperties(super.getById(userId), UserInfoVO.class);
    }


}
