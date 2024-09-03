package com.example.EcoCamper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.EcoCamper.entity.Buylist;
import com.example.EcoCamper.entity.Shop;

public interface BuylistRepository extends JpaRepository<Buylist, Integer>{
	@Query(value = "select *from (select rownum rn, tt.*from"
			   + " (select * from Buylist order by logtime asc) tt)"
			   + "  where rn >=:startNum and rn <=:endNum", nativeQuery = true)
	List<Shop> findbyStartNumAndEndNum(@Param("startNum")int startNum,@Param("endNum")int endNum);
}
