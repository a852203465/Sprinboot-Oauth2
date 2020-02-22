package com.github.authorization.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.authorization.entity.OauthClientDetails;
import com.github.authorization.pojo.dto.OauthClientDetailsDTO;
import com.github.authorization.pojo.dto.PageDTO;
import com.github.authorization.pojo.vo.AuthorizationVO;
import com.github.authorization.pojo.vo.OauthClientDetailsVO;
import com.github.authorization.pojo.vo.PageVO;

import java.util.List;

/**
 * 认证模式信息业务层
 * @author Rong.Jia
 * @date 2020/02/21 10:52
 */
public interface OauthClientDetailsService extends IService<OauthClientDetails> {

    /**
     * 根据客户端Id 查询认证模式信息
     * @param clientId 客户端Id
     * @return 认证模式信息
     */
    OauthClientDetails findOauthClientDetailsByClientId(String clientId);

    /**
     *  添加客户端
     * @param oauthClientDetailsDTO 客户端
     * @date 2020/02/21
     * @return 是否成功
     */
    Boolean saveOauthClientDetails(OauthClientDetailsDTO oauthClientDetailsDTO);

    /**
     * 删除客户端信息
     * @date 2020/02/21
     * @param id 客户端信息主键
     * @return 是否成功
     */
    Boolean deleteOauthClientDetails(Long id);

    /**
     *  查询客户端信息
     * @param pageDTO 查询条件
     * @date 2020/02/21
     * @return  客户端信息
     */
    PageVO<OauthClientDetailsVO> findOauthClientDetails(PageDTO pageDTO);

    /**
     *  根据客户端id 获取授权  客户端模式
     * @param clientId 客户端id
     * @date 2020/02/21
     * @return 授权信息
     */
    AuthorizationVO getAuthorizationByClientCredentials(Long clientId);

    /**
     *  根据用户id 获取授权  密码模式
     * @param userId 用户id
     * @date 2020/02/22
     * @return 授权信息
     */
    AuthorizationVO getAuthorizationByPassword (Long userId);

    /**
     * 对象转换
     * @param oauthClientDetailsList 客户端信息do 对象集合
     * @date 2020/01/07 15:26
     * @author Rong.Jia
     * @return 客户端信息vo 对象集合
     */
    List<OauthClientDetailsVO> objectConversion(List<OauthClientDetails> oauthClientDetailsList);

    /**
     * 对象转换
     * @param oauthClientDetails 客户端信息do 对象
     * @date 2020/01/07 15:26
     * @author Rong.Jia
     * @return 客户端信息vo 对象
     */
    OauthClientDetailsVO objectConversion(OauthClientDetails oauthClientDetails);





}
