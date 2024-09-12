package com.example.EcoCamper.service;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.EcoCamper.dto.BuylistDTO;
import com.example.EcoCamper.dto.KakaoApproveResponse;
import com.example.EcoCamper.dto.KakaoCancelResponse;
import com.example.EcoCamper.dto.KakaoReadyResponse;
import com.example.EcoCamper.entity.Buylist;
import com.example.EcoCamper.repository.BuylistRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoPayService {
	private final BuylistRepository buylistRepository;
	static final String cid = "TC0ONETIME"; // 가맹점 테스트 코드

	@Value("${kakao.admin_key}") // admin key에서 scret key로 변경
	private String admin_Key;
	@Value("${kakaopay.secret_key}")
	private String secret_Key;
	private KakaoReadyResponse kakaoReady;

	public KakaoReadyResponse kakaoPayReady(Buylist buylist) {
		// 카카오페이 요청 양식
		Map<String, String> parameters = new HashMap<>();
		parameters.put("cid", cid);
		parameters.put("partner_order_id", Integer.toString(buylist.getBuyseq())); // 주문번호
		parameters.put("partner_user_id", buylist.getBuyid()); // 유저 아이디
		parameters.put("item_name", buylist.getProductcode()); // 상품명
		parameters.put("quantity", Integer.toString(buylist.getProductqty())); // 수량
		parameters.put("total_amount", Integer.toString(buylist.getProductprice())); // 총금액
		parameters.put("vat_amount", "0"); // 부가세
		parameters.put("tax_free_amount", "0"); // 비과세 금액
		parameters.put("approval_url", "http://localhost:8080/payment/success?partner_order_id=" + buylist.getBuyseq()
				+ "&qty=" + buylist.getProductqty()); // 성공 시 redirect url
		parameters.put("cancel_url", "http://localhost:8080/payment/cancel"); // 취소 시 redirect url
		parameters.put("fail_url", "http://localhost:8080/payment/fail"); // 실패 시 redirect url

		// 파라미터, 헤더
		HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
		// 외부에 보낼 url
		RestTemplate restTemplate = new RestTemplate();

		kakaoReady = restTemplate.postForObject("https://open-api.kakaopay.com/online/v1/payment/ready", requestEntity,
				KakaoReadyResponse.class);

		return kakaoReady;

	}

	/**
	 * 결제 완료 승인
	 */
	public KakaoApproveResponse approveResponse(String pgToken, String partnerOrderId, String qty) {
		Buylist currentbuylist = buylistRepository.findById(Integer.parseInt(partnerOrderId)).orElse(null);
		int pQty = Integer.parseInt(qty);
		// 카카오 요청
		Map<String, String> parameters = new HashMap<>();
		parameters.put("cid", cid);
		parameters.put("tid", kakaoReady.getTid());
		parameters.put("partner_order_id", partnerOrderId);
		parameters.put("partner_user_id", currentbuylist.getBuyid());
		parameters.put("pg_token", pgToken);

		// 파라미터, 헤더
		HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

		// 외부에 보낼 url
		RestTemplate restTemplate = new RestTemplate();

		KakaoApproveResponse approveResponse = restTemplate.postForObject(
				"https://open-api.kakaopay.com/online/v1/payment/approve", requestEntity, KakaoApproveResponse.class);

		for (int i = 0; i < pQty; i++) {

			Buylist buylist = buylistRepository.findById((Integer.parseInt(partnerOrderId)) - i).orElse(null);
			for (int j = 1; j < buylist.getProductqty(); j++) {
				i += 1;

			}

			if (buylist != null) {
				buylist.setBcancel("N");
				buylistRepository.save(buylist);
			}
		}

		return approveResponse;
	}

	/**
	 * 결제 환불
	 * 
	 * public KakaoCancelResponse kakaoCancel() {
	 * 
	 * // 카카오페이 요청 Map<String, String> parameters = new HashMap<>();
	 * parameters.put("cid", cid); parameters.put("tid", "환불할 결제 고유 번호");
	 * parameters.put("cancel_amount", "환불 금액");
	 * parameters.put("cancel_tax_free_amount", "환불 비과세 금액");
	 * parameters.put("cancel_vat_amount", "환불 부가세");
	 * 
	 * // 파라미터, 헤더 HttpEntity<Map<String, String>> requestEntity = new
	 * HttpEntity<>(parameters, this.getHeaders());
	 * 
	 * // 외부에 보낼 url RestTemplate restTemplate = new RestTemplate();
	 * 
	 * KakaoCancelResponse cancelResponse = restTemplate.postForObject(
	 * "https://open-api.kakaopay.com/online/v1/payment/cancel", requestEntity,
	 * KakaoCancelResponse.class);
	 * 
	 * return cancelResponse; }
	 */
	/**
	 * 카카오 요구 헤더값
	 */
	private HttpHeaders getHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();

		String auth = "SECRET_KEY " + secret_Key;

		httpHeaders.set("Authorization", auth);
		httpHeaders.set("Content-type", "application/json");
		return httpHeaders;
	}

}
