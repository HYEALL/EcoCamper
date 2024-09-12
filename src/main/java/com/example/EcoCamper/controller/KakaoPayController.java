package com.example.EcoCamper.controller;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.EcoCamper.dto.BuylistDTO;
import com.example.EcoCamper.dto.KakaoApproveResponse;
import com.example.EcoCamper.dto.KakaoCancelResponse;
import com.example.EcoCamper.dto.KakaoReadyResponse;
import com.example.EcoCamper.entity.Buylist;
import com.example.EcoCamper.entity.Shop;
import com.example.EcoCamper.service.BuylistSevice;
import com.example.EcoCamper.service.KakaoPayService;
import com.example.EcoCamper.service.ShopService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class KakaoPayController {
	@Autowired
	BuylistSevice BuylistService;
	@Autowired
	ShopService shopService;
	private final KakaoPayService kakaoPayService;

	/**
	 * 결제요청
	 */
	@PostMapping("/ready")
	public ResponseEntity readyToKakaoPay(BuylistDTO dto, HttpServletRequest request) {
		dto.setBuyid(request.getParameter("userId"));
		dto.setProductcode(request.getParameter("pcode"));
		dto.setLogtime(new Date());
		dto.setBcancel("Y");
		Buylist buylist = BuylistService.pay(dto); // 일단 cacel y로 저장하고 성공하면 n으로
		KakaoReadyResponse kakaoReadyResponse = kakaoPayService.kakaoPayReady(buylist);
		// 결제 화면으로 리다이렉트할 URL
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create(kakaoReadyResponse.getNext_redirect_pc_url()));

		return new ResponseEntity<>(headers, HttpStatus.FOUND);
	}
	/**
	 * 장바구니 결제요청
	 */
	@PostMapping("/cartReady")
	public ResponseEntity cartReadyToKakaoPay(HttpServletRequest request) {
		String cartItems = request.getParameter("cart-data");
		
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
				int totalPrice = 0; // 총 합계
		        int totalQuantity = 0; // 총 수량
		        int finalSeq = 0; // 마지막 주문번호
		        String finalPcode = ""; // 마지막 상품이름
				for (Map<String, Object> item : paramMap) {
					BuylistDTO dto = new BuylistDTO();
		            dto.setProductcode((String) item.get("code"));
		            int quantity = (Integer) item.get("quantity");
		            int price = (Integer) item.get("price");
		            int total = price * quantity;

		            dto.setProductqty(quantity);
		            dto.setProductprice(total);

		            totalPrice += total; // 합계에 추가
		            totalQuantity += quantity; // 수량에 추가

		            Shop shopdto = shopService.view(((String) item.get("code")));
		            int bal = shopdto.getPqty() - quantity;
		            int salse = shopdto.getPhit();
		            salse += quantity;
		            shopdto.setPhit(salse);
		            shopdto.setPqty(bal);

		            dto.setBuyid(request.getParameter("userId"));
		            dto.setReceivename(receivename);
		            dto.setBzipcode(bzipcode);
		            dto.setBaddr1(baddr1);
		            dto.setBaddr2(baddr2); // baddr2로 수정
		            dto.setBphone(bphone);
		            dto.setBcancel("Y"); // 일단 cancel Y로 저장하고 성공하면 N으로
		            dto.setBpayment(bpayment);
		            dto.setLogtime(new Date());

		            Buylist buylist = BuylistService.pay(dto);
		            finalPcode = buylist.getProductcode();
		            finalSeq = buylist.getBuyseq();
		        }

		        // 총 합계와 총 수량을 kakaopay에 전달할 finalDto에 설정
		        Buylist finalDto = new Buylist();
		        finalDto.setBuyseq(finalSeq);
		        finalDto.setProductcode(finalPcode+" 외 "+(totalQuantity-1)+"개");
		        finalDto.setProductprice(totalPrice);
		        finalDto.setProductqty(totalQuantity);
		        finalDto.setReceivename(receivename);
		        finalDto.setBzipcode(bzipcode);
		        finalDto.setBaddr1(baddr1);
		        finalDto.setBaddr2(baddr2);
		        finalDto.setBphone(bphone);
		        finalDto.setBpayment(bpayment);
		        finalDto.setBuyid(request.getParameter("userId"));
		        finalDto.setLogtime(new Date());
		        
		        KakaoReadyResponse kakaoReadyResponse = kakaoPayService.kakaoPayReady(finalDto);
		        // 결제 화면으로 리다이렉트할 URL
		        HttpHeaders headers = new HttpHeaders();
		        headers.setLocation(URI.create(kakaoReadyResponse.getNext_redirect_pc_url()));

		        return new ResponseEntity<>(headers, HttpStatus.FOUND);

		    } catch (JsonProcessingException e) {
		        e.printStackTrace();
		        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	}
	/**
	 * 결제 성공
	 */
	@GetMapping("/success")
	public ModelAndView afterPayRequest(@RequestParam("pg_token") String pgToken,
			@RequestParam("partner_order_id") String partnerOrderId, @RequestParam("qty") String qty, Model model) {

		KakaoApproveResponse kakaoApprove = kakaoPayService.approveResponse(pgToken, partnerOrderId, qty);
		//model.addAttribute("result", true);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/shop/shoppay");
		//modelAndView.addObject("kakaoApprove", kakaoApprove);
		modelAndView.addObject("result", true);
		return modelAndView;
		// return ResponseEntity.status(HttpStatus.FOUND)
		// .location(URI.create("/shop/shoppay"))
		// .build();
	}

	/**
	 * 결제 진행 중 취소
	 */
	@GetMapping("/cancel")
	public ResponseEntity<String> cancel() {
		return new ResponseEntity<>("Payment has been cancelled.", HttpStatus.BAD_REQUEST);
	}

	/**
	 * 결제 실패
	 */
	@GetMapping("/fail")
	public ResponseEntity<String> fail() {
		return new ResponseEntity<>("Payment failed. Please try again.", HttpStatus.BAD_REQUEST);
	}
	/*
	 * 
	 * // 환불
	 * 
	 * @PostMapping("/refund") public ResponseEntity refund() {
	 * 
	 * KakaoCancelResponse kakaoCancelResponse = kakaoPayService.kakaoCancel();
	 * 
	 * return new ResponseEntity<>(kakaoCancelResponse, HttpStatus.OK); }
	 */
}