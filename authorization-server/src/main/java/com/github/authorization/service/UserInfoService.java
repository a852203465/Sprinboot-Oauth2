package com.github.authorization.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.authorization.entity.UserInfo;
import com.github.authorization.pojo.dto.UserInfoDTO;
import com.github.authorization.pojo.vo.UserInfoVO;

/**
 * 角色信息业务层
 * @author Rong.Jia
 * @date 2020/02/21 09:52
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 用户信息
     */
    UserInfoVO findUserInfoByUsername(String username);

    /**
     * 添加用户
     * @param userInfoDTO 用户信息
     * @return 是否成功
     */
    Boolean saveUserInfo(UserInfoDTO userInfoDTO);

    /**
     * 删除用户
     * @param id 主键
     * @return 是否删除成功
     */
    Boolean deleteUserInfo(Long id);

    /**
     * 查询用户信息
     * @param userId 用户主键
     * @return 用户信息
     */
    UserInfoVO findOne(Long userId);







}
