package com.example.EcoCamper.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EcoCamper.entity.Feed;




public interface FeedRepository extends JpaRepository<Feed, Integer> {

	List<Feed> findAllByOrderByLogtimeDesc();
	
}
