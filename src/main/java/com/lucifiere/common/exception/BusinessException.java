package com.lucifiere.common.exception;

/**
 * 业务相关异常
 *
 * @author 沾清
 * @version 1.0
 * @since 1.0 2019年4月1日
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String innerCode, Object... args) {
        super(innerCode);
    }

}
