package com.example.EcoCamper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.EcoCamper.entity.Likes;

import jakarta.transaction.Transactional;

public interface LikesRepository extends JpaRepository<Likes, Integer>{
	

    // 특정 리뷰의 좋아요 수를 카운트하는 쿼리
    int countByReviewId(int reviewId);
    
    // 특정 사용자가 피드를 좋아요 했는지 확인하는 쿼리
    @Query("SELECT COUNT(l) > 0 FROM Likes l WHERE l.reviewId = :reviewId AND l.userId = :userId")
    boolean existsByReviewIdAndUserId(@Param("reviewId") int reviewId, @Param("userId") String userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Likes l WHERE l.reviewId = :reviewId AND l.userId = :userId")
    void deleteByReviewIdAndUserId(@Param("reviewId") int reviewId, @Param("userId") String userId);
}



