package com.lucifiere.bef.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.*;

/**
 * The type Bef error code.
 *
 * @author 沾清
 */
public class BefErrorCode {
    /**
     * The constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BefErrorCode.class);

    /**
     * The constant SYSTEM_ERROR.
     */
    public static final String SYSTEM_ERROR = "LARK-THEMIS-BEF-1-001";

    /**
     * 约定的错误码的路径
     * XA用于内部错误码
     * CN用于客户显示信息
     * AA用于日志记录信息
     */
    public static final String BUNDLE = "i18n.errors";

    /**
     * The constant READABLE_ERROR_CODE_FILE_PATH.
     */
    public final static String READABLE_ERROR_CODE_FILE_PATH = "i18n/bef_errors_en_XA.properties";
    /**
     * The Readable error code map.
     */
    private static Map<String, String> readableErrorCodeMap;

    /**
     * The constant DISPLAY_ERROR_CODE_FILE_PATH.
     */
    public final static String DISPLAY_ERROR_CODE_FILE_PATH = "i18n/bef_errors_zh_CN.properties";
    /**
     * The Display error code map.
     */
    private static Map<String, String> displayErrorCodeMap;

    /**
     * The constant INTERNAL_ERROR_CODE_FILE_PATH.
     */
    public final static String INTERNAL_ERROR_CODE_FILE_PATH = "i18n/bef_errors_en_AA.properties";
    /**
     * The Internal error code map.
     */
    private static Map<String, String> internalErrorCodeMap;

    static {
        readableErrorCodeMap = extractErrorCodes(READABLE_ERROR_CODE_FILE_PATH);
        displayErrorCodeMap = extractErrorCodes(DISPLAY_ERROR_CODE_FILE_PATH);
        internalErrorCodeMap = extractErrorCodes(INTERNAL_ERROR_CODE_FILE_PATH);
    }

    /**
     * Extract error codes map.
     *
     * @param resourceFilePath the resource file path
     * @return the map
     */
    private static Map<String, String> extractErrorCodes(String resourceFilePath) {
        return extractErrorCodes(Thread.currentThread().getContextClassLoader(),
            resourceFilePath);
    }

    /**
     * Extract error codes map.
     *
     * @param classLoader      the class loader
     * @param resourceFilePath the resource file path
     * @return the map
     */
    private static Map<String, String> extractErrorCodes(ClassLoader classLoader,
                                                         String resourceFilePath) {
        Map<String, String> props = new HashMap<>(16);
        try {
            Enumeration<URL> resources = classLoader.getResources(resourceFilePath);
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                InputStream in = url.openStream();
                Properties prop = new Properties();
                prop.load(new InputStreamReader(in, Charset.forName("UTF-8")));
                prop.forEach((key, value) -> props.put(String.valueOf(key), String.valueOf(value)));
            }
        } catch (IOException e) {
            return Collections.emptyMap();
        }
        return props;
    }

    /**
     * To system error message bef error message.
     *
     * @param errorArgs the error args
     * @return the bef error message
     */
    public static BefErrorMessage toSystemErrorMessage(Object... errorArgs) {
        return toErrorMessage(SYSTEM_ERROR, errorArgs);
    }

    /**
     * To error message bef error message.
     *
     * @param errorCode the error code
     * @param errorArgs the error args
     * @return the bef error message
     */
    public static BefErrorMessage toErrorMessage(String errorCode, Object... errorArgs) {
        String readableErrorMessage = readableErrorCodeMap.get(errorCode);
        if (readableErrorMessage == null) {
            readableErrorMessage = errorCode;
        }

        String internalErrorMessage = internalErrorCodeMap.get(errorCode);
        if (internalErrorMessage == null) {
            internalErrorMessage = readableErrorMessage;
        } else {
            internalErrorMessage = MessageFormat.format(internalErrorMessage, errorArgs);
        }

        String displayErrorMessage = displayErrorCodeMap.get(errorCode);
        if (displayErrorMessage == null) {
            displayErrorMessage = internalErrorMessage;
        } else {
            displayErrorMessage = MessageFormat.format(displayErrorMessage, errorArgs);
        }

        return BefErrorMessage.of(errorCode, readableErrorMessage, displayErrorMessage, internalErrorMessage);
    }
}
