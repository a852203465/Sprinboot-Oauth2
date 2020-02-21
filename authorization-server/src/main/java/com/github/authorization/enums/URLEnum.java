package com.github.authorization.enums;

/**
 * url 枚举类
 * @author Rong.Jia
 * @date 2020/02/21 17:01
 */
public enum  URLEnum {

    // 获取授权
    OAUTH_TOKEN("/oauth/token?grant_type=client_credentials");

    ;

    /**
     * 下推接口
     */
    private String value;

    URLEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }







}
