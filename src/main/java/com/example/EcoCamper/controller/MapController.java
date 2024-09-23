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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.EcoCamper.dao.PlacefilterSpecification;
import com.example.EcoCamper.entity.Map;
import com.example.EcoCamper.jwt.TokenProvider;
import com.example.EcoCamper.repository.MapRepository;
import com.example.EcoCamper.service.MapService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
		List<Map> results = mapService.findAll(spec);

		// 필터링된 결과를 클라이언트에 반환합니다.
		return ResponseEntity.ok(results);
	}

	// 장소 입력하기 폼
	@GetMapping("/writePlace")
	public String showMapForm(Model model) {
		model.addAttribute("map", new Map());
		model.addAttribute("req", "place/writePlace"); // map 데이터를 입력하는 폼
		return "/index";
	}

	// 입력된 장소 db에 저장
	@PostMapping("/savePlace")
	public String savePlace(Map place, RedirectAttributes redirectAttributes) {
	    try {
	        // 현재 날짜를 upload_date에 설정
	        place.setUpload_date(new Date());
	        
	        // 장소 데이터를 저장하고, 저장된 엔티티를 반환받음
	        Map savedPlace = mapRepository.save(place);
	        
	        // 저장 성공 시 상세보기 페이지로 리다이렉트
	        redirectAttributes.addAttribute("place_seq", savedPlace.getPlace_seq());
	        return "redirect:/placeForm";
	    } catch (Exception e) {
	        // 저장 실패 시 실패 메시지와 함께 장소 입력 페이지로 리다이렉트
	        redirectAttributes.addAttribute("saveSuccess", "false");
	        return "redirect:/writePlace";
	    }
	}

	// 장소 상세보기
	@GetMapping("/placeForm")
	public String placeForm(@RequestParam("place_seq") int place_seq, Model model) {

		// seq를 이용해 데이터를 가져온 후, model에 담아 뷰로 전달
		Optional<Map> mapOptional = mapService.getPlaceBySeq(place_seq);

		// Optional에서 데이터를 추출하고 모델에 담는다.
		if (mapOptional.isPresent()) {
			model.addAttribute("map", mapOptional.get());
		} else {
			// 데이터가 없을 경우 처리
			model.addAttribute("map", new Map()); // 또는 에러 처리
		}

		System.out.println("map optional 읽어온 정보: " + mapOptional.get());

		String placeFacilityStr = mapOptional.get().getPlace_facility();
		String placeEnvironmentStr = mapOptional.get().getPlace_environment();
		String placeSeasonStr = mapOptional.get().getPlace_season();

		List<String> place_facility = new ArrayList<>();
		List<String> place_environment = new ArrayList<>();
		List<String> place_season = new ArrayList<>();

		if (placeFacilityStr != null && !placeFacilityStr.isEmpty()) {
			place_facility = Arrays.asList(placeFacilityStr.split(","));
		}

		if (placeEnvironmentStr != null && !placeEnvironmentStr.isEmpty()) {
			place_environment = Arrays.asList(placeEnvironmentStr.split(","));
		}

		if (placeSeasonStr != null && !placeSeasonStr.isEmpty()) {
			place_season = Arrays.asList(placeSeasonStr.split(","));
		}

		System.out.println("place_facility 리스트: " + place_facility);
		System.out.println("place_environment 리스트: " + place_environment);
		System.out.println("place_season 리스트: " + place_season);

		model.addAttribute("place_facility", place_facility);
		model.addAttribute("place_environment", place_environment);
		model.addAttribute("place_season", place_season);
		model.addAttribute("req", "/place/placeForm"); 
		return "/index";
	}

	// 장소 삭제하기 로직
	@GetMapping("/deletePlace")
	public String deletePlace(@RequestParam("place_seq") int place_seq, RedirectAttributes redirectAttributes) {
	    try {
	        mapService.deletePlace(place_seq); // 삭제 로직
	        redirectAttributes.addAttribute("deleteSuccess", "true");
	    } catch (Exception e) {
	        redirectAttributes.addAttribute("deleteSuccess", "false");
	    }
	    return "redirect:/map"; // 삭제 후 지도 페이지로 이동
	}

	// 장소 수정하기 (상세보기와 동일하게 place_seq로 장소 정보 띄우기)
	@GetMapping("/modifyPlace")
	public String modifyPlace(@RequestParam("place_seq") int place_seq, Model model) {

		// seq를 이용해 데이터를 가져온 후, model에 담아 뷰로 전달
		Optional<Map> mapOptional = mapService.getPlaceBySeq(place_seq);

		// Optional에서 데이터를 추출하고 모델에 담는다.
		if (mapOptional.isPresent()) {
			model.addAttribute("map", mapOptional.get());
		} else {
			// 데이터가 없을 경우 처리
			model.addAttribute("map", new Map()); // 또는 에러 처리
		}

		System.out.println("map optional 읽어온 정보: " + mapOptional.get());

		String placeFacilityStr = mapOptional.get().getPlace_facility();
		String placeEnvironmentStr = mapOptional.get().getPlace_environment();
		String placeSeasonStr = mapOptional.get().getPlace_season();

		List<String> place_facility = new ArrayList<>();
		List<String> place_environment = new ArrayList<>();
		List<String> place_season = new ArrayList<>();

		if (placeFacilityStr != null && !placeFacilityStr.isEmpty()) {
			place_facility = Arrays.asList(placeFacilityStr.split(","));
		}

		if (placeEnvironmentStr != null && !placeEnvironmentStr.isEmpty()) {
			place_environment = Arrays.asList(placeEnvironmentStr.split(","));
		}

		if (placeSeasonStr != null && !placeSeasonStr.isEmpty()) {
			place_season = Arrays.asList(placeSeasonStr.split(","));
		}

		System.out.println("place_facility 리스트: " + place_facility);
		System.out.println("place_environment 리스트: " + place_environment);
		System.out.println("place_season 리스트: " + place_season);

		model.addAttribute("place_facility", place_facility);
		model.addAttribute("place_environment", place_environment);
		model.addAttribute("place_season", place_season);
		model.addAttribute("req", "place/modifyPlace"); // map 데이터를 수정하는 폼
		return "/index";
	}
	
	// 장소 수정하기 로직
	@PostMapping("/updatePlace")
	public String updatePlace(HttpServletRequest request, Map placeData, RedirectAttributes redirectAttributes) {
		int place_seq = Integer.parseInt(request.getParameter("place_seq"));
		System.out.println(place_seq);
		
		// seq를 이용해 데이터를 가져온 후, model에 담아 뷰로 전달
		Optional<Map> mapOptional = mapService.getPlaceBySeq(place_seq);
		System.out.println(mapOptional);
		
	    try {
	        mapService.updatePlace(placeData); // 수정 로직
	        redirectAttributes.addAttribute("updateSuccess", "true");
	        return "redirect:/placeForm?place_seq=" + placeData.getPlace_seq(); // 수정 성공 후 상세 페이지로 이동
	    } catch (Exception e) {
	        redirectAttributes.addAttribute("updateSuccess", "false");
	        return "redirect:/modifyPlace?place_seq=" + placeData.getPlace_seq(); // 수정 실패 시 다시 수정 페이지로
	    }
	}

}
