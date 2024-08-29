package com.example.EcoCamper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.EcoCamper.jwt.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public JwtFilter jwtAuthenticationFilter() {
		return new JwtFilter();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http.csrf(csrf -> csrf.disable())
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/login", "/loginForm", "/join", "/joinForm", "/index", "/myPage", "/menu", "/user/checkId", "/user/logout",
	            		"/js/**", "/css/**", "/images/**").permitAll()
	            
	            .anyRequest().authenticated()
	        )
	        .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}

}
