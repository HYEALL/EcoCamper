package com.example.EcoCamper.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMENT_SEQUENCE_GENERATOR")
    @SequenceGenerator(name = "COMMENT_SEQUENCE_GENERATOR", sequenceName = "num", initialValue = 1, allocationSize = 1)
	private int num; // 댓글 번호
	private int seq; // 댓글 단 글번호
	private String writer; // -- 작성자(유저 아이디) 
	private String content; // 댓글 내용
	@Temporal(TemporalType.TIMESTAMP)
	private Date logtime;

}
