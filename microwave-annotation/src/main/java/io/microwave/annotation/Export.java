package io.microwave.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 导出服务
 */
@Target({ElementType.TYPE})
@Retention(RUNTIME)
public @interface Export {
}
