package com.example.EcoCamper.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.EcoCamper.entity.Map;
import com.example.EcoCamper.repository.MapRepository;

@Repository
public class MapDAO {
	@Autowired
	MapRepository mapRepository;
}
