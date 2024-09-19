package com.example.EcoCamper.dao;

import java.util.List;
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
    	Feed feed = dto.toEntity();
        return repository.save(feed) != null;
    }

	// 작성
	
	public boolean feedWriteVideo(FeedDTO dto) {
		
		Feed feed = dto.toEntity();
        return repository.save(feed) != null;
        }

	
	public List<FeedDTO> findAll() {
        return repository.findAllByOrderByLogtimeDesc().stream()
            .map(feed -> {
            	FeedDTO dto = new FeedDTO();
            	dto.setSeq(feed.getSeq());
                dto.setId(feed.getId());
                dto.setOutdoor(feed.getOutdoor());
                dto.setFeed_subject(feed.getFeed_subject());
                dto.setFeed_content(feed.getFeed_content());
                dto.setPlace(feed.getPlace());
                dto.setTags(feed.getTags());
                dto.setGoods(feed.getGoods());
                dto.setFeed_file(feed.getFeed_file());
                dto.setFeed_type(feed.getFeed_type());
                dto.setLogtime(feed.getLogtime());
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
		if (feed != null) {
	        // 기존 값 설정
	        String existingTags = feed.getTags();
	        String newTags = dto.getTags();

	        // 새 태그가 공백이 아니면 업데이트
	        if (newTags != null && !newTags.trim().isEmpty()) {
	            feed.setTags(newTags);
	        } else {
	            // 새 태그가 공백일 경우 기존 태그 유지
	            feed.setTags(existingTags);
	        }

	        // 나머지 속성 설정
	        feed.setSeq(dto.getSeq());
	        feed.setId(dto.getId());
	        feed.setOutdoor(dto.getOutdoor());
	        feed.setFeed_subject(dto.getFeed_subject());
	        feed.setFeed_content(dto.getFeed_content());
	        feed.setPlace(dto.getPlace());
	        feed.setGoods(dto.getGoods());
	        // feed_file 설정 (새 값이 null이거나 빈 문자열이면 기존 값 유지)
	        if (dto.getFeed_file() != null && !dto.getFeed_file().trim().isEmpty()) {
	            feed.setFeed_file(dto.getFeed_file());
	        }
	        feed.setFeed_type(dto.getFeed_type());

	        repository.save(feed); // 피드 저장

	        result = true;
	    }
	    return result;
	}
    
            
	 public List<Feed> getFeedsById(String id) {
	        // 로그인한 사용자의 피드만 가져오는 DAO 또는 Repository 호출
	        return repository.findByIdOrderByLogtimeDesc(id);
	    }
	 
	 public List<Feed> getFeedsByUserId(String id) {
	        return repository.findFeedsByUserIdAndReviewId(id);
	    }
	 
	 public List<Feed> findFeedsBySaveSeq(String id){
		 	return repository.findFeedsBySeqIdAndSaveSeq(id);
	 }
 	
	// 태그로 피드 검색
	    public List<Feed> findFeedsByTagName(String tagName) {
	        return repository.findByTagName(tagName);
	    }

	    public List<Feed> feedBytopfeeds() {
	    	return repository.findTop5FeedsByLikes();
			
		}
}
