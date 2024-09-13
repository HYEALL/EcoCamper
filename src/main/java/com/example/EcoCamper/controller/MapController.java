package com.example.EcoCamper.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.EcoCamper.dao.PlacefilterSpecification;
import com.example.EcoCamper.entity.Map;
import com.example.EcoCamper.jwt.TokenProvider;
import com.example.EcoCamper.repository.MapRepository;
import com.example.EcoCamper.service.MapService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@Controller
public class MapController {
	@Autowired
	MapService mapService;
	
	@Autowired
	MapRepository mapRepository;

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
    public ResponseEntity<List<Map>> searchPlaces(@RequestBody java.util.Map<String, Object> requestData) {
        // 클라이언트로부터 전달된 필터링 조건을 받아옵니다.
        String keyword = (String) requestData.get("keyword");
        List<String> regions = (List<String>) requestData.get("regions");
        List<String> categories = (List<String>) requestData.get("categories");
        List<String> facilities = (List<String>) requestData.get("facilities");
        List<String> environments = (List<String>) requestData.get("environments");
        List<String> seasons = (List<String>) requestData.get("seasons");
        
        System.out.println(keyword);
        System.out.println(regions);
        System.out.println(categories);
        System.out.println(facilities);
        System.out.println(environments);
        System.out.println(seasons);

        // PlacefilterSpecification의 정적 메서드를 사용하여 Specification을 생성합니다.
        Specification<Map> spec = PlacefilterSpecification.filterByRegionCategoryFacilityEnvironmentSeasonKeyword(
            regions, categories, facilities, environments, seasons, keyword);

        // 필터링된 결과를 가져옵니다.
        List<Map> results = mapRepository.findAll(spec);
        
        // 필터링된 결과를 클라이언트에 반환합니다.
        return ResponseEntity.ok(results);
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
