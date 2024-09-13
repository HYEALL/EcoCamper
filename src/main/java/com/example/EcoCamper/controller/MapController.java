package com.example.EcoCamper.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.EcoCamper.dto.SearchDTO;
import com.example.EcoCamper.entity.Map;
import com.example.EcoCamper.jwt.TokenProvider;
import com.example.EcoCamper.service.MapService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@Controller
public class MapController {
	@Autowired
	MapService service;

	@Autowired
	private TokenProvider tokenProvider;

	@Value("${project.upload.path}")
	private String uploadpath;

	@GetMapping("/map")
	public String map(Model model, HttpServletRequest request) {

		// 로그인된 아이디를 토큰에 저장
		String token = tokenProvider.resolveTokenFromCookie(request);
		System.out.println("token : " + token);
		String userId = tokenProvider.validateAndGetUserId(token);
		System.out.println("userId = " + userId);

		// 데이터 공유
		model.addAttribute("req", "/place/map");
		// view 파일 선택
		return "/index";
	}

	// 필터와 검색어에 따른 검색 결과를 처리하는 POST 메서드
    @PostMapping("/search")
    @Transactional
    public ResponseEntity<List<Map>> searchPlaces(@RequestBody SearchDTO search) {
    	// 데이터 잘 받아오는지 확인
        System.out.println("ajax로 받은 데이터:");
        System.out.println("Keyword: " + search.getKeyword());
        System.out.println("Regions: " + search.getRegions());
        System.out.println("Categories: " + search.getCategories());
        System.out.println("Facilities: " + search.getFacilities());
        System.out.println("Environments: " + search.getEnvironments());
        System.out.println("Seasons: " + search.getSeasons());
        
    	// 필터를 적용한 장소 목록 조회
        List<Map> filteredPlaces = service.findPlacesByFilters(
            search.getKeyword(),
            search.getRegions(),
            search.getCategories(),
            search.getFacilities(),
            search.getEnvironments(),
            search.getSeasons()
        );

        System.out.println("동적쿼리 결과: " + filteredPlaces);
        return ResponseEntity.ok(filteredPlaces);
    }


	@GetMapping("/placeForm")
	public String placeForm(Model model, HttpServletRequest request) {

		// 데이터 공유

		return "place/placeForm";
	}

	@PostMapping("/place")
	public String place(Model model, HttpServletRequest request) {
		// 데이터 공유
		// 로그인한 아이디도 넘겨받기 (장소 스크래핑 기능)
		// seq 넘겨받아서 한줄 데이터 읽어오기 -> json으로 저장
		return "place/place";
	}
}
