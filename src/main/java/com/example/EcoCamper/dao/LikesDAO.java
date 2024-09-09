package com.example.EcoCamper.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.EcoCamper.dto.LikesDTO;
import com.example.EcoCamper.entity.Likes;
import com.example.EcoCamper.repository.LikesRepository;

@Repository
public class LikesDAO {
    @Autowired
    LikesRepository repository;


    // 좋아요 추가 메소드
    public int addLike(LikesDTO likesDTO) {
    	Likes like = likesDTO.toEntity();
    	repository.save(like);
        return like.getLikes_num(); // like num 가져오기
    }

    // 좋아요 삭제 메소드 (likes_num 사용)
    public void deleteByReviewIdAndUserId(int reviewId, String userId) {
        repository.deleteByReviewIdAndUserId(reviewId, userId);
    }


    // 특정 리뷰에 대한 좋아요 수 가져오기
    public int countLikesByReview_id(int reviewId) {
        return repository.countByReviewId(reviewId);
    }
    
    // 특정 사용자가 피드를 좋아요 했는지 확인
    public boolean existsByReviewIdAndUserId(int reviewId, String userId) {
        return repository.existsByReviewIdAndUserId(reviewId, userId);
    }
 
}
