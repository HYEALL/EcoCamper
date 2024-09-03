package com.example.EcoCamper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.EcoCamper.service.UserService;

@Controller
public class AdminController {
	@Autowired
	UserService service;
	
	@GetMapping("/admin/userList")
	public String userList() {
		return "/admin/userList";
	}
}
