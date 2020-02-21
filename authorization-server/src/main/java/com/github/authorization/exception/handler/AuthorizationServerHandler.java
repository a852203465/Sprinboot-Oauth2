package com.github.authorization.exception.handler;

import com.github.authorization.exception.AuthorizationServerException;
import com.github.authorization.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常控制处理器
 * @author Rong.Jia
 * @date 2020/01/07 14:33
 */
@Slf4j
@RestControllerAdvice
public class AuthorizationServerHandler {

    /**
     *  捕获自定义异常，并返回异常数据
     * @author Rong.Jia
     * @date 2020/01/07 14:34
     */
    @ExceptionHandler(value = AuthorizationServerException.class)
    public ResponseVO monitoringExceptionHandler(AuthorizationServerException e){

        log.error("monitoringExceptionHandler  {}", e.getMessage());

        return ResponseVO.error(e.getCode(), e.getMessage());

    }

}
