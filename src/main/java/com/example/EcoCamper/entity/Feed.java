package com.example.EcoCamper.entity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
public class Feed {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FEED_SEQUENCE_GENERATOR")
    @SequenceGenerator(name = "FEED_SEQUENCE_GENERATOR", sequenceName = "seq", initialValue = 1, allocationSize = 1)
	private int seq;
	private String id;
	private String outdoor;
	private String feed_subject;
	private String feed_content;
	private int hit;
	private String place;
	private String feed_tag;
	private String goods;	
	private String feed_file;
	private String feed_type;
	@Temporal(TemporalType.TIMESTAMP)
	private Date logtime;

	
	  public List<String> getImageFileNames() {
		    return Arrays.asList(feed_file.split(","));
		    }
}
