package com.example.validation;

import java.nio.charset.StandardCharsets;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ByteLengthValidator implements ConstraintValidator<ByteLength, String> {
	private int min;
	private int max;
	
	@Override
	public void initialize(ByteLength annotation) {
		this.min = annotation.min();
		this.max = annotation.max();
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			value = "";
		}
		int len = value.getBytes(StandardCharsets.UTF_8).length;
		if (len < min || len > max) {
			return false;
		} else {
			return true;
		}
	}
}
