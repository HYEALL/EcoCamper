package com.example.EcoCamper.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.EcoCamper.dto.UserDTO;
import com.example.EcoCamper.dto.UserResponseDTO;
import com.example.EcoCamper.entity.User;
import com.example.EcoCamper.repository.UserRepository;

@Repository
public class UserDAO {
	@Autowired
	UserRepository repository;
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public User join(final UserDTO userDTO) {
		if (userDTO == null || userDTO.getId() == null) {
			return null;
		}
		final String userId = userDTO.getId();
		if (repository.existsById(userId)) {
			return null;
		}

		userDTO.setPwd(passwordEncoder.encode(userDTO.getPwd()));
		userDTO.setLogtime(new Date());
		User user = userDTO.toEntity();
		return repository.save(user);
	}

	public User login(String id, String pwd, final PasswordEncoder encoder) {
		User user = repository.findById(id).orElse(null);
		if (user != null && encoder.matches(pwd, user.getPwd())) {
			return user;
		}

		return null;
	}

	public User getUser(String id) {
		return repository.findById(id).orElse(null);
	}

	public boolean checkId(String id) {
		return repository.existsById(id);
	}

	public Page<User> findAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return repository.findAll(pageable);
	}
	public int getTotalA() {
		return (int) repository.count();
	}
}
