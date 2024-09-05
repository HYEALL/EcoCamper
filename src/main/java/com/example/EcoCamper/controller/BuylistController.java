package com.example.EcoCamper.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.EcoCamper.dao.BuylistDAO;
import com.example.EcoCamper.dao.ShopDAO;
import com.example.EcoCamper.dto.BuylistDTO;
import com.example.EcoCamper.dto.ShopDTO;
import com.example.EcoCamper.entity.Buylist;
import com.example.EcoCamper.entity.Shop;
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
	ShopDAO service_shop;

	@Autowired
	private TokenProvider tokenProvider;

	@PostMapping("/shop/shoppay")
	public String pay(HttpServletRequest request, Model model, HttpSession session) {

		String token = tokenProvider.resolveTokenFromCookie(request);
		System.out.println("token : " + token);
		String userId = tokenProvider.validateAndGetUserId(token);

		User user = service2.getUser(userId);
		// System.out.println(userId);

		String pcode = request.getParameter("pcode");
		
		//System.out.println(pcode);
		
		String productname = request.getParameter("productname");
		int productqty = Integer.parseInt(request.getParameter("productqty"));
		int productprice = Integer.parseInt(request.getParameter("productprice"));

		String buyername = user.getName();

		String receivename = request.getParameter("receivename");
		String baddress = request.getParameter("baddress");
		String bphone = request.getParameter("bphone");
		String bpayment = request.getParameter("bpayment");

		/* 주문결제 동시에 재고 테이블 수량 감소 */
		Shop shopdto = service_shop.view(pcode);
		int bal = shopdto.getPqty() - productqty;
		int salse=shopdto.getPhit();
		salse+=productqty;
		shopdto.setPhit(salse);
		shopdto.setPqty(bal);

		BuylistDTO dto = new BuylistDTO();
		/* 테스트 할데 buylist id 값 변경해가면서 해야 추가된다. 안그럼 같은 아아디값이면 수정된다. */
		dto.setBuyid(userId);

		dto.setProductname(productname);
		dto.setProductqty(productqty);
		dto.setProductprice(productprice);

		dto.setBuyername(buyername);

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

		String cartItems = request.getParameter("cart-data");

		//System.out.println(cartItems);

		String buyername = user.getName();
		String receivename = request.getParameter("receivename");
		String baddress = request.getParameter("baddress");
		String bphone = request.getParameter("bphone");
		String bpayment = request.getParameter("bpayment");

			try {
				List<Map<String, Object>> paramMap = new ObjectMapper().readValue(cartItems,
						new TypeReference<List<Map<String, Object>>>() {
						});
				for (Map<String, Object> item : paramMap) {
					BuylistDTO dto = new BuylistDTO();
					dto.setProductname((String) item.get("name"));
					dto.setProductprice((Integer) item.get("price"));
					dto.setProductqty((Integer) item.get("quantity"));
					
					Shop shopdto = service_shop.view(((String) item.get("code")));
					int bal = shopdto.getPqty()-dto.getProductqty() ;
					int salse=shopdto.getPhit();
					salse+=dto.getProductqty();
					shopdto.setPhit(salse);
					shopdto.setPqty(bal);
					
					dto.setBuyid(userId);
					dto.setBuyername(buyername);
					dto.setReceivename(receivename);
					dto.setBaddress(baddress);
					dto.setBphone(bphone);
					dto.setBpayment(bpayment);
					dto.setLogtime(new Date());
	
					// DTO 출력 또는 필요한 작업 수행
					//System.out.println(dto);
					
					Buylist result = service1.pay(dto);
					model.addAttribute("result", result);
				}
	
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

		// String productname= request.getParameter("productname");
		// int productqty = Integer.parseInt(request.getParameter("productqty")) ;
		// int productprice=Integer.parseInt(request.getParameter("productprice")) ;
		
			
			
			
		return "/shop/shopcartpay";
	}
}
