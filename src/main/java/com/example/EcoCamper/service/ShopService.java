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

	public Shop view(String pcode) {
		return dao.view(pcode);
	}

	public Shop buy(String pcode) {
		return dao.buy(pcode);
	}

	public Shop save(Shop shopdto) {
		return dao.save(shopdto);
	}

	public int count() {
		return dao.count();
	}

	public List<Shop> allList(int startNum, int endNum) {
		return dao.allList(startNum, endNum);
	}

	public List<Shop> condiallList(int startNum, int endNum, String search) {
		return dao.condiallList(startNum, endNum, search);
	}

	public int searchcount(String search) {
		return dao.searchcount(search);
	}

	public List<Shop> test(String search) {
		return dao.test(search);
	}

	public List<Shop> shopTop() {
		return dao.shopTop();
	}

}
