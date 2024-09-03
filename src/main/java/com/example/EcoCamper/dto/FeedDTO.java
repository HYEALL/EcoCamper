package com.example.EcoCamper.dto;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.example.EcoCamper.entity.Feed;

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
	    private int good;
	    private int good_num;
	    private int hit;
	    private String place;
	    private String feed_tag;
	    private String goods;
	    private String feed_file;  // 이미지 파일 이름
	    private String feed_type;
	    private Date logtime;

	    public Feed toEntity() {
	        return new Feed(seq, id, outdoor, feed_subject, feed_content, good, good_num, hit, place, feed_tag, goods, feed_file, feed_type, logtime);
	    }
	    
	 // 파일 이름 문자열을 리스트로 변환
	    public List<String> getImageFileNames() {
	    return Arrays.asList(feed_file.split(","));
	    }
	}
