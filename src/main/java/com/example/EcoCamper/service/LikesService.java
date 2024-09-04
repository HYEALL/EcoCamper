package com.example.EcoCamper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EcoCamper.dao.LikesDAO;
import com.example.EcoCamper.dto.LikesDTO;
import com.example.EcoCamper.entity.Likes;

@Service
public class LikesService {
	
	@Autowired
	LikesDAO dao;

    // 좋아요 추가 로직
    public int addLike(LikesDTO likesDTO) {
        return dao.addLike(likesDTO);
    }

    // 좋아요 삭제 로직
    public void removeLike(int likes_num) {
        dao.removeLikesBylikesNum(likes_num);
    }

    // 특정 리뷰의 좋아요 수를 가져오는 로직
    public int getLikeCount(int review_id) {
        return dao.countLikesByReview_id(review_id);
    }
    
    public List<Likes> findByUser_id(String user_id) {
    	return dao.findByUser_id(user_id);
    }
}
