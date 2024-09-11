package com.example.EcoCamper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EcoCamper.dao.FeedDAO;
import com.example.EcoCamper.dto.FeedDTO;
import com.example.EcoCamper.entity.Feed;


@Service
public class FeedService {

	@Autowired
	FeedDAO dao;
	
	public boolean feedWritePhoto(FeedDTO dto) {
		return dao.feedWritePhoto(dto);
	}

	public boolean feedWriteVideo(FeedDTO dto) {
		
		return dao.feedWriteVideo(dto);
	}
	
	 public List<FeedDTO> getAllFeeds() {
		 
	    return dao.findAll();
	    
	    }
	 
	 public Feed feedView(int seq) {
			
		 return dao.feedView(seq);
		
		}
	 
	 public boolean feedDelete(int seq) {
		 return dao.feedDelete(seq);
	 }
	
	 public boolean feedUpdate(FeedDTO dto, int seq) {
		 return dao.feedUpdate(dto, seq);
	 }
	
	 public List<Feed> getFeedsById(String id) {
		    return dao.getFeedsById(id);
		}
	 
	 public List<Feed> getFeedsByUserId(String id) {
	        return dao.getFeedsByUserId(id);
	    }
	 public List<Feed> findFeedsBySaveSeq(String id) {
	        return dao.findFeedsBySaveSeq(id);
	    }
	 
	 
}
