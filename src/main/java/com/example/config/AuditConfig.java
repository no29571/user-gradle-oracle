package com.example.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.service.LoginUserDetails;

@Configuration
@EnableJpaAuditing
public class AuditConfig {
    @Bean
    AuditorAware<Integer> auditorProvider() {
        return new AuditorAware<Integer>() {
        	@Override
        	public Optional<Integer> getCurrentAuditor() {
        		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        		if (auth == null || !auth.isAuthenticated() || auth.getPrincipal() == null) {
        			return Optional.empty();
        		}
        		
    			//return Optional.of(1);//初期ユーザ作成時のみ有効化
        		
        		LoginUserDetails principal = (LoginUserDetails) auth.getPrincipal();
        		return Optional.of(principal.getId());
        	}
        };
    }
}