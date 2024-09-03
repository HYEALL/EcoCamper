package com.example.EcoCamper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EcoCamper.entity.Likes;

public interface LikesRepository extends JpaRepository<Likes, Integer>{
	

    // 특정 리뷰의 좋아요 수를 카운트하는 쿼리
    int countByReviewId(int reviewId);

    // 특정 리뷰의 좋아요를 삭제하는 쿼리
    void deleteByReviewId(int reviewId);
}




