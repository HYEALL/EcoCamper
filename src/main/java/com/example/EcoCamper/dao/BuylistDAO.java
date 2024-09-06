package com.example.EcoCamper.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.EcoCamper.dto.BuylistDTO;
import com.example.EcoCamper.entity.Buylist;
import com.example.EcoCamper.repository.BuylistRepository;

@Repository
public class BuylistDAO {
	
	@Autowired
	BuylistRepository buylistRepository;
	
	public Buylist pay(BuylistDTO dto) {
		return buylistRepository.save(dto.toEntity());
	}
	
	public Buylist cartpay(BuylistDTO dto) {
		
		return buylistRepository.save(dto.toEntity());
	}
	
	public List<Buylist> orderList(int startNum, int endNum,String userId) {
		return buylistRepository.findbyStartNumAndEndNum(startNum, endNum, userId);
	}
	
	
	public int  getTotal(String userId) {
		return (int) buylistRepository.countByBuyid(userId);
	}
	
	public Boolean findByPcodeAndBuyId(String pcode, String buyId) {
		List<Buylist> buylist = buylistRepository.findByProductcodeAndBuyid(pcode, buyId);
		if(!buylist.isEmpty()) {
			return true;
		} else {
			return false;
		}
		
	}
}
