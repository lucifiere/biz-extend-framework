package com.lucifiere.common.exception;

/**
 * 公共异常
 *
 * @author 沾清
 * @version 1.0
 * @since 1.0 2019年4月1日
 */
public class CommonException extends RuntimeException {

    public CommonException(String innerCode, Object... args) {
        super(innerCode);
    }

}
