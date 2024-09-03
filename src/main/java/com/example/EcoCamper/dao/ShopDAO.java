package com.example.EcoCamper.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.EcoCamper.entity.Shop;
import com.example.EcoCamper.repository.ShopRepository;

@Repository
public class ShopDAO {
	
	@Autowired
	ShopRepository shoprepository;
	
	public Shop	view(String pcode) {
		return shoprepository.findById(pcode).orElse(null);
	}
	
	public Shop	buy(String pcode) {
		return shoprepository.findById(pcode).orElse(null);
	}
	/*
	public int update(int afterqty) {
		int result=0;
		
		return result;
	}*/
}
