package com.example.EcoCamper.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.EcoCamper.entity.User;
import com.example.EcoCamper.repository.UserRepository;

@Repository
public class UserDAO {
	@Autowired
	UserRepository repository;
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public User create(final User user) {
		if (user == null || user.getId() == null) {
			throw new RuntimeException("Invalid arguments");
		}
		final String userId = user.getId();
		if (repository.existsById(userId)) {
			throw new RuntimeException("userId already exists");
		}
		user.setPwd(passwordEncoder.encode(user.getPwd()));
		return repository.save(user);
	}

	public User login(String id, String pwd, final PasswordEncoder encoder) {
		User user = repository.findById(id).orElse(null);
		if (user != null && encoder.matches(pwd, user.getPwd())) {
			return user;
		}

		return null;
	}
}
