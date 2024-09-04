package com.example.EcoCamper.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.EcoCamper.entity.User;
import com.example.EcoCamper.service.UserService;


import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdminController {
	@Autowired
	UserService service;

	@GetMapping("/admin/userList")
	public String userList(@RequestParam(value = "pg", defaultValue = "0") int pg, Model model) {
		// int totalPages = service.getTotalA();
		Page<User> list = service.findAll(pg, 7);
		model.addAttribute("list", list.getContent());
		model.addAttribute("pg", pg);
		model.addAttribute("totalPages", list.getTotalPages() - 1);
		model.addAttribute("startPage", Math.max(0, list.getPageable().getPageNumber() - 10));
		// model.addAttribute("endPage", Math.min(list.getTotalPages(), pg + 2));
		model.addAttribute("endPage", Math.min(list.getTotalPages() - 1, list.getPageable().getPageNumber() + 10));
		return "/admin/userList";
	}

	@GetMapping("/admin/userView")
	public String userView(Model model, HttpServletRequest request) {
		String id = request.getParameter("id");
		int pg = Integer.parseInt(request.getParameter("pg"));

		User user = service.getUser(id);

		model.addAttribute("pg", pg);
		model.addAttribute("user", user);

		return "/admin/userView";
	}
}
