package com.example.EcoCamper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.EcoCamper.dto.LikesDTO;
import com.example.EcoCamper.service.LikesService;

@Controller
public class LikesController {
	
	@Autowired
	LikesService service;
	// 좋아요 추가 엔드포인트
    @PostMapping("/add")
    public ResponseEntity<?> addLike(@RequestBody LikesDTO likesDTO) {
    	System.out.println(likesDTO);
        int likes_num = service.addLike(likesDTO);
        return ResponseEntity.ok(likes_num);
    }

    // 좋아요 삭제 엔드포인트
    @DeleteMapping("/remove/{likes_num}")	
    public ResponseEntity<?>  removeLike(@PathVariable("likes_num") int likes_num) {
    	System.out.println("remove likes_num" + likes_num);
        service.removeLike(likes_num);
        return ResponseEntity.ok("좋아요가 삭제되었습니다.");
    }

    // 특정 리뷰의 좋아요 수를 가져오는 엔드포인트
    @GetMapping("/count/{review_id}")
    public int getLikeCount(@PathVariable int review_id) {
        return service.getLikeCount(review_id);
    }

}
