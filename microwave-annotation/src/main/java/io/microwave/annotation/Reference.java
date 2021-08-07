package io.microwave.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 引用服务
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RUNTIME)
public @interface Reference {
    /**
     * 远程服务名称
     * @return
     */
    String refer() default "";

    /**
     * 本地服务名称
     * @return
     */
    String local() default "";

    /**
     * 服务最小连接数
     * @return
     */
    int min() default 1;

    /**
     * 服务最大连接数
     * @return
     */
    int max() default 1;

    /**
     * 连接空闲时间
     * @return
     */
    int idle() default 60;
}
