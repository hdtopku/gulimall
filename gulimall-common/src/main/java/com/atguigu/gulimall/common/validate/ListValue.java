package com.atguigu.gulimall.common.validate;

import com.alibaba.nacos.api.grpc.auto.Payload;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * @author lxh
 * @createTime 2022-03-08 21:49:58
 */

@Documented
@Constraint(
        validatedBy = {ListValueConstraintValidator.class}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ListValue {
    String message() default "{com.atguigu.gulimall.common.validate.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int[] values() default {};
}
