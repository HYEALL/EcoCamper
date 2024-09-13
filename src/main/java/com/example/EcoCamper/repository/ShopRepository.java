package com.example.EcoCamper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.EcoCamper.entity.Shop;

public interface ShopRepository extends JpaRepository<Shop, String> {

	@Query(value = "select *from (select rownum rn, tt.*from" + " (select * from Shop order by pcode desc) tt)"
			+ "  where rn >=:startNum and rn <=:endNum", nativeQuery = true)
	List<Shop> findbyStartNumAndEndNum(@Param("startNum") int startNum, @Param("endNum") int endNum);

	@Query(value = "SELECT * FROM (SELECT ROWNUM rn, tt.* FROM"
	        + " (SELECT * FROM Shop WHERE ((pname LIKE %:search%) OR (ptype LIKE %:search%)) ORDER BY pcode DESC) tt)"
	        + " WHERE rn >= :startNum AND rn <= :endNum", nativeQuery = true)
	List<Shop> findByStartNumAndEndNumWithSearch(@Param("search") String search, @Param("startNum") int startNum,
	        @Param("endNum") int endNum);


	@Query(value = "select COUNT(*) from Shop where ((pname LIKE %:search%) OR (ptype LIKE %:search%))", nativeQuery = true)
	int countBySearch(@Param("search") String search);

	List<Shop> findByPtypeLike(String ptype);

}