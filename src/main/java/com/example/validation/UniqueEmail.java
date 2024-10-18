package com.example.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = {UniqueEmailValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
	String message() default "{com.example.validation.UniqueEmail.message}";//messages.properties
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface List {
    	UniqueEmail[] value();
	}
}
