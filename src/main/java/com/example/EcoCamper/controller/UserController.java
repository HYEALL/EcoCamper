package com.example.EcoCamper.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.EcoCamper.dto.UserDTO;
import com.example.EcoCamper.entity.Feed;
import com.example.EcoCamper.entity.Shop;
import com.example.EcoCamper.entity.User;
import com.example.EcoCamper.jwt.KakaoApi;
import com.example.EcoCamper.jwt.TokenProvider;
import com.example.EcoCamper.service.FeedService;
import com.example.EcoCamper.service.MapService;
import com.example.EcoCamper.service.ShopService;
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
	@Autowired
	ShopService shopService;
	@Autowired
	private KakaoApi kakaoApi;
	
	@Autowired
	FeedService feedService;
	
	@Autowired
	MapService mapService;

	// 카카오 로그인
	@GetMapping(value = "/login/oauth2/code/kakao")
	public String kakaoLogin(@RequestParam(value = "code") String code, HttpServletResponse response, Model model) {
		// 인가 코드 받기
		String accessToken = kakaoApi.getAccessToken(code);
		// 사용자 정보 받기
		Map<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);
		User kakaoUser = service.kakaoLogin(userInfo);
		String email = (String) userInfo.get("email");
		String nickname = (String) userInfo.get("nickname");

		// 카카오 email로 정보가 있으면 로그인
		if (kakaoUser != null) {
			// 토큰 생성
			final String token = tokenProvider.createToken(kakaoUser);
			// JWT 토큰을 HttpOnly 쿠키에 저장
			ResponseCookie cookie = ResponseCookie.from("token", token).httpOnly(true) // 자바스크립트 접근 불가
					.secure(true) // HTTPS에서만 전송
					.path("/").maxAge(24 * 60 * 60) // 쿠키 유효기간 설정(1일)
					.sameSite("Strict") // SameSite 속성 설정
					.build();

			response.setHeader("Set-Cookie", cookie.toString());

			System.out.println("kakao login: " + token);
			return "redirect:/index";
			// 카카오 email로 정보가 없으면 회원가입
		} else {
			model.addAttribute("req", "/user/joinForm");
			model.addAttribute("email", email);
			model.addAttribute("name", nickname);
			return "/index";
		}

		/*
		 * System.out.println("email = " + email); System.out.println("nickname = " +
		 * nickname); System.out.println("accessToken = " + accessToken);
		 */

	}

	// 로그인
	@ResponseBody
	@PostMapping(value = "/login")
	public String login(@RequestBody UserDTO userDTO, HttpServletResponse response) {
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

			System.out.println("login : " + token);
			return "Login Success";

		} else {
			// UserResponseDTO responseDTO = UserResponseDTO.builder().error("Login
			// failed.").build();
			return "Login Failed";
		}

	}

	@GetMapping("/loginForm")
	public String loginForm(Model model) {
		model.addAttribute("req", "/user/loginForm");
		return "/index";
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
	public String joinForm(Model model) {
		model.addAttribute("req", "/user/joinForm");
		return "/index";
	}

	@PostMapping("/join")
	public String join(UserDTO userDTO, Model model, HttpServletRequest request) {
		String birthYear = request.getParameter("birthYear");
		String birthMonth = request.getParameter("birthMonth");
		String birthDay = request.getParameter("birthDay");
		String birthDate = birthYear + "/" + birthMonth + "/" + birthDay;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Date date = formatter.parse(birthDate);
			userDTO.setAge(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userDTO.setRole("USER");
		User user = service.join(userDTO);

		model.addAttribute("user", user);
		return "/user/join";
	}

	@GetMapping("/index")
	public String index(HttpServletRequest request, Model model) {
		String token = tokenProvider.resolveTokenFromCookie(request); // 쿠키에서 token 가져오기
		String userId = null;
		if (token != null) {
			userId = tokenProvider.validateAndGetUserId(token); // token이 유효한지 확인하고 userId 가져오기
			if (userId != null) {
				model.addAttribute("userId", userId);
			}
		}

		List<Shop> list_shop = shopService.shopTop();
		List<Feed> list_feed = feedService.feedTop();
		List<com.example.EcoCamper.entity.Map> list_map = mapService.getLatestFivePlaces();
		
		//System.out.println(list_shop);
		//System.out.println(list_feed);
		//System.out.println(list_map);
		model.addAttribute("list_shop", list_shop);
		model.addAttribute("list_feed", list_feed);
		model.addAttribute("list_map", list_map);

		return "/index";
	}

	@GetMapping("/menu")
	public String menu() {
		return "/menu";

	}

	@GetMapping("/myPage")
	public String myPage(@RequestParam(value = "mp", required = false) String mp, Model model,
			HttpServletRequest request) {
		String token = tokenProvider.resolveTokenFromCookie(request); // 쿠키에서 token 가져오기
		String userId = null;
		if (token != null) {
			userId = tokenProvider.validateAndGetUserId(token); // token이 유효한지 확인하고 userId 가져오기
			if (userId != null) {
				User user = service.getUser(userId);
				model.addAttribute("userId", userId);
				model.addAttribute("user", user);
				model.addAttribute("req", "/user/myPage");
				model.addAttribute("mp", mp);
				return "/index";
			} else {
				model.addAttribute("req", "/user/loginForm");
				return "/index";
			}
		} else {
			model.addAttribute("req", "/user/loginForm");
			return "/index";
		}

	}

	@GetMapping("/user/editForm")
	public String editForm(Model model, HttpServletRequest request) {
		String token = tokenProvider.resolveTokenFromCookie(request); // 쿠키에서 token 가져오기
		String userId = tokenProvider.validateAndGetUserId(token); // token이 유효한지 확인하고 userId 가져오기
		if (userId != null) {
			User user = service.getUser(userId);
			System.out.println(user);
			model.addAttribute("user", user);
			model.addAttribute("req", "/user/editForm");
			return "/index";
		} else {
			return "/index";
		}

	}

	@PostMapping("/edit")
	public String edit(UserDTO userDTO, Model model, HttpServletRequest request) {

		boolean result = service.userModify(userDTO);
		model.addAttribute("result", result);
		return "/user/edit";
	}

}