package com.example.EcoCamper.controller;

import java.security.Principal;

import javax.swing.text.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.EcoCamper.dto.UserDTO;
import com.example.EcoCamper.dto.UserResponseDTO;
import com.example.EcoCamper.entity.User;
import com.example.EcoCamper.jwt.TokenProvider;
import com.example.EcoCamper.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {

	@Autowired
	UserService service;
	@Autowired
	private TokenProvider tokenProvider;

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody UserDTO userDTO, HttpServletResponse response) {
		User user = service.login(userDTO.getId(), userDTO.getPwd(), passwordEncoder);

		if (user != null) {
			// 토큰 생성
			final String token = tokenProvider.createToken(user);
			// JWT 토큰을 HttpOnly 쿠키에 저장
		    ResponseCookie cookie = ResponseCookie.from("token", token)
		            .httpOnly(true) // 자바스크립트 접근 불가
		            .secure(true)   // HTTPS에서만 전송
		            .path("/")
		            .maxAge(24 * 60 * 60) // 쿠키 유효기간 설정(1일)
		            .sameSite("Strict") // SameSite 속성 설정
		            .build();
		    
		    response.setHeader("Set-Cookie", cookie.toString());
		    
			System.out.println(token);
			final UserDTO responseUserDTO = UserDTO.builder().id(user.getId()).token(token).age(user.getAge())
					.name(user.getName()).build();
			return ResponseEntity.ok().body(responseUserDTO);

		} else {
			UserResponseDTO responseDTO = UserResponseDTO.builder().error("Login failed.").build();
			return ResponseEntity.badRequest().body(responseDTO);
		}

	}

	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/loginForm";
	}
	
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "/user/joinForm";
	}
	
	@PostMapping("/join")
	public String join(UserDTO userDTO, Model model) {
		User user = service.join(userDTO);
		model.addAttribute("user", user);
		return "/user/join";
	}
	
	@GetMapping("/index")
	public String index() {
		return "/index";
	}
	
	@GetMapping("/menu")
	public String menu() {
	        return "/menu";
	    
	}
	@GetMapping("/myPage")
	public String myPage(Model model, HttpServletRequest request) {
		String token = tokenProvider.resolveTokenFromCookie(request);
		System.out.println("token : " + token);
	    String userId = tokenProvider.validateAndGetUserId(token);
	    if (userId != null) {
	    	User user = service.getUser(userId);
			System.out.println("user : " + user);
			model.addAttribute("user", user);
			
			return "/user/myPage";
	    } else {
	    	return "index";
	    }

		
	}


	

}