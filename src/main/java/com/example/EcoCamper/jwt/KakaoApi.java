package com.example.EcoCamper.jwt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KakaoApi {
	@Value("${kakao.api_key}")
	private String kakaoApiKey;

	@Value("${kakao.redirect_uri}")
	private String kakaoRedirectUri;

	// 인가 코드를 받아서 accessToken을 반환
	public String getAccessToken(String code) {
		String accessToken = "";
		String reqUrl = "https://kauth.kakao.com/oauth/token";

		try {
			URL url = new URL(reqUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// 필수 헤더 세팅
			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
			conn.setDoOutput(true); // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();

			// 필수 쿼리 파라미터 세팅
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=").append(kakaoApiKey);
			sb.append("&redirect_uri=").append(kakaoRedirectUri);
			sb.append("&code=").append(code);

			bw.write(sb.toString());
			bw.flush();

			int responseCode = conn.getResponseCode();
			log.info("[KakaoApi.getAccessToken] responseCode = {}", responseCode);

			BufferedReader br;
			if (responseCode >= 200 && responseCode < 300) {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}

			String line = "";
			StringBuilder responseSb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				responseSb.append(line);
			}
			String result = responseSb.toString();
			log.info("responseBody = {}", result);

			JsonElement element = JsonParser.parseString(result);
			JsonObject jsonObject = element.getAsJsonObject();

			accessToken = jsonObject.get("access_token").getAsString();

			br.close();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accessToken;
	}

	// accessToken을 받아서 UserInfo 반환
	public HashMap<String, Object> getUserInfo(String accessToken) {
		HashMap<String, Object> userInfo = new HashMap<>();
		String reqUrl = "https://kapi.kakao.com/v2/user/me";
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);
			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

			int responseCode = conn.getResponseCode();
			log.info("[KakaoApi.getUserInfo] responseCode : {}", responseCode);

			BufferedReader br;
			if (responseCode >= 200 && responseCode <= 300) {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}

			String line = "";
			StringBuilder responseSb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				responseSb.append(line);
			}
			String result = responseSb.toString();
			log.info("responseBody = {}", result);

			JsonElement element = JsonParser.parseString(result);

			JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
			JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

			String nickname = properties.getAsJsonObject().get("nickname").getAsString();
			String email = kakaoAccount.getAsJsonObject().get("email").getAsString();

			userInfo.put("nickname", nickname);
			userInfo.put("email", email);

			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return userInfo;
	}

	// accessToken을 받아서 로그아웃 시키는 메서드
	public void kakaoLogout(String accessToken) {
		String reqUrl = "https://kapi.kakao.com/v1/user/logout";

		try {
			URL url = new URL(reqUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);

			int responseCode = conn.getResponseCode();
			log.info("[KakaoApi.kakaoLogout] responseCode : {}", responseCode);

			BufferedReader br;
			if (responseCode >= 200 && responseCode <= 300) {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}

			String line = "";
			StringBuilder responseSb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				responseSb.append(line);
			}
			String result = responseSb.toString();
			log.info("kakao logout - responseBody = {}", result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}