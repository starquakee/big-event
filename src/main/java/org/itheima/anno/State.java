package org.itheima.anno;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.itheima.validation.StateValidation;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {StateValidation.class}) //提供校验规则类
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface State {
    //提供校验失败后的提示信息
    String message() default "state参数的值只能是已发布或草稿";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
