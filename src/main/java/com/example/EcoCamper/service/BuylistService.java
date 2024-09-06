package com.example.EcoCamper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EcoCamper.dao.BuylistDAO;
import com.example.EcoCamper.dto.BuylistDTO;
import com.example.EcoCamper.entity.Buylist;

@Service
public class BuylistService {
	
	@Autowired
	private BuylistDAO dao;
	
	public Buylist pay(BuylistDTO dto) {
		return dao.pay(dto);
	}
	
	public Buylist cartpay(BuylistDTO dto) {
		return dao.cartpay(dto);
	}
	
	public List<Buylist> orderList(int startNum, int endNum,String userId) {
		return dao.orderList(startNum, endNum, userId);
	}
	
	
	public int getTotal(String userId) {
		return dao.getTotal(userId);
	}
	public Boolean findByPcodeAndBuyId(String pcode, String buyId) {
		return dao.findByPcodeAndBuyId(pcode, buyId);
		
	}
}
