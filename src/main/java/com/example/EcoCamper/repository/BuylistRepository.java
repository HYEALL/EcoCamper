package com.example.EcoCamper.repository;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.EcoCamper.dto.OrderlistDTO;
import com.example.EcoCamper.entity.Buylist;

public interface BuylistRepository extends JpaRepository<Buylist, Integer>{
	@Query("SELECT new com.example.EcoCamper.dto.OrderlistDTO(b.buyseq, b.buyid, b.productcode, b.productqty, b.productprice, b.receivename, b.bzipcode, b.baddr1, b.baddr2, b.bphone, b.bpayment, bcancel, b.logtime, s.pname) "
	           + "FROM Buylist b "
	           + "JOIN Shop s ON b.productcode = s.pcode "
	           + "WHERE b.buyid = :userId AND b.bcancel = 'N' "
	           + "ORDER BY b.buyseq DESC")
	List<OrderlistDTO> findByUserIdwithPname(@Param("userId")String userId);

	@Query(value = "SELECT SUM(productprice) FROM Buylist WHERE buyid = :userId AND bcancel = 'N'", nativeQuery = true)
	Integer sumProductPriceByUserId(@Param("userId") String userId);

	long countByBuyid(String userId);
	
	 @Modifying
	 @Transactional
	 @Query("UPDATE Buylist  SET bcancel = 'Y' WHERE buyseq = :buyseq AND bcancel = 'N'")
	   int updateBcancelToY(@Param("buyseq") int buyseq);
	
	List<Buylist> findByProductcodeAndBuyid(String pcode, String buyId);
}
