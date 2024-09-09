package com.example.EcoCamper.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.EcoCamper.dto.BuylistDTO;
import com.example.EcoCamper.dto.OrderlistDTO;
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
	
	public List<OrderlistDTO> orderList(String userId) {
		return buylistRepository.findByUserIdwithPname(userId);
	}
	
	
	public int  getTotal(String userId) {
		return (int) buylistRepository.countByBuyid(userId);
	}
	
	public int orderAllsum(String userId) {
		return (int)buylistRepository.sumProductPriceByUserId(userId);
	}
	
	public Boolean findByPcodeAndBuyId(String pcode, String buyId) {
		List<Buylist> buylist = buylistRepository.findByProductcodeAndBuyid(pcode, buyId);
		if(!buylist.isEmpty()) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public boolean shopOrderListDelete(String[] buyseqArray) {
		System.out.println(buyseqArray);
		boolean result=false;
		if(buyseqArray != null) {
			buylistRepository.deleteByBuyseqIn(buyseqArray);
			 result=true;
		}
		
		return result;
	}
	
}
