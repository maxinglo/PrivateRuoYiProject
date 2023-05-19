package com.ruoyi.common.SustechEmail;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

@Email(message = "邮箱格式不正确")
@Pattern(regexp = "^(.*@mail\\.sustech\\.edu\\.cn|.*@sustech\\.edu\\.cn)$", message = "请使用南方科技大学企业邮箱")
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = {})
@Documented
public @interface SustechEmail {
    String message() default "请使用南方科技大学企业邮箱";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
