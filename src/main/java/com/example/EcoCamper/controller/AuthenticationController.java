package com.example.EcoCamper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {
	
	@GetMapping("/authorization-fail")
	public String authorizationFail() {
		return "/authorization-fail";
	}
}
