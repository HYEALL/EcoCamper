package com.example.EcoCamper.dto;

import java.util.Date;

import com.example.EcoCamper.entity.Reply;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ReplyDTO {
	private int num; // 댓글 번호
	private int seq; // 댓글 단 글번호
	private String writer; // -- 작성자(유저 아이디) 
	private String content; // 댓글 내용
	private Date logtime;
	
	public Reply toEntity() {
		return new Reply(num, seq, writer, content, logtime);
	}

}
