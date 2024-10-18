package com.example.validation;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

//入力値の検証と保持
@Getter
@Setter
public class PasswordChangeForm {
	private String email;
	
	@Size(min = 1, max = 50)
	private String passwordOld;
	
	@Size(min = 1, max = 50)
	private String passwordNew;
	
	@Size(min = 1, max = 50)
	private String passwordConfirm;
	
	//テンプレート側では、inputSameフィールドのエラーメッセージとして扱う
	@AssertTrue(message = "{com.example.validation.PasswordChangeForm.isInputSame}")
	public boolean isInputSame() {
		if (passwordNew == null) {
			if (passwordConfirm == null) {
				return true;
			} else {
				return false;
			}
		} else {
			return passwordNew.equals(passwordConfirm);
		}
	}
}
