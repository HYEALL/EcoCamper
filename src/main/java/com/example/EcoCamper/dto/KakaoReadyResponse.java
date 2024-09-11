package com.example.EcoCamper.dto;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class KakaoReadyResponse { // 결제 요청시 카카오에게 받음
	private String tid; // 결제 고유 번호
    private String next_redirect_pc_url; // 결제 페이지
    private String created_at;
}
