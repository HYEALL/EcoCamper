package com.example.EcoCamper.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "feeds")
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAG_SEQUENCE_GENERATOR")
    @SequenceGenerator(name = "TAG_SEQUENCE_GENERATOR", sequenceName = "tag_seq", initialValue = 1, allocationSize = 1)
	private int tag_seq;
	private String tag_name;
	
	
	@ManyToMany(mappedBy = "tags")
	private List<Feed> feeds = new ArrayList<>();

}
