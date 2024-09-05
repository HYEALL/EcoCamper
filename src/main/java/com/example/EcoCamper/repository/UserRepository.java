package com.example.EcoCamper.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EcoCamper.entity.User;

public interface UserRepository extends JpaRepository<User, String>{
	Optional<User> findById(String id);
	Page<User> findAll(Pageable pageable);
}
