package com.jfeat.captcha.sdk;

import java.lang.annotation.*;

/**
 * @author jackyhuang
 * @date 2022/1/8
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Captcha {
    boolean enabled() default true;
}
