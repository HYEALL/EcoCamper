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
import com.example.EcoCamper.dto.OrderlistDTO;
import com.example.EcoCamper.entity.Buylist;
import com.example.EcoCamper.entity.Shop;
import com.example.EcoCamper.entity.User;
import com.example.EcoCamper.jwt.TokenProvider;
import com.example.EcoCamper.service.BuylistSevice;
import com.example.EcoCamper.service.ShopService;
import com.example.EcoCamper.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class BuylistController {
	
	@Autowired
	BuylistSevice service_buy;

	@Autowired
	UserService service_user;

	@Autowired
	ShopService service_shop;

	@Autowired
	private TokenProvider tokenProvider;

	@PostMapping("/shop/shoppay")
	public String pay(HttpServletRequest request, Model model, HttpSession session) {
		String token = tokenProvider.resolveTokenFromCookie(request);
		//System.out.println("token : " + token);
		String userId = null;
		if(token != null) {
			userId = tokenProvider.validateAndGetUserId(token);
		}
		User user = service_user.getUser(userId);
		// System.out.println(userId);

		String pcode = request.getParameter("pcode");
		
		//System.out.println(pcode);
		
		int productqty = Integer.parseInt(request.getParameter("productqty"));
		int productprice = Integer.parseInt(request.getParameter("productprice"));

		

		String receivename = request.getParameter("receivename");
		String baddr1 = request.getParameter("baddr1");
		String baddr2 = request.getParameter("baddr2");
		String bzipcode = request.getParameter("bzipcode");
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

		dto.setProductcode(pcode);
		dto.setProductqty(productqty);
		dto.setProductprice(productprice);

		

		dto.setReceivename(receivename);
		dto.setBzipcode(bzipcode);
		dto.setBaddr1(baddr1);
		dto.setBaddr2(baddr2);
		dto.setBphone(bphone);
		dto.setBpayment(bpayment);
		dto.setBcancel("N");
		dto.setLogtime(new Date());
		System.out.println(dto);
		Buylist result = service_buy.pay(dto);

		model.addAttribute("result", result);
		model.addAttribute("productqty", productqty);
		model.addAttribute("pcode", pcode);
		return "/shop/shoppay";
	}

	@PostMapping("/shop/shopcartpay")
	public String shopcartpay(HttpServletRequest request, Model model, HttpSession session) {
		String token = tokenProvider.resolveTokenFromCookie(request);
		//System.out.println("token : " + token);
		String userId = null;
		if(token != null) {
			userId = tokenProvider.validateAndGetUserId(token);
		}

		User user = service_user.getUser(userId);

		String cartItems = request.getParameter("cart-data");

		//System.out.println(cartItems);

		String receivename = request.getParameter("receivename");
		String baddr1 = request.getParameter("baddr1");
		String baddr2 = request.getParameter("baddr2");
		String bzipcode = request.getParameter("bzipcode");
		String bphone = request.getParameter("bphone");
		String bpayment = request.getParameter("bpayment");

			try {
				List<Map<String, Object>> paramMap = new ObjectMapper().readValue(cartItems,
						new TypeReference<List<Map<String, Object>>>() {
						});
				for (Map<String, Object> item : paramMap) {
					//System.out.println(item);
					BuylistDTO dto = new BuylistDTO();
					dto.setProductcode((String) item.get("code"));
					dto.setProductqty((Integer) item.get("quantity"));
					int total= (((Integer) item.get("price")) * ((Integer) item.get("quantity"))) ;
					//System.out.println(total);
					dto.setProductprice(total);
					
					Shop shopdto = service_shop.view(((String) item.get("code")));
					int bal = shopdto.getPqty()-dto.getProductqty() ;
					int salse=shopdto.getPhit();
					salse+=dto.getProductqty();
					shopdto.setPhit(salse);
					shopdto.setPqty(bal);
					
					dto.setBuyid(userId);
					
					dto.setReceivename(receivename);
					dto.setBzipcode(bzipcode);
					dto.setBaddr1(baddr1);
					dto.setBaddr1(baddr2);
					dto.setBphone(bphone);
					dto.setBcancel("N");
					dto.setBpayment(bpayment);
					
					dto.setLogtime(new Date());
	
					// DTO 출력 또는 필요한 작업 수행
					//System.out.println(dto);
					Buylist result = service_buy.pay(dto);
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
	
	@GetMapping("/shop/shopOrderList")
	public String orderList(HttpServletRequest request,Model model) {
		String token = tokenProvider.resolveTokenFromCookie(request);
		//System.out.println("token : " + token);
		String userId = null;
		if(token != null) {
			userId = tokenProvider.validateAndGetUserId(token);
		}
		
		int pg = 1;

		if (request.getParameter("pg") != null && !request.getParameter("pg").equals("")) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		
		int endNum = pg * 5;
		int startNum = endNum - 4;
		
		List<OrderlistDTO> list=service_buy.orderList(userId);
		int total = (int) service_buy.getTotal(userId); // 총 글 수
		int totalP = (total + 4) / 5; // 총 페이지

		int startPage = (pg - 1) / 3 * 3 + 1;
		int endPage = startPage + 2;
		if (endPage > totalP)
			endPage = totalP;
		
		int orderAllsum=service_buy.orderAllsum(userId);
		
		//System.out.println(total);
		//System.out.println(list);
		
		//System.out.println(orderAllsum);
		
		model.addAttribute("orderAllsum",orderAllsum);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("pg", pg);
		model.addAttribute("totalP", totalP);
		
		model.addAttribute("list", list);
		model.addAttribute("userId",userId);
		model.addAttribute("req", "/shop/shopOrderList");
		return "/index";
	}
	
	@PostMapping("/shop/shopOrderListDelete")
	public String shopbuyListDelete(HttpServletRequest request, Model model) {
		String buyseqs=request.getParameter("buyseqs");
		String pcodes=request.getParameter("pcodes");
		String productqtys=request.getParameter("productqtys");
		
		//System.out.println(buyseqs);
		//System.out.println(pcodes);
		//System.out.println(productqtys);
		
		
		String[] buyseqArray = buyseqs.split(",");
		
		boolean result=service_buy.shopOrderListDelete(buyseqArray);
		
		model.addAttribute("result",result);
		
		//주문취소시 다시 재고 중가 시켜야함
		String[] pcodeArray = pcodes.split(",");
	    String[] productqtyArray = productqtys.split(",");
		for (int i = 0; i < pcodeArray.length; i++) {
			String pcode = pcodeArray[i];
	        int productqty = Integer.parseInt(productqtyArray[i]);
	        
	        //System.out.println(pcode+productqty);
	        
	        Shop shopdto = service_shop.view(pcode);
	        //System.out.println(shopdto);
	        int bal = shopdto.getPqty() + productqty ;
			int salse=shopdto.getPhit();
			salse-=productqty;
			shopdto.setPhit(salse);
			shopdto.setPqty(bal);
			//System.out.println(shopdto);
	        
			Shop cancelresult=service_shop.save(shopdto);
		}
		
	    
	    
	    
	    
		
		return "/shop/shopOrderListDelete";
	}
	
	
	
	
	
	
}
