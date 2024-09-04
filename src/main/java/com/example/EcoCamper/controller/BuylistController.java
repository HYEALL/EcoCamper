package com.example.EcoCamper.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.EcoCamper.dao.BuylistDAO;
import com.example.EcoCamper.dto.BuylistDTO;
import com.example.EcoCamper.dto.ShopDTO;
import com.example.EcoCamper.entity.Buylist;
import com.example.EcoCamper.entity.User;
import com.example.EcoCamper.jwt.TokenProvider;
import com.example.EcoCamper.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class BuylistController {

	@Autowired
	BuylistDAO service1;

	@Autowired
	UserService service2;

	@Autowired
	private TokenProvider tokenProvider;

	@PostMapping("/shop/shoppay")
	public String nomalpay(HttpServletRequest request, Model model, HttpSession session) {

		String token = tokenProvider.resolveTokenFromCookie(request);
		System.out.println("token : " + token);
		String userId = tokenProvider.validateAndGetUserId(token);

		User user = service2.getUser(userId);
		System.out.println(userId);

		String pcode = request.getParameter("pcode");
		String productname = request.getParameter("productname");
		int productqty = Integer.parseInt(request.getParameter("productqty"));
		int productprice = Integer.parseInt(request.getParameter("productprice"));

		String buyername = user.getName();

		String receivename = request.getParameter("receivename");
		String baddress = request.getParameter("baddress");
		String bphone = request.getParameter("bphone");
		String bpayment = request.getParameter("bpayment");

		BuylistDTO dto = new BuylistDTO();
		/* 테스트 할데 buylist id 값 변경해가면서 해야 추가된다. 안그럼 같은 아아디값이면 수정된다. */
		dto.setBuyid(userId);

		dto.setProductname(productname);
		dto.setProductqty(productqty);
		dto.setProductprice(productprice);

		dto.setBuyername(buyername);
		System.out.println(buyername);

		dto.setReceivename(receivename);
		dto.setBaddress(baddress);
		dto.setBphone(bphone);
		dto.setBpayment(bpayment);
		dto.setLogtime(new Date());

		Buylist result = service1.pay(dto);

		model.addAttribute("result", result);
		model.addAttribute("productqty", productqty);
		model.addAttribute("pcode", pcode);
		return "/shop/shoppay";
	}

	@PostMapping("/shop/shopcartpay")
	public String shopcartpay(HttpServletRequest request, Model model, HttpSession session) {

		String token = tokenProvider.resolveTokenFromCookie(request);
		System.out.println("token : " + token);
		String userId = tokenProvider.validateAndGetUserId(token);

		User user = service2.getUser(userId);

		// JSON 문자열로 된 cartItems를 파싱하여 JSONArray로 변환
		String cartItems = request.getParameter("cart-data");

		System.out.println(cartItems);

		/*
		 * [{"code":"0001","name":"친환경세제","img":"cleaner.jpg","price":3000,"quantity":2}
		 * , {"code":"0002","name":"친환경휴지","img":"roll.jpg","price":10000,"quantity":3},
		 * {"code":"0003","name":"물아이스팩","img":"water.jpg","price":4000,"quantity":4}]
		 */


		String buyername = user.getName();
		String receivename = request.getParameter("receivename");
		String baddress = request.getParameter("baddress");
		String bphone = request.getParameter("bphone");
		String bpayment = request.getParameter("bpayment");

		// String productname= request.getParameter("productname");
		// int productqty = Integer.parseInt(request.getParameter("productqty")) ;
		// int productprice=Integer.parseInt(request.getParameter("productprice")) ;

		return "/shop/shopcartpay";
	}
}
