package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(authz -> authz
				.requestMatchers("/webjars/**").permitAll()	// WebJarsは認証不要
				.requestMatchers("/css/**").permitAll()	// CSSは認証不要
				.requestMatchers("/error").permitAll()	// エラーページは認証不要
				//.requestMatchers("/").permitAll()	// トップページは認証不要
				
				.requestMatchers("/createUser").hasAnyAuthority("ROLE_ADMIN")//ADMIN以外はForbidden
				//hasAnyRole使い方要注意：判定時にプレフィックス"ROLE_"が付加されるため、引数は"ROLE_"をつけずに渡す
				.requestMatchers("/editUser/**").hasAnyRole("ADMIN", "USER")//ADMINとUSER以外はForbidden
				
				//.requestMatchers("/**").permitAll()	// 初期ユーザ作成時のみ有効化
				
				.anyRequest().authenticated()		// 他のURLは要認証
			)
			.formLogin(login -> login
				.loginPage("/login")				// ログインフォーム
				//.loginProcessingUrl("/login")		// ログイン処理（POST）のURL（デフォルトなら省略可）
				//.usernameParameter("username")		// リクエストパラメータ名（デフォルトなら省略）
				//.passwordParameter("password")		// リクエストパラメータ名（デフォルトなら省略）
				//.defaultSuccessUrl("/", true)		// ログイン成功時の遷移先（trueで必ず遷移。デフォルトでは指定されたURL）
				//.failureUrl("/login?error=true")	// ログイン失敗時の遷移先（デフォルトなら省略）
				.permitAll() 						// ログインは認証不要
			)
			.logout(logout -> logout
				//.logoutUrl("/logout") 				// ログアウト処理（POST）のURL（デフォルトなら省略可）
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))// GETでもログアウト可
				.logoutSuccessUrl("/login")			// ログアウト成功時の遷移先
			)
		;
		return http.build();
	}
}