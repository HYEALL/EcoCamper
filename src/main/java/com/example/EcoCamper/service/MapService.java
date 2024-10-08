package com.example.EcoCamper.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.EcoCamper.dao.MapDAO;
import com.example.EcoCamper.dao.PlacefilterSpecification;
import com.example.EcoCamper.entity.Map;
import com.example.EcoCamper.repository.MapRepository;

@Service
public class MapService {

	@Autowired
	MapDAO dao;
	
	@Autowired
	MapRepository mapRepository;
	
	@Autowired
	PlacefilterSpecification placefilterSpecification;
	
	// 필터 조건에 맞는 장소 리스트를 반환하는 메서드
    public List<Map> filterPlaces(
            List<String> regionCodes,
            List<String> categoryCodes,
            List<String> facilityCodes,
            List<String> environmentCodes,
            List<String> seasonCodes,
            String keywordCode) {

        // Specification을 사용해 필터 조건을 동적으로 적용
        Specification<Map> spec = PlacefilterSpecification.filterByRegionCategoryFacilityEnvironmentSeasonKeyword(
                regionCodes, categoryCodes, facilityCodes, environmentCodes, seasonCodes, keywordCode);

        // 필터된 장소 리스트를 반환
        return mapRepository.findAll(spec);
    }

    // 전체 리스트 불러오기
	public List<Map> findAll(Specification<Map> spec) {
		return mapRepository.findAll(spec);
	}

	public void save(Map map) {
		// 저장 전 현재 날짜와 시간을 설정
        map.setUpload_date(new Date());  // 현재 날짜 및 시간 추가
		mapRepository.save(map);
	}

	// 상세보기
	public Optional<Map> getPlaceBySeq(int place_seq) {
		return mapRepository.findById(place_seq);
	}
	
	// 최근 latest top5 장소들 불러오는 로직
    public List<Map> getLatestFivePlaces() {
        return mapRepository.findTop5ByOrderByUploadDateDesc();
    }
    
    // 수정 로직
    public void updatePlace(Map placeData) {
        mapRepository.save(placeData); // JPA를 이용해 업데이트
    }

    // 삭제 로직
    public void deletePlace(int place_seq) {
        mapRepository.deleteById(place_seq); // JPA를 이용해 삭제
    }
}