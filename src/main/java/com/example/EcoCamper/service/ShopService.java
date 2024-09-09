package com.example.EcoCamper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EcoCamper.dao.ShopDAO;
import com.example.EcoCamper.dto.ShopDTO;
import com.example.EcoCamper.entity.Shop;

@Service
public class ShopService {

	@Autowired
	private ShopDAO dao;
	
	public Shop	view(String pcode) {
		return dao.view(pcode);
	}
	public Shop	buy(String pcode) {
		return dao.buy(pcode);
	}
	
	public Shop save(Shop shopdto) {
		return dao.save(shopdto);
	}
	
	 public List<Shop> getAllShops() {
	        return dao.getAllShops();
	    }
}
