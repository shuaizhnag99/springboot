package com.ule.uhj.Annotation;

import java.lang.annotation.*;

/**
 * Created by zhengxin on 2018/3/7.
 */
@Documented
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Trans {
}
