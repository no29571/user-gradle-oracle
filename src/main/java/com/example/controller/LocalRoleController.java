package com.example.controller;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.entity.LocalRole;
import com.example.service.LocalRoleService;
import com.example.validation.LocalRoleForm;

@Controller
public class LocalRoleController {
	@Autowired
	LocalRoleService roleService;
	
	@GetMapping("/listRole")
	public String listRole(Model model) {
		model.addAttribute("roleList", roleService.findAll());
		return "role/list";
	}
	
	@GetMapping("/createRole")
	public String createRole(Model model) {
		model.addAttribute("localRoleForm", new LocalRoleForm());
		return "role/create";
	}
	
	@PostMapping("/createRole")
	public String createRole(@Validated LocalRoleForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "/role/create";
		}
		roleService.save(form, result);
		if (result.hasErrors()) {
			return "/role/create";
		}
		return "redirect:/listRole";
	}
	
	@GetMapping("/deleteRole/{name}")
	public String deleteRole(@PathVariable(value = "name") String name, Model model) {
		Optional<LocalRole> opt = roleService.findById(name);
		if (opt.isEmpty()) {
			return "redirect:/listRole";
		}
		LocalRole role = opt.get();
		LocalRoleForm form = new LocalRoleForm();
		BeanUtils.copyProperties(role, form);
		model.addAttribute("localRoleForm", form);
		return "role/delete";
	}
	
	@PostMapping("/deleteRole")
	public String deleteRole(@Validated LocalRoleForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "/role/delete";
		}
		roleService.delete(form, result);
		if (result.hasErrors()) {
			return "/role/delete";
		}
		return "redirect:/listRole";
	}
}
