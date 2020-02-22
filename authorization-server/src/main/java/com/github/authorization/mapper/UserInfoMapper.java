package com.github.authorization.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.authorization.entity.UserInfo;

public interface UserInfoMapper extends BaseMapper<UserInfo> {

    int deleteByPrimaryKey(Long id);

    @Override
    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    UserInfo findUserInfoByUsername(String username);




}