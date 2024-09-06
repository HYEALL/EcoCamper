package com.example.EcoCamper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EcoCamper.dao.LikesDAO;
import com.example.EcoCamper.dto.LikesDTO;


@Service
public class LikesService {
	
	@Autowired
	LikesDAO dao;


    // 좋아요 추가 로직
    public int addLike(LikesDTO likesDTO) {
        return dao.addLike(likesDTO);
    }

    // 좋아요 삭제 로직
    public void deleteByReviewIdAndUserId(int reviewId, String userId) {
        dao.deleteByReviewIdAndUserId(reviewId, userId);
    }

    // 특정 리뷰의 좋아요 수를 가져오는 로직
    public int getLikeCount(int reviewId) {
        return dao.countLikesByReview_id(reviewId);
    }
    
    // 특정 사용자가 피드를 좋아요 했는지 확인하는 로직
    public boolean isLikedByUser(int reviewId, String userId) {
        return dao.existsByReviewIdAndUserId(reviewId, userId);
    }
}
