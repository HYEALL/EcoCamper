package com.example.EcoCamper.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.EcoCamper.entity.Place;
import com.example.EcoCamper.repository.PlaceRepository;

@Repository
public class PlaceDAO {
	@Autowired
	PlaceRepository placeRepository;
	
	// 목록
	public List<Place> placeList() {
		List<Place> list = placeRepository.findAll();
		return list;
	}
	
}
