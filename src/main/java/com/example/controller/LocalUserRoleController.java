package com.example.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.LocalRole;
import com.example.entity.LocalUser;
import com.example.entity.LocalUserRole;
import com.example.service.LocalRoleService;
import com.example.service.LocalUserRoleService;
import com.example.service.LocalUserService;
import com.example.validation.LocalUserRoleForm;

@Controller
public class LocalUserRoleController {
	@Autowired
	LocalUserRoleService userRoleService;
	
	@Autowired
	LocalUserService userService;
	
	@Autowired
	LocalRoleService roleService;
	
	@GetMapping("/listUserRole")
	public String listUserRole(Model model) {
		model.addAttribute("userRoleList", userRoleService.findAll());
		model.addAttribute("roleList", roleService.findAll());
		return "/user_role/list";
	}
	
	@PostMapping("/searchUserRole")
	public String searchUserRole(@RequestParam(name="role", required=false) LocalRole role, Model model) {
		List<LocalUserRole> list;
		if (role == null) {
			list = userRoleService.findAll();
		} else {
			list = userRoleService.findByRoleName(role.getName());
		}
		model.addAttribute("userRoleList", list);
		model.addAttribute("roleList", roleService.findAll());
		model.addAttribute("role", role);
		return "/user_role/list";
	}
	
	@GetMapping("/editUserRole/{userId}")
	public String editUserRole(@PathVariable(value = "userId") int userId, Model model) {
		LocalUserRoleForm form = new LocalUserRoleForm();
		LocalUser user = userService.findById(userId).orElse(null);
		form.setUser(user);
		List<LocalUserRole> userRole = userRoleService.findByUserId(userId);
		LocalRole[] roleValues = new LocalRole[userRole.size()];
		for (int i = 0; i < roleValues.length; i++) {
			roleValues[i] = userRole.get(i).getRole();
		}
		form.setRoleValues(roleValues);
		model.addAttribute("localUserRoleForm", form);
		
		model.addAttribute("userList", Arrays.asList(new LocalUser[] {user}));
		model.addAttribute("roleList", roleService.findAll());
		return "/user_role/create";
	}
	
	@GetMapping("/createUserRole")
	public String createUserRole(Model model) {
		model.addAttribute("localUserRoleForm", new LocalUserRoleForm());
		model.addAttribute("userList", userService.findAll());
		model.addAttribute("roleList", roleService.findAll());
		return "/user_role/create";
	}
	
	@PostMapping("/createUserRole")
	public String createUserRole(@Validated LocalUserRoleForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			LocalUser user = form.getUser();
			if (user != null) {
				model.addAttribute("userList", Arrays.asList(new LocalUser[] {user}));
			} else {
				model.addAttribute("userList", userService.findAll());				
			}
			model.addAttribute("roleList", roleService.findAll());
			return "/user_role/create";
		}
		userRoleService.save(form);
		return "redirect:/listUserRole";
	}
	
	@PostMapping("/deleteUserRole")
	public String deleteUserRole(@RequestParam("userId") Integer userId, 
			@RequestParam("roleName") String roleName) {
		userRoleService.delete(userId, roleName);
		return "redirect:/listUserRole";
	}
}
