package com.example.EcoCamper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.EcoCamper.dto.UserDTO;
import com.example.EcoCamper.dto.UserResponseDTO;
import com.example.EcoCamper.entity.User;
import com.example.EcoCamper.jwt.TokenProvider;
import com.example.EcoCamper.service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService service;
	@Autowired
	private TokenProvider tokenProvider;

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
		User user = service.login(userDTO.getId(), userDTO.getPwd(), passwordEncoder);

		if (user != null) {
			// 토큰 생성
			final String token = tokenProvider.createToken(user);
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
	
	
	@GetMapping("/writeForm")
	public String writeForm() {
		
		return "/user/writeForm";
	}
	
	@PostMapping("/write")
	public String write(User user) {
		service.create(user);
		return "/user/writeForm";
	}
	
	@GetMapping("/main")
	public String main() {

		return "/main";
	}
}