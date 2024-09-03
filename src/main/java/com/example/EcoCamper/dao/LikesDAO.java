package com.example.EcoCamper.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.EcoCamper.entity.Likes;
import com.example.EcoCamper.repository.LikesRepository;

@Repository
public class LikesDAO {
    @Autowired
    LikesRepository repository;

    // 좋아요 추가 메소드
    public Likes addLike(Likes like) {
        return repository.save(like);
    }

    // 좋아요 삭제 메소드 (reviewId 사용)
    public void removeLikesByReview_id(int review_id) {
        repository.deleteByReviewId(review_id);
    }

    // 특정 리뷰에 대한 좋아요 수 가져오기
    public int countLikesByReview_id(int review_id) {
        return repository.countByReviewId(review_id);
    }

}
