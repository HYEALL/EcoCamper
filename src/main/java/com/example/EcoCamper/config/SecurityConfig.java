package com.example.EcoCamper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.EcoCamper.jwt.JwtFilter;
import com.example.EcoCamper.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final TokenProvider jwtProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http.csrf(csrf -> csrf.disable())
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/login/**", "/loginForm", "/join", "/joinForm", "/user/checkId", "/index", "/myPage/**", "/menu", "/shop/**", "/feed/**", "/add/", "/remove/**", "/mail/**", "/confirm/**", "/menu2/"
	            		,"/js/**", "/css/**", "/images/**",  "/storage/**", "/vendor/**", "/payment/**" ).permitAll()
	            .requestMatchers("/user/logout", "/user/edit", "/map", "/search").hasAnyRole("USER", "ADMIN")
	            .requestMatchers("/admin/**").hasRole("ADMIN")
	            .anyRequest().authenticated()
	        )
	        .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
	        //.exceptionHandling(exception -> { 
              //  exception.authenticationEntryPoint(new MyAuthenticationEntryPoint()); // 인증 실패시
                //exception.accessDeniedHandler(new MyAccessDeniedHandler()); // 인가 실패시
            //});
	    	
	    return http.build();
	}

}
