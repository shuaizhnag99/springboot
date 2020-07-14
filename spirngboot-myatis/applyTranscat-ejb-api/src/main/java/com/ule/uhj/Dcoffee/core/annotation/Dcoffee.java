package com.ule.uhj.Dcoffee.core.annotation;

import java.lang.annotation.*;

/**
 * Created by zhengxin on 2018/3/14.
 */
@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Dcoffee {
    public static final String name = "name";
    public String name() default "";
    public Class proxy() default Object.class;
    public String scope() default "";
}
