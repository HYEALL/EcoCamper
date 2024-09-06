package com.example.EcoCamper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EcoCamper.dao.ReplyDAO;
import com.example.EcoCamper.entity.Reply;


@Service
public class ReplyService {
	
	@Autowired
	ReplyDAO dao;
	

    // 댓글 생성
    public Reply createReply(Reply reply) {
        return dao.save(reply);
    }


    public List<Reply> getRepliesByFeedId(int seq) {
        return dao.findBySeq(seq);
    }
    
 // 댓글 가져오기
    public Reply getReplyById(int id) {
        return dao.getReplyById(id);
    }

 // 댓글 삭제
    public boolean deleteReplyById(int replyId) {
        return dao.deleteByReplyId(replyId);
    }
}
