package com.example.EcoCamper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.EcoCamper.entity.Map;

@Repository
public interface MapRepository extends JpaRepository<Map, Integer>{

	@Query("SELECT m FROM Map m WHERE " +
			   "(:keyword IS NULL OR m.place_name LIKE %:keyword% OR m.place_description LIKE %:keyword%) AND " +
	           "(:regions IS NULL OR m.place_address IN :regions) AND " +
	           "(:categories IS NULL OR m.place_category IN :categories) AND " +
	           "(:facilities IS NULL OR m.place_facility IN :facilities) AND " +
	           "(:environments IS NULL OR m.place_environment IN :environments) AND " +
	           "(:seasons IS NULL OR m.place_season IN :seasons)")
	    List<Map> findPlacesByFilters(
	    	@Param("keyword") String keyword,
	        @Param("regions") List<String> regions, 
	        @Param("categories") List<String> categories,
	        @Param("facilities") List<String> facilities,
	        @Param("environments") List<String> environments,
	        @Param("seasons") List<String> seasons
	    );
}

