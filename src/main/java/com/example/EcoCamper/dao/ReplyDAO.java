package com.example.EcoCamper.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.EcoCamper.entity.Reply;
import com.example.EcoCamper.repository.ReplyRepository;

@Repository
public class ReplyDAO {
	
	@Autowired
	ReplyRepository repository;

	    // 댓글 저장
	    public Reply save(Reply reply) {
	        return repository.save(reply);
	    }


	    public List<Reply> findBySeq(int seq) {
	        return repository.findBySeq(seq);
	    }
	    
	 // 댓글 가져오기
	    public Reply getReplyById(int id) {
	        return repository.findById(id).orElse(null);
	    }
	    
	 // 댓글 삭제
	    // 댓글 삭제
	    public boolean deleteByReplyId(int replyId) {
	        Reply reply = getReplyById(replyId);
	        if (reply != null) {
	            repository.delete(reply);
	            return true;
	        }
	        return false;
	    }
	    

}
