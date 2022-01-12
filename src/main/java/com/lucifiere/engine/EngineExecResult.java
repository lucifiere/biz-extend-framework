package com.lucifiere.engine;

/**
 * 引擎计算结果
 *
 * @author 沾清
 * @version 1.0
 * @since 1.0 2019年7月17日
 */
public class EngineExecResult {

    private boolean success;

    private Object result;

    private String msg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static EngineExecResult suc(Object o) {
        EngineExecResult resp = new EngineExecResult();
        resp.setSuccess(true);
        resp.setMsg("执行成功");
        resp.setResult(o);
        return resp;
    }

    public static EngineExecResult fail(String msg) {
        EngineExecResult resp = new EngineExecResult();
        resp.setSuccess(false);
        resp.setMsg(msg);
        resp.setResult(null);
        return resp;
    }

    public static EngineExecResult error() {
        EngineExecResult resp = new EngineExecResult();
        resp.setSuccess(false);
        resp.setMsg("QL脚本执行异常！");
        resp.setResult(null);
        return resp;
    }

}
