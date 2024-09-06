package com.example.EcoCamper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.EcoCamper.dto.LikesDTO;
import com.example.EcoCamper.jwt.TokenProvider;
import com.example.EcoCamper.service.LikesService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LikesController {
	@Autowired
	private TokenProvider tokenProvider;
	
	@Autowired
	LikesService service;
	// 좋아요 추가 엔드포인트
	 @PostMapping("/add")
	    public ResponseEntity<?> addLike(@RequestBody LikesDTO likesDTO) {
	    	System.out.println(likesDTO);
	        int likes_num = service.addLike(likesDTO);
	        return ResponseEntity.ok(likes_num);
	    }

	 @DeleteMapping("/remove")
	    public ResponseEntity<?> removeLike(@RequestParam("reviewId") int reviewId, @RequestParam("userId") String userId) {
	        System.out.println("reviewId = " + reviewId + ", userId = " + userId);
	        service.deleteByReviewIdAndUserId(reviewId, userId);
	        return ResponseEntity.ok("좋아요가 삭제되었습니다.");
	    }

	    // 특정 리뷰의 좋아요 수를 가져오는 엔드포인트
	    @GetMapping("/count/{review_id}")
	    public int getLikeCount(@PathVariable int review_id) {
	        return service.getLikeCount(review_id);
	    }
	    
	    @GetMapping("/isLiked/{reviewId}")
	    public ResponseEntity<Boolean> isLiked(
	        @PathVariable("reviewId") int reviewId,
	        @RequestParam("userId") String userId) {
	        boolean isLiked = service.isLikedByUser(reviewId, userId);
	        return ResponseEntity.ok(isLiked);
	    }
	   
}
