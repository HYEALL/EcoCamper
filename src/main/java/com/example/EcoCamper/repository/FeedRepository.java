package com.example.EcoCamper.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.EcoCamper.dto.FeedDTO;
import com.example.EcoCamper.entity.Feed;




public interface FeedRepository extends JpaRepository<Feed, Integer> {

	List<Feed> findAllByOrderByLogtimeDesc();
	
	List<Feed> findByIdOrderByLogtimeDesc(String id);
	
	 
	@Query("SELECT f FROM Feed f JOIN Likes l ON f.seq = l.reviewId WHERE l.userId = :userId AND f.id = l.userId")
	List<Feed> findFeedsByUserIdAndReviewId(@Param("userId") String id);
	
	@Query("SELECT f FROM Feed f JOIN Save l ON f.seq = l.save_seq WHERE l.save_id = :save_id AND f.id = l.save_id")
	List<Feed> findFeedsBySeqIdAndSaveSeq(@Param("save_id") String id);
}
