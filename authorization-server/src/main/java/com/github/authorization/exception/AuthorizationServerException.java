package com.github.authorization.exception;

import com.github.authorization.enums.ResponseEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 监控服务自定义异常
 * @author Rong.Jia
 * @date 2020/01/07 14:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthorizationServerException extends RuntimeException  implements Serializable {

    private static final long serialVersionUID = 4375668247447814391L;

    /**
     *  异常code 码
     */
    private Integer code;

    /**
     * 异常详细信息
     */
    private String message;

    public AuthorizationServerException(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public AuthorizationServerException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }

    public AuthorizationServerException(ResponseEnum responseEnum, String message) {
        super(message);
        this.code = responseEnum.getCode();
        this.message = message;
    }

    public AuthorizationServerException(Integer code, String message, Throwable t) {
        super(message, t);
        this.code = code;
        this.message = message;
    }

    public AuthorizationServerException(ResponseEnum responseEnum, Throwable t) {
        super(responseEnum.getMessage(), t);
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }

}
