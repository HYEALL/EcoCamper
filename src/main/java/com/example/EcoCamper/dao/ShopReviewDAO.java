package com.example.EcoCamper.dao;

import java.util.List;

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

	public List<ShopReview> reveiwList(int startNum, int endNum, String pcode){
		return shopReviewRepository.findbyStartNumAndEndNum(startNum, endNum, pcode);
	}
	
	public int getTotal(String pcode) {
		return (int) shopReviewRepository.countByShopreviewpcode(pcode);
	}
	
	public boolean shopReviewDelete(int shopreviewseq){
		ShopReview shopReview=shopReviewRepository.findById(shopreviewseq).orElse(null);
		if(shopReview !=null) {
			shopReviewRepository.delete(shopReview);
		}
		return !shopReviewRepository.existsById(shopreviewseq);
	}
	
	public List<ShopReview> myReview(String userId,int startNum,int endNum) {
		return shopReviewRepository.findbyStartNumAndEndNumWithUserId(userId, startNum, endNum);
	}
	
	public int count(String userId){
		return shopReviewRepository.countByUserId(userId);
	}
	
}
