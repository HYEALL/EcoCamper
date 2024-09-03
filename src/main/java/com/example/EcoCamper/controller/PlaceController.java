package com.example.EcoCamper.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.EcoCamper.dao.PlaceDAO;
import com.example.EcoCamper.entity.Place;
import com.example.EcoCamper.jwt.TokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@Controller
public class PlaceController {
    @Autowired
    PlaceDAO dao;
    
    @Autowired
	private TokenProvider tokenProvider;
    
    @Value("${project.upload.path}")
    private String uploadpath;

    @GetMapping("/map_main")
    public String map(Model model, HttpServletRequest request) {
    	// 로그인된 아이디를 토큰에 저장
    	String token = tokenProvider.resolveTokenFromCookie(request);
		System.out.println("token : " + token);
	    String userId = tokenProvider.validateAndGetUserId(token);
	    
	    // db에 있는 정보 다 읽어오기
	    List<Place> list = dao.placeList();
	    System.out.println(list);
	    System.out.println(list.get(0));
	    
	    //  데이터 공유
	    
	    // 데이터 공유 (JSON으로 변환)
	    ObjectMapper objectMapper = new ObjectMapper();
	    String jsonList = null;
		try {
			jsonList = objectMapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    model.addAttribute("list", jsonList);
	    //model.addAttribute("list", list);
    	
	    // view 파일 선택
        return "/place/map_main";
    }
}
