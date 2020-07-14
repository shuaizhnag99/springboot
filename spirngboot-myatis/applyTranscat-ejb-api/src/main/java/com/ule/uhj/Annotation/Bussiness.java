package com.ule.uhj.Annotation;

import com.ule.uhj.dto.zgd.TransferModel;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bussiness {
    /***
     * 业务名称
     * @return
     */
    String name();

    /***
     * 业务描述
     * （无实际意义，仅描述业务使用）
     * @return
     */
    String descript() default "";

    /***
     * 是否进行模型校验
     * @return
     */
    boolean check() default false;

    /***
     * 是否留存调用日志
     * @return
     */
    boolean save() default false;
}
