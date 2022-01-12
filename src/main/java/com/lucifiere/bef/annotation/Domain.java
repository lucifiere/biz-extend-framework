package com.lucifiere.bef.annotation;

import java.lang.annotation.*;

/**
 * 域定义
 *
 * @author 沾清
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface Domain {

    /**
     * 命名空间前缀
     *
     * @return the string
     */
    String parentCode() default "";

    /**
     * 代码
     *
     * @return the string
     */
    String code();

    /**
     * 名字
     *
     * @return the string
     */
    String name() default "";

    /**
     * 描述
     *
     * @return the string
     */
    String desc() default "";
}
