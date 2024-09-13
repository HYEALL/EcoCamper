package com.example.EcoCamper.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.EcoCamper.entity.Tag;
import com.example.EcoCamper.repository.TagRepository;

@Repository
public class TagDAO {
	 @Autowired
	 TagRepository repository;

	 
	  public Tag findByName(String tagName) {
	        return repository.findByTagName(tagName);
	    }

	  // 태그를 저장하는 메서드
	    public Tag save(Tag tag) {
	        return repository.save(tag);
	    }
	
}
