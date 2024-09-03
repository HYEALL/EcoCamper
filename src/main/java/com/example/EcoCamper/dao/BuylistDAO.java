package com.example.EcoCamper.dao;

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
	
}
