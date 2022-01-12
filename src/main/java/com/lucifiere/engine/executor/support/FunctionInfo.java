package com.lucifiere.engine.executor.support;

/**
 * 规则引擎执行所需要的外部表达式对象
 *
 * @author 和同
 */
public class FunctionInfo {

    /**
     * ql表达式文件读取
     */
    private String qlExpress;

    /**
     * BizInstanceId对象中的bizCode
     */
    private String instanceCode;

    public String getQlExpress() {
        return qlExpress;
    }

    public void setQlExpress(String qlExpress) {
        this.qlExpress = qlExpress;
    }

    public String getInstanceCode() {
        return instanceCode;
    }

    public void setInstanceCode(String instanceCode) {
        this.instanceCode = instanceCode;
    }

}
