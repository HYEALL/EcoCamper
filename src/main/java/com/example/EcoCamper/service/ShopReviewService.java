package com.example.EcoCamper.service;

import java.util.List;

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
	
	public List<ShopReview> reveiwList(int startNum, int endNum,String pcode) {
		return dao.reveiwList(startNum, endNum, pcode);
	}
	
	public int getTotal(String pcode) {
		return dao.getTotal(pcode);
	}
	
	public boolean shopReviewDelete(int shopreviewseq){
		return dao.shopReviewDelete(shopreviewseq);
	}
	
	public List<ShopReview> myReview(String userId ,int startNum,int endNum) {
		return dao.myReview(userId, startNum, endNum);
	}
	
	public int count(String userId){
		return dao.count(userId);
	}
}
