package com.lucifiere.engine;

import java.io.Serializable;

/**
 * 优惠实例
 *
 * @author 忠魂
 */
public class PromotionInstance implements Serializable {

    public enum ExceptionStrategy {
        /**
         * 遇到异常终止
         */
        BREAK,
        /**
         * 忽略业务异常
         */
        IGNORE_BIZ,
        /**
         * 忽略全部异常
         */
        IGNORE_ALL,
    }

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -9127046500233460716L;

    /**
     * 表达式
     */
    private String express = "";

    /**
     * 控制脚本执行过程中遇到异常时的处理方式，由子类决定
     */
    private ExceptionStrategy executeStrategy = ExceptionStrategy.IGNORE_BIZ;

    /**
     * Gets express
     *
     * @return the express
     */
    public String getExpress() {
        return express;
    }

    /**
     * Sets express
     *
     * @param express the express
     */
    public void setExpress(String express) {
        this.express = express;
    }

    public ExceptionStrategy getExecuteStrategy() {
        return executeStrategy;
    }

    public void setExecuteStrategy(ExceptionStrategy executeStrategy) {
        this.executeStrategy = executeStrategy;
    }
}
