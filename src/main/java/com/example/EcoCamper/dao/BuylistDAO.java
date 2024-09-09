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
		Integer sum= buylistRepository.sumProductPriceByUserId(userId);
		 if (sum == null) {
		        return 0;  // 주문 내역이 없을 때 0 반환
		    }
		return sum;
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
		boolean result=false;
		 for (String buyseq : buyseqArray) {
	           int buyseqgo = Integer.parseInt(buyseq.trim());
               int updatedCount = buylistRepository.updateBcancelToY(buyseqgo);
               
               if(updatedCount>0) { 
            	   result=true;
               }else 
            	   result=false;
		 }
		return result;
	}
	
}
