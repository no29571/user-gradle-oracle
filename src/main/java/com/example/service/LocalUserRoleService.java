package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.LocalRole;
import com.example.entity.LocalUser;
import com.example.entity.LocalUserRole;
import com.example.entity.LocalUserRoleId;
import com.example.repositry.LocalUserRoleRepository;
import com.example.validation.LocalUserRoleForm;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class LocalUserRoleService {
	@Autowired
	LocalUserRoleRepository userRoleRepository;
	
	public void save(LocalUserRoleForm form) {
		LocalUser user = form.getUser();
		//delete
		userRoleRepository.deleteByUserId(user.getId());
		//insert
		LocalRole[] values = form.getRoleValues();
		for (LocalRole role : values) {
			userRoleRepository.save(new LocalUserRole(user, role));
		}
	}
	
	public List<LocalUserRole> findAll() {
		return userRoleRepository.findAllByOrderByUserIdAscRoleNameAsc();
	}
	
	public List<LocalUserRole> findByRoleName(String roleName) {
		return userRoleRepository.findByRoleName(roleName);
	}
	
	public List<LocalUserRole> findByUserId(Integer userId) {
		return userRoleRepository.findByUserId(userId);
	}
	
	public void delete(Integer userId, String roleName) {
		userRoleRepository.deleteById(new LocalUserRoleId(userId, roleName));
	}
}
