package org.flutterstudy.api.contracts.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UserNameValidator.class})
public @interface ValidUserName {
	String message() default "Invalid Username";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
