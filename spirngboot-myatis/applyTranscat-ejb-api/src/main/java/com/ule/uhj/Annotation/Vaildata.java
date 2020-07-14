package com.ule.uhj.Annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Vaildata {
	public static final String NOT_NULL = ",not,null,";
	String model() default "";
	int maxLength() default 0;
	int minLength() default -1;
	boolean nullable() default false;
	String external() default "";
	String externalValue() default "";
	String errorMsg() default "校验出错";
}
