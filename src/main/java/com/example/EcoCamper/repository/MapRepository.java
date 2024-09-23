package com.example.EcoCamper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.EcoCamper.entity.Map;

@Repository
public interface MapRepository extends JpaRepository<Map, Integer>, JpaSpecificationExecutor<Map>{
    // 등록 날짜 순으로 상위 5개 장소 가져오기
	@Query("SELECT m FROM Map m ORDER BY m.upload_date DESC")
	List<Map> findTop5ByOrderByUploadDateDesc();
}
