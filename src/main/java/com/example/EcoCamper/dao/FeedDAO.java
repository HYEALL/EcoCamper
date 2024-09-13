package com.example.EcoCamper.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.EcoCamper.dto.FeedDTO;
import com.example.EcoCamper.entity.Feed;
import com.example.EcoCamper.entity.Tag;
import com.example.EcoCamper.repository.FeedRepository;
import com.example.EcoCamper.repository.TagRepository;





@Repository
public class FeedDAO {
	@Autowired 
	FeedRepository repository;
	@Autowired 
	TagRepository tagRepository;
	
    public boolean feedWritePhoto(FeedDTO dto) {
        // 태그 문자열을 태그 엔티티로 변환
        List<Tag> tagEntities = dto.getTags().stream()
            .map(tagName -> {
                Tag tag = tagRepository.findByTagName(tagName);
                if (tag == null) {
                    tag = new Tag();
                    tag.setTag_name(tagName);  // 새로운 태그 생성
                }
                return tag;
            })
            .collect(Collectors.toList());

        // FeedDTO를 Feed 엔티티로 변환하면서 태그 목록 설정
        Feed feed = dto.toEntity(tagEntities);

        // Feed 엔티티를 저장
        return repository.save(feed) != null;
    }

	// 작성
	
	public boolean feedWriteVideo(FeedDTO dto) {
		
        List<Tag> tagEntities = dto.getTags().stream()
                .map(tagName -> {
                    Tag tag = tagRepository.findByTagName(tagName);
                    if (tag == null) {
                        tag = new Tag();
                        tag.setTag_name(tagName);  // 새로운 태그 생성
                    }
                    return tag;
                })
                .collect(Collectors.toList());

            // FeedDTO를 Feed 엔티티로 변환하면서 태그 목록 설정
            Feed feed = dto.toEntity(tagEntities);

            // Feed 엔티티를 저장
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
                List<String> tagNames = feed.getTags().stream()
                        .map(Tag::getTag_name)
                        .collect(Collectors.toList());
                    dto.setTags(tagNames); // 태그 이름 리스트로 설정
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
		if(feed != null) {
			feed.setSeq(dto.getSeq());
			feed.setId(dto.getId());
			feed.setOutdoor(dto.getOutdoor());
			feed.setFeed_subject(dto.getFeed_subject());
			feed.setFeed_content(dto.getFeed_content());
			feed.setPlace(dto.getPlace());
			feed.setGoods(dto.getGoods());
			feed.setFeed_file(dto.getFeed_file());
			feed.setFeed_type(dto.getFeed_type());
			feed.setLogtime(dto.getLogtime());
			 // 현재 피드의 태그 가져오기
            Set<Tag> existingTags = new HashSet<>(feed.getTags());

            // 새로운 태그 생성
            Set<Tag> newTags = dto.getTags().stream()
                .map(tagName -> {
                    Tag tag = tagRepository.findByTagName(tagName);
                    if (tag == null) {
                        tag = new Tag();
                        tag.setTag_name(tagName);
                        tag = tagRepository.save(tag); // 새 태그 저장
                    }
                    return tag;
                })
                .collect(Collectors.toSet());

            // 기존 태그와 새로운 태그를 비교하여 업데이트
            if (newTags.isEmpty()) {
                // 새로운 태그가 없는 경우 기존 태그 유지
                feed.setTags(new ArrayList<>(existingTags));
            } else {
                // 새로운 태그가 있는 경우 새로운 태그로 업데이트
                existingTags.clear(); // 기존 태그 제거
                existingTags.addAll(newTags); // 새로운 태그 추가
                feed.setTags(new ArrayList<>(existingTags));
            }

            repository.save(feed); // 피드 저장

            return true;
        }
        return false;
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
 	
	   // 태그 이름으로 피드 검색
	    public List<Feed> findFeedsByTag(String tagName) {
	        return repository.findByTagName(tagName);
	    }
}
