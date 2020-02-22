package com.github.authorization.pojo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * token 有效信息
 * @author Rong.Jia
 * @date 2020/02/22 22:46
 */
@NoArgsConstructor
@Data
public class TokenCheckVO implements Serializable {

    private static final long serialVersionUID = -3707756125382425495L;

    private int exp;
    private String user_name;
    private String client_id;
    private List<String> aud;
    private List<String> scope;

}
