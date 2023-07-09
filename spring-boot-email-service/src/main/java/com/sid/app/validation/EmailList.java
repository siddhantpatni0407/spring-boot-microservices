package com.sid.app.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * @author Siddhant Patni
 */
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailListValidator.class)
public @interface EmailList {

    String message() default "Invalid email address may be provided.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}