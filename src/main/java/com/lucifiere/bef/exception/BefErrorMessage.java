package com.lucifiere.bef.exception;

import java.io.Serializable;

/**
 * The type Bef error message.
 *
 * @author 沾清
 */
public class BefErrorMessage implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3017472277626865964L;

    /**
     * The Error code.
     */
    private String errorCode;
    /**
     * The Readable error message.
     */
    private String readableErrorMessage;
    /**
     * The Display error message.
     */
    private String displayErrorMessage;
    /**
     * The Internal error message.
     */
    private String internalErrorMessage;

    /**
     * Instantiates a new Bef error message.
     *
     * @param errorCode            the error code
     * @param readableErrorMessage the readable error message
     * @param displayErrorMessage  the display error message
     * @param internalErrorMessage the internal error message
     */
    public BefErrorMessage(String errorCode, String readableErrorMessage, String displayErrorMessage,
                           String internalErrorMessage) {
        this.errorCode = errorCode;
        this.readableErrorMessage = readableErrorMessage;
        this.displayErrorMessage = displayErrorMessage;
        this.internalErrorMessage = internalErrorMessage;
    }

    /**
     * Of bef error message.
     *
     * @param errorCode            the error code
     * @param readableErrorMessage the readable error message
     * @param displayErrorMessage  the display error message
     * @param internalErrorMessage the internal error message
     * @return the bef error message
     */
    public static BefErrorMessage of(String errorCode, String readableErrorMessage, String displayErrorMessage,
                                     String internalErrorMessage) {
        return new BefErrorMessage(errorCode, readableErrorMessage, displayErrorMessage, internalErrorMessage);
    }

    /**
     * Gets errorCode
     *
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Gets readableErrorMessage
     *
     * @return the readableErrorMessage
     */
    public String getReadableErrorMessage() {
        return readableErrorMessage;
    }

    /**
     * Gets displayErrorMessage
     *
     * @return the displayErrorMessage
     */
    public String getDisplayErrorMessage() {
        return displayErrorMessage;
    }

    /**
     * Gets internalErrorMessage
     *
     * @return the internalErrorMessage
     */
    public String getInternalErrorMessage() {
        return internalErrorMessage;
    }
}
