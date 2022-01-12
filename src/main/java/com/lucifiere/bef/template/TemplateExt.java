package com.lucifiere.bef.template;

import java.lang.annotation.*;

/**
 * 模板扩展扫描注解
 *
 * @author 沾清
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TemplateExt {

    /**
     * 扩展点代码
     *
     * @return the string
     */
    String code() default "";

    /**
     * 业务场景代码
     *
     * @return the string
     */
    String scenario() default "";
}
