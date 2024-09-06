package com.example.EcoCamper.dto;

import com.example.EcoCamper.entity.Likes;

import lombok.Data;
import lombok.ToString;

@Data
@ToString

public class LikesDTO {
	
	private int likes_num; 
	private int review_id; 
	private String user_id;

	
	public Likes toEntity() {
		return new Likes(likes_num, review_id, user_id);
	}
}
