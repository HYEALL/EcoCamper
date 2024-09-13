package com.example.EcoCamper.dto;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.example.EcoCamper.entity.Feed;
import com.example.EcoCamper.entity.Tag;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FeedDTO {
	 private int seq;
	    private String id;
	    private String outdoor;
	    private String feed_subject;
	    private String feed_content;

	    private String place;
	    private List<String> tags; // 태그 리스트 추가
	    private String goods;
	    private String feed_file;  // 이미지 파일 이름
	    private String feed_type;
	    private Date logtime;

	    // Feed 엔티티로 변환하는 메소드
	    public Feed toEntity(List<Tag> tagEntities) {
	        return new Feed(seq, id, outdoor, feed_subject, feed_content, place, tagEntities, goods, feed_file, feed_type, logtime);
	    }
	 // 파일 이름 문자열을 리스트로 변환
	    public List<String> getImageFileNames() {
	    return Arrays.asList(feed_file.split(","));
	    }
	    
	    // 태그 리스트를 문자열로 변환
	    public String getTagNames() {
	        if (tags == null || tags.isEmpty()) {
	            return "";
	        }
	        return String.join(", ", tags); // 태그 리스트를 콤마로 구분하여 문자열로 변환
	    }
	}
