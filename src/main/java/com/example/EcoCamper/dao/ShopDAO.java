package com.example.EcoCamper.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import com.example.EcoCamper.dto.ShopDTO;
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
	
	public Shop save(Shop shopdto) {
		return shoprepository.save(shopdto);
	}
	
	 public List<Shop> getAllShops() {
	        return shoprepository.findAll();
	    }
}
