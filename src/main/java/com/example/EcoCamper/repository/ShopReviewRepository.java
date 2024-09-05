package com.example.EcoCamper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.EcoCamper.entity.ShopReview;

public interface ShopReviewRepository extends JpaRepository<ShopReview, Integer>{
	@Query(value = "select *from (select rownum rn, tt.*from"
			   + " (select * from ShopReview order by logtime asc) tt)"
			   + "  where rn >=:startNum and rn <=:endNum", nativeQuery = true)
	List<ShopReview> findbyStartNumAndEndNum(@Param("startNum")int startNum,@Param("endNum")int endNum);
}
