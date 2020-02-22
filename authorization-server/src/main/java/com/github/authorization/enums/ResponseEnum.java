package com.github.authorization.enums;

/**
 * 返回码 枚举类
 * @author Rong.Jia
 * @date 2020/01/07 14:32
 */
public enum ResponseEnum {

    /**
     */

    // 成功
    SUCCESS(0,"成功"),

    // 参数不正确
    PARAMETER_ERROR(1, "参数不正确"),

    // 失败
    ERROR(-1, "失败"),
    SYSTEM_ERROR(-1, "系统错误"),

    THE_ID_CANNOT_BE_EMPTY(1004, "id 不能为空"),
    THE_NAME_CANNOT_BE_EMPTY(1005, "名称不能为空"),

    THE_CLIENT_ALREADY_EXISTS(6001, "客户端已存在"),
    THE_CLIENT_DOES_NOT_E0XIST_OR_IS_DELETED(6002, "客户端不存在, 或已删除"),

    USER_ALREADY_EXISTS(6003, "用户已存在"),
    THE_USER_DOES_NOT_EXIST(6004, "用户不存在"),











    ;

    private Integer code;
    private String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }













}
