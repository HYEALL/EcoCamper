package com.example.EcoCamper.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.EcoCamper.entity.Reply;
import com.example.EcoCamper.service.ReplyService;

@Controller
public class ReplyController {

	@Autowired
	ReplyService service;
	
	  @PostMapping("/reply")
	    public ResponseEntity<Reply> createReply(@RequestBody Reply reply) {
	        reply.setLogtime(new Date()); // 댓글 작성 시간을 현재 시간으로 설정
	        Reply savedReply = service.createReply(reply); // 저장된 댓글을 반환
	        return ResponseEntity.status(201).body(savedReply); // 생성 성공 응답
	    }

	    @GetMapping("/replies/{seq}")
	    @ResponseBody
	    public ResponseEntity<List<Reply>> getRepliesByFeedId(@PathVariable("seq") int seq) {
	        List<Reply> replies = service.getRepliesByFeedId(seq); // 게시글 번호에 해당하는 댓글을 반환
	        return ResponseEntity.ok(replies); // 성공 응답
	    }
	    
	    @DeleteMapping("/deleteReply/{replyId}")
	    public ResponseEntity<Map<String, String>> deleteReply(@PathVariable("replyId") int replyId) {
	        boolean removed = service.deleteReplyById(replyId);
	        Map<String, String> response = new HashMap<>();
	        if (removed) {
	            response.put("message", "댓글이 삭제되었습니다.");
	            return ResponseEntity.ok(response);
	        } else {
	            response.put("message", "댓글을 찾을 수 없습니다.");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }
	    }
}
