package com.example.EcoCamper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.EcoCamper.entity.Likes;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Integer>{
	

    // 특정 리뷰의 좋아요 수를 카운트하는 쿼리
    int countByReviewId(int reviewId);

	//List<Likes> findByUser_id(String user_id);
    @Query("SELECT l FROM Likes l WHERE l.user_id = :user_id")
    List<Likes> findByUser_id(@Param("user_id") String user_id);


}




