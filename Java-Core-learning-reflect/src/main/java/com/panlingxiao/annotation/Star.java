package com.panlingxiao.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by panlingxiao on 2018/7/6.
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Star {
    String value() default "hello";

}
