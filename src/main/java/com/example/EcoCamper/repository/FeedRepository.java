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

	@Query("SELECT f FROM Feed f JOIN Likes l ON f.seq = l.reviewId WHERE l.userId = :userId")
	List<Feed> findFeedsByUserIdAndReviewId(@Param("userId") String id);

	@Query("SELECT f FROM Feed f JOIN Save l ON f.seq = l.save_seq WHERE l.save_id = :save_id")
	List<Feed> findFeedsBySeqIdAndSaveSeq(@Param("save_id") String id);
	
	// 태그가 포함된 피드를 검색합니다.
    @Query("SELECT f FROM Feed f WHERE f.tags LIKE %:tagName%")
    List<Feed> findByTagName(@Param("tagName") String tagName);

}
