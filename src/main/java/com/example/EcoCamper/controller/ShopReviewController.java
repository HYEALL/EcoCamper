package com.example.EcoCamper.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.EcoCamper.dao.ShopReviewDAO;
import com.example.EcoCamper.dto.ShopReviewDTO;
import com.example.EcoCamper.entity.ShopReview;
import com.example.EcoCamper.jwt.TokenProvider;
import com.example.EcoCamper.service.ShopReviewService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ShopReviewController {
	
	@Autowired
	ShopReviewService service;
	
	@Autowired
	private TokenProvider tokenProvider;
	
	@PostMapping("/shop/shopReview")
	public String shopReview(HttpServletRequest request, Model model) {
		String token = tokenProvider.resolveTokenFromCookie(request);
		//System.out.println("token : " + token);
		String userId = tokenProvider.validateAndGetUserId(token);
		
		String reviewtext=request.getParameter("reviewText");
		String pcode = request.getParameter("pcode");
		
		/*
		System.out.println(pcode);
		System.out.println(userId);
		System.out.println(reviewtext);*/
		
		ShopReviewDTO dto=new ShopReviewDTO();
		dto.setShopreviewpcode(pcode);
		dto.setShopreviewid(userId);
		dto.setShopreviewcontent(reviewtext);
		dto.setShopreviewhit(0);
		dto.setLogtime(new Date());
		
		System.out.println(dto);
		ShopReview result=service.write(dto);
		System.out.println("result"+result);
		
		model.addAttribute("result",result);
		model.addAttribute("dto",dto);
		
		return "/shop/shopReview";
	}
	
}
