package com.example.EcoCamper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EcoCamper.dao.MapDAO;
import com.example.EcoCamper.entity.Map;

@Service
public class MapService {

	@Autowired
	MapDAO dao;

	// 목록
	public List<Map> placeList() {
		return dao.placeList();
	}
	
	public List<Map> findPlacesByFilters(
            String keyword,
            List<String> regions,
            List<String> categories,
            List<String> facilities,
            List<String> environments,
            List<String> seasons) {

        // MapDAO에서 동적 쿼리를 실행하여 필터된 데이터 가져오기
        return dao.findPlacesByFilters(keyword, regions, categories, facilities, environments, seasons);
    }
}