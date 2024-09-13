package com.example.EcoCamper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.EcoCamper.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {
	
	 @Query("SELECT t FROM Tag t WHERE t.tag_name = :tag_name")
	    Tag findByTagName(@Param("tag_name") String tagName);
}
