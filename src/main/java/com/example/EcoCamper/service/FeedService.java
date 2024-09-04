package com.example.EcoCamper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EcoCamper.dao.FeedDAO;
import com.example.EcoCamper.dto.FeedDTO;
import com.example.EcoCamper.entity.Feed;
import com.example.EcoCamper.entity.Likes;

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


}
