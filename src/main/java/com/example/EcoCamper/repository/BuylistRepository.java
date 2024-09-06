package com.example.EcoCamper.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.EcoCamper.entity.Buylist;

public interface BuylistRepository extends JpaRepository<Buylist, Integer>{
	@Query(value = "select *from (select rownum rn, tt.*from"
			   + " (select * from Buylist where buyid=:userId order by logtime desc) tt)"
			   + "  where rn >=:startNum and rn <=:endNum", nativeQuery = true)
	List<Buylist> findbyStartNumAndEndNum(@Param("startNum")int startNum,@Param("endNum")int endNum,@Param("userId")String userId);

	  long countByBuyid(String userId);
	
	
	List<Buylist> findByProductcodeAndBuyid(String pcode, String buyId);
}
