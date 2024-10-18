package com.example.controller;

import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.entity.LocalUser;
import com.example.service.LocalUserService;
import com.example.service.LoginUserDetails;
import com.example.validation.PasswordChangeForm;

@Controller
public class LoginController {
	@Autowired
	private  LocalUserService userService;

	@Autowired
	private MessageSource messageSource;
	
	@GetMapping("/")
	public String home(Model model) {
		return "/index";
	}
	
	@GetMapping("/login")
	public String login() {
		return "/login";
	}
	
	@GetMapping("/changePassword")
	public String changePassword(Model model) {
		PasswordChangeForm form = new PasswordChangeForm();
		model.addAttribute("passwordChangeForm", form);
		return "/passwd";
	}
	
	@PostMapping("/changePassword")
	public String changePassword(@Validated PasswordChangeForm form, BindingResult result,
			@AuthenticationPrincipal LoginUserDetails userDetails, Model model) {
		if (result.hasErrors()) {
			return "/passwd";
		}
		form.setEmail(userDetails.getUsername());
		Optional<LocalUser> opt = userService.changePassword(form, result);
		if (opt.isEmpty()) {
			return "redirect:/logout";
		}
		if (result.hasErrors()) {
			return "/passwd";
		}
		//return "redirect:/";
		model.addAttribute("message", messageSource.getMessage("com.example.controller.LoginController.changePassword", null, Locale.JAPAN));
		return "/passwd";
	}
}
