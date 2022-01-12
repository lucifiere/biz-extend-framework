package com.lucifiere.bef.exception;

/**
 * The type Bef system exception.
 *
 * @author 沾清
 */
public class BefSystemException extends RuntimeException {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 2110317492293048466L;

    /**
     * The Error message.
     */
    private BefErrorMessage errorMessage;

    /**
     * Instantiates a new Bef system exception.
     *
     * @param errorMessage the error message
     */
    public BefSystemException(BefErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Instantiates a new Bef system exception.
     *
     * @param cause the cause
     */
    public BefSystemException(Throwable cause) {
        super(cause);
        this.errorMessage = BefErrorCode.toSystemErrorMessage();
    }

    /**
     * Gets errorMessage
     *
     * @return the errorMessage
     */
    public BefErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
