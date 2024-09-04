package com.example.EcoCamper.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.EcoCamper.dto.FeedDTO;
import com.example.EcoCamper.entity.Feed;
import com.example.EcoCamper.entity.Likes;
import com.example.EcoCamper.repository.FeedRepository;





@Repository
public class FeedDAO {
	@Autowired 
	FeedRepository feedRepository;
	
	public boolean feedWritePhoto(FeedDTO dto) {
		
		return feedRepository.save(dto.toEntity()) != null;
	}
	// 작성
	
	public boolean feedWriteVideo(FeedDTO dto) {
		
		return feedRepository.save(dto.toEntity()) != null;
	}
	
	public List<FeedDTO> findAll() {
        return feedRepository.findAllByOrderByLogtimeDesc().stream()
            .map(project -> {
            	FeedDTO dto = new FeedDTO();
            	dto.setSeq(project.getSeq());
                dto.setId(project.getId());
                dto.setOutdoor(project.getOutdoor());
                dto.setFeed_subject(project.getFeed_subject());
                dto.setFeed_content(project.getFeed_content());
                dto.setGood(project.getGood());
                dto.setGood_num(project.getGood_num());
                dto.setHit(project.getHit());
                dto.setPlace(project.getPlace());
                dto.setFeed_tag(project.getFeed_tag());
                dto.setGoods(project.getGoods());
                dto.setFeed_file(project.getFeed_file());
                dto.setFeed_type(project.getFeed_type());
                dto.setLogtime(project.getLogtime());
                return dto;
            })
            .collect(Collectors.toList());
    }


	
	
}
