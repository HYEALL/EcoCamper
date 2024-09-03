package com.example.EcoCamper.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public String addLike(@RequestBody LikesDTO likesDTO) {
        service.addLike(likesDTO);
        return "좋아요가 추가되었습니다.";
    }

    // 좋아요 삭제 엔드포인트
    @DeleteMapping("/remove/{review_id}")
    public String removeLike(@PathVariable int review_id) {
        service.removeLike(review_id);
        return "좋아요가 삭제되었습니다.";
    }

    // 특정 리뷰의 좋아요 수를 가져오는 엔드포인트
    @GetMapping("/count/{review_id}")
    public int getLikeCount(@PathVariable int review_id) {
        return service.getLikeCount(review_id);
    }

}
