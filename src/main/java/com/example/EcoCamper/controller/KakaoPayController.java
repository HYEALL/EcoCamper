package com.example.EcoCamper.controller;

import java.net.URI;
import java.util.Date;

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
import com.example.EcoCamper.service.BuylistSevice;
import com.example.EcoCamper.service.KakaoPayService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class KakaoPayController {
	@Autowired
	BuylistSevice BuylistService;
	private final KakaoPayService kakaoPayService;

	/**
	 * 결제요청
	 */
	@PostMapping("/ready")
	public ResponseEntity readyToKakaoPay(BuylistDTO dto, HttpServletRequest request) {
		dto.setBuyid(request.getParameter("userId"));
		dto.setProductcode(request.getParameter("pcode"));
		dto.setLogtime(new Date());
		dto.setBcancel("N");
		Buylist buylist = BuylistService.pay(dto); // 일단 저장하고 실패하면 cancel yes로
		System.out.println(dto);
		KakaoReadyResponse kakaoReadyResponse = kakaoPayService.kakaoPayReady(buylist);
		// 결제 화면으로 리다이렉트할 URL
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create(kakaoReadyResponse.getNext_redirect_pc_url()));

		return new ResponseEntity<>(headers, HttpStatus.FOUND);
	}

	/**
	 * 결제 성공
	 */
	@GetMapping("/success")
	public ModelAndView afterPayRequest(@RequestParam("pg_token") String pgToken,
			@RequestParam("partner_order_id") String partnerOrderId, Model model) {

		KakaoApproveResponse kakaoApprove = kakaoPayService.approveResponse(pgToken, partnerOrderId);
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