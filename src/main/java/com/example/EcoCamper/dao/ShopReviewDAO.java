package com.example.EcoCamper.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.EcoCamper.dto.ShopReviewDTO;
import com.example.EcoCamper.entity.ShopReview;
import com.example.EcoCamper.repository.ShopReviewRepository;

@Repository
public class ShopReviewDAO {
	@Autowired
	ShopReviewRepository shopReviewRepository;
	
	public ShopReview write(ShopReviewDTO dto)  {
		return shopReviewRepository.save(dto.toEntity());
	}
}
