package com.example.validation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.entity.LocalUser;
import com.example.repositry.LocalUserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, LocalUserForm> {
	@Autowired
	private LocalUserRepository userRepository;
	
	private String message;
	
	@Override
	public void initialize(UniqueEmail annotation) {
		this.message = annotation.message();
	}
	
	@Override
	public boolean isValid(LocalUserForm value, ConstraintValidatorContext context) {
		if (value.getEmail() == null || value.getEmail().isEmpty()) {
			//未設定はここではチェックしない
			return true;
		}
		
		boolean valid = false;
		Optional<LocalUser> opt = userRepository.findByEmail(value.getEmail());
		if (opt.isPresent()) {
			LocalUser user = opt.get();
			if (user.getId() == value.getId()) {
				valid = true;
			} else {
				valid = false;
			}
		} else {
			valid = true;
		}
		
		if (!valid) {
			//LocalUserFormレベルのチェック → emailフィールドのエラーメッセージに設定
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message)
				.addPropertyNode("email")
				.addConstraintViolation();
		}
		return valid;
	}
}

