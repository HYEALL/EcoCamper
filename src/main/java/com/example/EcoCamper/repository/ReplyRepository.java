package com.example.EcoCamper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EcoCamper.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{

	List<Reply> findBySeq(int seq);
}
