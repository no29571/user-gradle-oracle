package com.example.service;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.entity.LocalUser;
import com.example.entity.LocalUserRole;

//Spring Security
public class LoginUserDetails implements UserDetails {
	private static final long serialVersionUID = 1L;
	private LocalUser user;
	private Collection<? extends GrantedAuthority> authorities;
	
	public LoginUserDetails(LocalUser user) {
		this.user = user;
		
		SimpleGrantedAuthority[] auth;
		if (user.getUserRole().size() == 0) {
			auth = new SimpleGrantedAuthority[1];
			auth[0] = new SimpleGrantedAuthority("ROLE_GUEST");
		} else {
			auth = new SimpleGrantedAuthority[user.getUserRole().size()];
			for (int i = 0; i < auth.length; i++) {
				LocalUserRole userRole = user.getUserRole().get(i);
				auth[i] = new SimpleGrantedAuthority("ROLE_" + userRole.getRole().getName());
			}
		}
		this.authorities = Arrays.asList(auth);		
	}
	
	public Integer getId() {
		//更新情報（作成者・更新者）設定に使用
		return user.getId();
	}
	
	public String getDisplayName() {
		//ログイン情報出力に使用
		return user.getName();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}
	
	@Override
	public String getUsername() {
		//ログインIDにメールアドレスを使用
		return this.user.getEmail();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		//実装しないので、固定でtrueを返却
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		//実装しないので、固定でtrueを返却
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		//実装しないので、固定でtrueを返却
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.isEnabled();
	}
}