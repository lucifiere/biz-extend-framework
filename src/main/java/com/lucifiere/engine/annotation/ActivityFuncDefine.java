package com.lucifiere.engine.annotation;

import com.lucifiere.biz.activities.constants.OperationParamEnum;

import java.lang.annotation.*;

/**
 * 营销计算函数扫描注解
 *
 * @author 忠魂
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ActivityFuncDefine {
    /**
     * 函数编码
     *
     * @return the string
     */
    String code();

    /**
     * 函数名
     *
     * @return the string
     */
    String macroName();

    /**
     * 函数描述
     *
     * @return the string
     */
    String desc() default "";

    /**
     * 函数附加参数列表
     *
     * @return the operation param enum [ ]
     */
    OperationParamEnum[] params() default {};

    /**
     * condition还是action
     *
     * @return the int
     */
    int metaType() default 0;

    /**
     * 语义动作描述 例如：打折、减钱等
     *
     * @return the int
     */
    int actionType() default 0;
}
