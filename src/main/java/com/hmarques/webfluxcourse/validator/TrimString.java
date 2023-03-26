package com.hmarques.webfluxcourse.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Constraint(validatedBy = {TrimStringValidation.class})
@Target(FIELD)
@Retention(RUNTIME)
public @interface TrimString {

  String message() default "Não é aceito espaços em brancos!";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}