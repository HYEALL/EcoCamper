package com.example.EcoCamper.controller;

import java.net.http.HttpRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.EcoCamper.dao.ShopDAO;
import com.example.EcoCamper.dao.ShopReviewDAO;
import com.example.EcoCamper.dto.ShopDTO;
import com.example.EcoCamper.entity.Shop;
import com.example.EcoCamper.entity.ShopReview;
import com.example.EcoCamper.entity.User;
import com.example.EcoCamper.jwt.TokenProvider;
import com.example.EcoCamper.service.BuylistSevice;
import com.example.EcoCamper.service.ShopReviewService;
import com.example.EcoCamper.service.ShopService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class ShopController {
	
	@Autowired
	ShopService service_shop;
	
	@Autowired
	ShopReviewService serveice_re;
	
	@Autowired
	BuylistSevice service_buy;
	
	@Value("${project.upload.path}")
	private String uploadpath;
	
	@Autowired
	private TokenProvider tokenProvider;
	
	
	
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
	public String productmain(HttpServletRequest request,Model model) {
		String token = tokenProvider.resolveTokenFromCookie(request);
		//System.out.println("token : " + token);
		String userId = null;
		if(token != null) {
			userId = tokenProvider.validateAndGetUserId(token);
			
		} 
		model.addAttribute("userId",userId);
		
		return "/shop/shopmain";
	}
	@GetMapping("/shop/shopbuy")
	public String productbuy(HttpServletRequest request, Model model) {
		String pcode=request.getParameter("pcode");
		int productqty=Integer.parseInt(request.getParameter("productqty"));
		//System.out.println(productcode);
		
		
		Shop dto=service_shop.view(pcode);
		
		 int productprice=dto.getPprice()*productqty;
		
		model.addAttribute("productprice",productprice);
		model.addAttribute("productqty",productqty);
		model.addAttribute("dto",dto);
		return "/shop/shopbuy";
	}
	@GetMapping("/shop/shopcart")
	public String productcart(HttpServletRequest request, Model model) {
		String token = tokenProvider.resolveTokenFromCookie(request);
		//System.out.println("token : " + token);
		String userId = null;
		if(token != null) {
			userId = tokenProvider.validateAndGetUserId(token);
			
		} 
		model.addAttribute("userId",userId);
		return "/shop/shopcart";
	}

	
	@GetMapping("/shop/shopcartbuy")
	public String shopcartbuy() {

		return "/shop/shopcartbuy";
	}
	
	
	@GetMapping("/shop/shopview")
	public String productview(HttpServletRequest request, Model model) {
	//just view	
		String pcode=request.getParameter("pcode");
		//System.out.println(productcode);
		Shop dto=service_shop.view(pcode);
		
		
		String token = tokenProvider.resolveTokenFromCookie(request);
		String userId = null;
		boolean order = false;
		if (token != null) {
			userId = tokenProvider.validateAndGetUserId(token);
			// pcode랑 주문내역 pcode, id가 같으면 true, pcode 비교후 true 리턴
			order = service_buy.findByPcodeAndBuyId(pcode, userId);
			
		}

		
	//review
		int pg = 1;

		if (request.getParameter("pg") != null && !request.getParameter("pg").equals("")) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}

		
		int endNum = pg * 5;
		int startNum = endNum - 4;
		
		List<ShopReview> list=serveice_re.reveiwList(startNum,endNum, pcode);
		
		int total = (int) serveice_re.getTotal(pcode); // 총 글 수
		int totalP = (total + 4) / 5; // 총 페이지

		int startPage = (pg - 1) / 3 * 3 + 1;
		int endPage = startPage + 2;
		if (endPage > totalP)
			endPage = totalP;
		
		//System.out.println(total);
		//System.out.println(list);
		
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("pg", pg);
		model.addAttribute("totalP", totalP);
		model.addAttribute("list", list);
		
		model.addAttribute("dto",dto);

		model.addAttribute("pcode",pcode);
		model.addAttribute("userId",userId);
		model.addAttribute("order", order);
		
		return "/shop/shopview";
	}
	
	@GetMapping("/shop/shopAllList")
	public String shopAllList() {
		
		return "/shop/shopAllList";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
