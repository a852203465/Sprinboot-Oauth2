package com.github.authorization.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.authorization.entity.Authority;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 角色信息持久层
 * @author Rong.Jia
 * @date 2020/02/21 09:43
 */
public interface AuthorityMapper extends BaseMapper<Authority> {

    /**
     *  根据登录凭证查询角色
     * @param credentialsId  登录凭证id
     * @author Rong.Jia
     * @date 2020/02/21 09:43
     * @return 角色信息
     */
    @Select("select a.* from authority a left join credentials_authorities ca on a.id = ca.authorities_id where ca.credentials_id = #{credentialsId} ")
    List<Authority> findAuthoritiesByCredentialsId(Long credentialsId);

}
