package com.example.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = {ByteLengthValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ByteLength {
	String message() default "{com.example.validation.ByteLength.message}";//messages.properties
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	int min();
	int max();
	
    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface List {
    	ByteLength[] value();
	}
}
