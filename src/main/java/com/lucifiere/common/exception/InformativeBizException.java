package com.lucifiere.common.exception;

/**
 * 需要附带信息的异常
 *
 * @author 沾清
 * @version 1.0
 * @since 1.0 2019年3月28日
 */
public class InformativeBizException extends RuntimeException {

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public InformativeBizException(String innerCode, Object... args) {
        super(innerCode);
    }

    public InformativeBizException(String innerCode, Object obj, Object... args) {
        super(innerCode);
        this.data = obj;
    }

}
