package com.example.validation;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

//入力値の検証と保持
@Getter
@Setter
public class LocalRoleForm {
	@Size(min = 1, max = 10)
	private String name;
}
