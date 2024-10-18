package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.example.entity.LocalUser;
import com.example.repositry.LocalUserRepository;
import com.example.validation.LocalUserForm;
import com.example.validation.PasswordChangeForm;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class LocalUserService {
	@Autowired
	private LocalUserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	public LocalUser insert(LocalUserForm form) {
		LocalUser user = new LocalUser();
		BeanUtils.copyProperties(form, user);
		user.setPassword(passwordEncoder.encode(form.getPasswordRaw()));//暗号化
		return userRepository.save(user);
	}
	
	public LocalUser update(LocalUserForm form) {
		LocalUser user = new LocalUser();
		BeanUtils.copyProperties(form, user);
		return userRepository.save(user);
	}
	
	public Optional<LocalUser> changePassword(PasswordChangeForm form, BindingResult result) {
		Optional<LocalUser> opt = userRepository.findOneForUpdateByEmail(form.getEmail());
		if (opt.isEmpty()) {
			return opt;
		}
		LocalUser user = opt.get();
		if (!passwordEncoder.matches(form.getPasswordOld(), user.getPassword())) {
			result.rejectValue("passwordOld", "com.example.service.LocalUserService.changePassword.matches");//messages.properties
			return opt;
		}
		user.setPassword(passwordEncoder.encode(form.getPasswordNew()));//暗号化
		return Optional.of(userRepository.save(user));
	}
	
	public List<LocalUser> findAll() {
		return userRepository.findAllByOrderByEmailAsc();
	}
	
	public List<LocalUser> findUser(String keyword) {
		return userRepository.findByEmailContainingOrderByEmailAsc(keyword);
	}
	
	public Optional<LocalUser> findById(Integer id) {
		return userRepository.findById(id);
	}
	
	/*public void delete(Integer id) {
		userRepository.deleteById(id);
	}*/
}
