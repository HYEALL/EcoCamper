package com.example.EcoCamper.repository;


import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.EcoCamper.entity.Shop;

public interface ShopRepository extends JpaRepository<Shop, String> {
	
	@Query(value = "select *from (select rownum rn, tt.*from"
			   + " (select * from Shop order by productcode desc) tt)"
			   + "  where rn >=:startNum and rn <=:endNum", nativeQuery = true)
	List<Shop> findbyStartNumAndEndNum(@Param("startNum")int startNum,@Param("endNum")int endNum);
}