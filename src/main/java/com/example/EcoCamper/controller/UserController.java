package com.example.EcoCamper.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.Optional;

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

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {

	@Autowired
	UserService service;
	@Autowired
	private TokenProvider tokenProvider;

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	// 로그인
	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody UserDTO userDTO, HttpServletResponse response) {
		User user = service.login(userDTO.getId(), userDTO.getPwd(), passwordEncoder);

		if (user != null) {
			// 토큰 생성
			final String token = tokenProvider.createToken(user);
			// JWT 토큰을 HttpOnly 쿠키에 저장
			ResponseCookie cookie = ResponseCookie.from("token", token).httpOnly(true) // 자바스크립트 접근 불가
					.secure(true) // HTTPS에서만 전송
					.path("/").maxAge(24 * 60 * 60) // 쿠키 유효기간 설정(1일)
					.sameSite("Strict") // SameSite 속성 설정
					.build();

			response.setHeader("Set-Cookie", cookie.toString());

			System.out.println("login" + token);
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

	// 아이디 중복 체크
	@GetMapping("/user/checkId")
	public String checkId(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		String id = request.getParameter("id");
		// db
		boolean result = service.checkId(id);

		// 2. 데이터 공유
		model.addAttribute("result", result);
		model.addAttribute("id", id);

		// 3. view 파일 선택
		return "/user/checkId";
	}

	@GetMapping("/user/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Optional<Cookie> tokenCookie = Arrays.stream(request.getCookies()) // 토큰 쿠키 가져오기
				.filter(cookie -> cookie.getName().equals("token")).findFirst();
		if (tokenCookie.isPresent()) { // JWT 토큰 쿠키 삭제
			Cookie cookie = tokenCookie.get();
			cookie.setValue("");
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		return "/user/logout";
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
		String token = tokenProvider.resolveTokenFromCookie(request); // 쿠키에서 token 가져오기
		String userId = tokenProvider.validateAndGetUserId(token); // token이 유효한지 확인하고 userId 가져오기
		if (userId != null) {
			User user = service.getUser(userId);
			model.addAttribute("user", user);

			return "/user/myPage";
		} else {
			return "index";
		}

	}
	
	@GetMapping("/user/edit")
	public String edit(Model model, HttpServletRequest request) {
		String token = tokenProvider.resolveTokenFromCookie(request); // 쿠키에서 token 가져오기
		String userId = tokenProvider.validateAndGetUserId(token); // token이 유효한지 확인하고 userId 가져오기
		if (userId != null) {
			User user = service.getUser(userId);
			model.addAttribute("user", user);

			return "/user/edit";
		} else {
			return "index";
		}

	}

}