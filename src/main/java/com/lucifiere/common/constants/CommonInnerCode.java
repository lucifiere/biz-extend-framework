package com.lucifiere.common.constants;

/**
 * 大文娱B端公共错误码定义
 *
 * @author 沾清
 * @version 1.0
 * @since 1.0 2019年3月28日
 */
final public class CommonInnerCode {

    /**
     * 调用成功
     */
    public static final String SUCCESS = "LARK-F221-001-4-B000";

    /**
     * 严重：未归类的系统异常
     */
    public static final String SYSTEM_ERROR = "LARK-F221-001-2-O999";

    /**
     * 参数异常
     */
    public static final String ILLEGAL_ARGUMENT = "LARK-F221-001-4-P998";

    /**
     * 必传参数缺失
     */
    public static final String REQUIRED_ARGUMENT_MISSING = "LARK-F221-001-4-P997";

    /**
     * 查询/筛选结果缺失
     */
    public static final String DATA_NOT_EXIST = "LARK-F221-001-4-B996";

    /**
     * 业务校验有误，可自定义错误提示文案
     */
    public static final String BIZ_CHECK_FAILED = "LARK-F221-001-4-B995";

    /**
     * ql执行失败
     */
    public static final String QL_SCRIPT_ERROR = "LARK-F221-001-4-B994";

    /**
     * 不支持的业务类型
     */
    public static final String UNSUPPORTED_BUSINESS_CONFIG = "LARK-F221-001-4-B993";

    /**
     * 业务域截断
     */
    public static final String BREAK_SCENE_CHECK = "LARK-F221-001-4-B992";

    /**
     * 不支持的操作
     */
    public static final String UNSUPPORTED_OPERATION = "LARK-F221-001-4-O991";

}
