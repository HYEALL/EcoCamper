package com.example.EcoCamper.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.EcoCamper.dto.FeedDTO;
import com.example.EcoCamper.entity.Feed;
import com.example.EcoCamper.repository.FeedRepository;





@Repository
public class FeedDAO {
	@Autowired 
	FeedRepository repository;
	
	public boolean feedWritePhoto(FeedDTO dto) {
		
		return repository.save(dto.toEntity()) != null;
	}
	// 작성
	
	public boolean feedWriteVideo(FeedDTO dto) {
		
		return repository.save(dto.toEntity()) != null;
	}
	
	public List<FeedDTO> findAll() {
        return repository.findAllByOrderByLogtimeDesc().stream()
            .map(project -> {
            	FeedDTO dto = new FeedDTO();
            	dto.setSeq(project.getSeq());
                dto.setId(project.getId());
                dto.setOutdoor(project.getOutdoor());
                dto.setFeed_subject(project.getFeed_subject());
                dto.setFeed_content(project.getFeed_content());
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
	
	public Feed feedView(int seq) {
		
		return repository.findById(seq).orElse(null);

	}
	
	public boolean feedDelete(int seq) {
		
		Feed feed = repository.findById(seq).orElse(null);
		if(feed != null) {
			repository.deleteById(seq);
		}
		
		return !repository.existsById(seq);
		}
	
	public boolean feedUpdate(FeedDTO dto, int seq) {
		boolean result = false;
		Feed feed = repository.findById(seq).orElse(null);
		if(feed != null) {
			feed.setSeq(dto.getSeq());
			feed.setId(dto.getId());
			feed.setOutdoor(dto.getOutdoor());
			feed.setFeed_subject(dto.getFeed_subject());
			feed.setFeed_content(dto.getFeed_content());
			feed.setHit(dto.getHit());
			feed.setPlace(dto.getPlace());
			feed.setFeed_tag(dto.getFeed_tag());
			feed.setGoods(dto.getGoods());
			feed.setFeed_file(dto.getFeed_file());
			feed.setFeed_type(dto.getFeed_type());
			feed.setLogtime(dto.getLogtime());
            repository.save(feed);
            
            result = true;
            
        	return result;	
		} else {
			return result;
		}
	}
	
}
