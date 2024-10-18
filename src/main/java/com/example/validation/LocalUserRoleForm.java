package com.example.validation;

import com.example.entity.LocalRole;
import com.example.entity.LocalUser;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

//入力値の検証と保持
@Getter
@Setter
public class LocalUserRoleForm {
	@NotNull(message = "{com.example.validation.LocalUserRoleForm.NotNull}")
	private LocalUser user;
	
	private LocalRole[] roleValues;
}
