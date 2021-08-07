package io.microwave.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 配置服务
 */
@Target({ElementType.TYPE})
@Retention(RUNTIME)
public @interface MicrowaveServer {
    /**
     * 服务名称
     * @return
     */
    String application() default "";

    /**
     * 服务注册端口号
     * @return
     */
    int port() default 8761;
}
