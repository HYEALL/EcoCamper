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

	public Shop view(String pcode) {
		return shoprepository.findById(pcode).orElse(null);
	}

	public Shop buy(String pcode) {
		return shoprepository.findById(pcode).orElse(null);
	}

	public Shop save(Shop shopdto) {
		return shoprepository.save(shopdto);
	}

	public int count() {
		return (int) shoprepository.count();
	}

	public List<Shop> allList(int startNum, int endNum) {
		return shoprepository.findbyStartNumAndEndNum(startNum, endNum);
	}

	public List<Shop> condiallList(int startNum, int endNum, String search) {
		return shoprepository.findByStartNumAndEndNumWithSearch(search, startNum, endNum);
	}

	public int searchcount(String search) {
		return shoprepository.countBySearch(search);
	}

	public List<Shop> test(String search) {
		return shoprepository.findByPtypeLike(search);
	}

}
