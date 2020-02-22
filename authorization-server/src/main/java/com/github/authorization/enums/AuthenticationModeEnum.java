package com.github.authorization.enums;

import com.github.core.utils.ObjectUtils;

/**
 * 认证模式枚举类
 * @author Rong.Jia
 * @date 2020/02/22 13:45
 */
public enum  AuthenticationModeEnum {

    // 密码模式
    PASSWORD_MODE(1, "password"),

    // 授权码模式
    authorization_code_MODE(2, "authorization_code"),

    // 简化模式
    IMPLICIT_MODE(3, "implicit"),

    // 客户端模式
    CLIENT_CREDENTIALS_MODE(4, "client_credentials"),

    ;

    private Integer code;
    private String value;

    AuthenticationModeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }


    /**
     *  获取模式
     * @param code 代码
     * @return  模式
     */
    public static String getValue(Integer code){

        AuthenticationModeEnum[] authenticationModeEnums = AuthenticationModeEnum.values();
        for (AuthenticationModeEnum authenticationModeEnum : authenticationModeEnums) {
            if (ObjectUtils.equal(authenticationModeEnum.getCode(), code)) {
                return authenticationModeEnum.getValue();
            }
        }

        return null;
    }






}
