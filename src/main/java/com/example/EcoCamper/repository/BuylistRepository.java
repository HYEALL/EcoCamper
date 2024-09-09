package com.example.EcoCamper.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.EcoCamper.dto.OrderlistDTO;
import com.example.EcoCamper.entity.Buylist;

public interface BuylistRepository extends JpaRepository<Buylist, Integer>{
	@Query("SELECT new com.example.EcoCamper.dto.OrderlistDTO(b.buyseq, b.buyid, b.productcode, b.productqty, b.productprice, b.receivename, b.baddress, b.bphone, b.bpayment, b.logtime, s.pname) "
	           + "FROM Buylist b "
	           + "JOIN Shop s ON b.productcode = s.pcode "
	           + "WHERE b.buyid = :userId "
	           + "ORDER BY b.buyseq DESC")
	List<OrderlistDTO> findByUserIdwithPname(@Param("userId")String userId);

	@Query(value = "SELECT SUM(productprice) FROM Buylist WHERE buyid = :userId", nativeQuery = true)
	int sumProductPriceByUserId(@Param("userId") String userId);

	long countByBuyid(String userId);
	
	boolean deleteByBuyseqIn(String[] buyseqArray);
	
	List<Buylist> findByProductcodeAndBuyid(String pcode, String buyId);
}
