package com.example.EcoCamper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EcoCamper.dao.ShopReviewDAO;
import com.example.EcoCamper.dto.ShopReviewDTO;
import com.example.EcoCamper.entity.ShopReview;

@Service
public class ShopReviewService {
	
	@Autowired
	private ShopReviewDAO dao;
	
	public ShopReview write(ShopReviewDTO dto) {
		return dao.write(dto);
	}
}
