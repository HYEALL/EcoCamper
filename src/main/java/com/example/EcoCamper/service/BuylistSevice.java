package com.example.EcoCamper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EcoCamper.dao.BuylistDAO;
import com.example.EcoCamper.dto.BuylistDTO;
import com.example.EcoCamper.dto.OrderlistDTO;
import com.example.EcoCamper.entity.Buylist;

@Service
public class BuylistSevice {
	
	@Autowired
	private BuylistDAO dao;
	
	public Buylist pay(BuylistDTO dto) {
		return dao.pay(dto);
	}
	
	public Buylist cartpay(BuylistDTO dto) {
		return dao.cartpay(dto);
	}
	
	public List<OrderlistDTO> orderList(String userId) {
		return dao.orderList( userId);
	}
	
	public int getTotal(String userId) {
		return dao.getTotal(userId);
	}
	
	public int orderAllsum(String userId) {
		return dao.orderAllsum(userId);
	}
	
	public Boolean findByPcodeAndBuyId(String pcode, String buyId) {
		return dao.findByPcodeAndBuyId(pcode, buyId);
		
	}
	
	public boolean shopOrderListDelete(String[] buyseqArray) {
	 return dao.shopOrderListDelete(buyseqArray);
	}
}
