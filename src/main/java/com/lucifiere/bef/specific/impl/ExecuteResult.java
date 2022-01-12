package com.lucifiere.bef.specific.impl;

/**
 * 执行结果
 *
 * @param <R> the type parameter
 * @author 沾清
 */
public class ExecuteResult<R> {

    /**
     * The Result.
     */
    private R result;

    /**
     * Gets result
     *
     * @return the result
     */
    public R getResult() {
        return result;
    }

    /**
     * Sets result
     *
     * @param result the result
     */
    public void setResult(R result) {
        this.result = result;
    }

    /**
     * Success execute result.
     *
     * @param <R>    the type parameter
     * @param result the result
     * @return the execute result
     */
    public static <R> ExecuteResult<R> success(R result) {
        ExecuteResult<R> executeResult = new ExecuteResult<>();
        executeResult.setResult(result);
        return executeResult;
    }
}
