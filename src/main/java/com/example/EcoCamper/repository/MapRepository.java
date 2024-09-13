package com.example.EcoCamper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.EcoCamper.entity.Map;

@Repository
public interface MapRepository extends JpaRepository<Map, Integer>, JpaSpecificationExecutor<Map>{
	
}
