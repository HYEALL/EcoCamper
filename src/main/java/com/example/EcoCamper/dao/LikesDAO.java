package com.example.EcoCamper.dao;

import java.util.List;

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
    public void removeLikesBylikesNum(int likes_num) {
        repository.deleteById(likes_num);
    }

    // 특정 리뷰에 대한 좋아요 수 가져오기
    public int countLikesByReview_id(int review_id) {
        return repository.countByReviewId(review_id);
    }
    
    // userId 기준으로 like list 찾기
	public List<Likes> findByUser_id(String user_id) {
		return repository.findByUser_id(user_id);
	}


}
