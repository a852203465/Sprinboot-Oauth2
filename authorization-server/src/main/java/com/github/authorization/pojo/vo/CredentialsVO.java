package com.github.authorization.pojo.vo;

import com.github.authorization.entity.Authority;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录凭证vo 信息
 * @author Rong.Jia
 * @date 2020/02/21 10:04
 */
@Data
public class CredentialsVO implements Serializable {

    private static final long serialVersionUID = 7914759244718059301L;

    private Long id;

    private Integer version;

    private String name;

    private String password;

    private boolean enabled;

    private List<Authority> authorities;

    public List<GrantedAuthority> getGrantedAuthorities(){
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Authority authority : getAuthorities()) {
            SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getAuthority());
            authorities.add(grantedAuthority);
        }
        return authorities;
    }

}
