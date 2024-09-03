package com.example.EcoCamper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.EcoCamper.dao.ShopDAO;
import com.example.EcoCamper.dto.ShopDTO;
import com.example.EcoCamper.entity.Shop;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class ShopController {
	
	@Autowired
	ShopDAO service;
	
	@Value("${project.upload.path}")
	private String uploadpath;
	
	@GetMapping("/shop/Zest")
	public String test() {
		return "/shop/Zest";
	}
	@GetMapping("/shop/Zest2")
	public String test2() {
		return "/shop/Zest2";
	}
	@GetMapping("/shop/Zest3")
	public String test3() {
		return "/shop/Zest3";
	}
	
	@GetMapping("/shop/shopmain")
	public String productmain(HttpServletRequest request) {
		/*
		int productqty=Integer.parseInt(request.getParameter("productqty")) ;
		String pcode=request.getParameter("pcode");
		if(productqty>0) {
			Shop dto=service.view(pcode);
			int beforeqty=dto.getPqty();
			int afterqty=beforeqty-productqty;
			service.update(afterqty);
		}
		*/
		return "/shop/shopmain";
	}
	@GetMapping("/shop/shopbuy")
	public String productbuy(HttpServletRequest request, Model model) {
		String pcode=request.getParameter("pcode");
		int productqty=Integer.parseInt(request.getParameter("productqty"));
		//System.out.println(productcode);
		
		Shop dto=service.view(pcode);
		
		 int productprice=dto.getPprice()*productqty;
		
		model.addAttribute("productprice",productprice);
		model.addAttribute("productqty",productqty);
		model.addAttribute("dto",dto);
		return "/shop/shopbuy";
	}
	@GetMapping("/shop/shopcart")
	public String productcart() {
		
		return "/shop/shopcart";
	}

	
	
	@GetMapping("/shop/shoprent")
	public String productrent() {
		
		return "/shop/shoprent";
	}
	@GetMapping("/shop/shopsearch")
	public String search() {
		
		return "/shop/shopsearch";
	}
	
	@GetMapping("/shop/shopmember")
	public String member() {
		
		return "/shop/shopmember";
	}
	@GetMapping("/shop/shopview")
	public String productview(HttpServletRequest request, Model model) {
		String pcode=request.getParameter("pcode");
		//System.out.println(productcode);
		
		Shop dto=service.view(pcode);
		model.addAttribute("dto",dto);
		return "/shop/shopview";
	}
	
}
