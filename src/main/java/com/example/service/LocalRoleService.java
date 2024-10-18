package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.example.entity.LocalRole;
import com.example.repositry.LocalRoleRepository;
import com.example.repositry.LocalUserRoleRepository;
import com.example.validation.LocalRoleForm;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class LocalRoleService {
	@Autowired
	LocalRoleRepository roleRepository;
	
	@Autowired
	LocalUserRoleRepository userRoleRepository;
	
	public LocalRole save(LocalRoleForm form, BindingResult result) {
		LocalRole role = new LocalRole();
		BeanUtils.copyProperties(form, role);
		if (roleRepository.existsById(form.getName())) {
			result.rejectValue("name", "com.example.service.LocalRoleService.save.existsById");//messages.properties
			return role;
		}
		return roleRepository.save(role);
	}
	
	public List<LocalRole> findAll() {
		return roleRepository.findAll();
	}
	
	public Optional<LocalRole> findById(String name) {
		return roleRepository.findById(name);
	}
	
	public void delete(LocalRoleForm form, BindingResult result) {
		if (userRoleRepository.existsByRoleName(form.getName())) {
			result.rejectValue("name", "com.example.service.LocalRoleService.delete.existsByRoleName");//messages.properties
			return;
		}
		roleRepository.deleteById(form.getName());
	}
}
