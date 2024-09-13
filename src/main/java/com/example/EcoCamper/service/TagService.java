package com.example.EcoCamper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EcoCamper.dao.TagDAO;
import com.example.EcoCamper.entity.Tag;

@Service
public class TagService {

	  @Autowired
	   TagDAO dao;

	  public Tag findByTagName(String tagName) {
	        return dao.findByName(tagName);
	    }

	  public Tag save(Tag tag) {
	        return dao.save(tag);
	    }
}
