package com.lucifiere.bef.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 能力扩展点-商业能力
 *
 * @author 沾清
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AbilityExtension {

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
