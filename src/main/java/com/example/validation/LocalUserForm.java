package com.example.validation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

//入力値の検証と保持
@Getter
@Setter
@UniqueEmail
public class LocalUserForm {
	private Integer id;
	
	@ByteLength(min = 1, max = 100)
	private String name;
	
	@Size(min = 1, max = 100)
	@Email
	private String email;
	
	//@Size(min = 1, max = 72)
	@Size(min = 1, max = 50)
	private String passwordRaw;
	private String password;
	
	private boolean enabled;
	
	private Long ver;
}
