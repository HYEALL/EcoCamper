package com.example.EcoCamper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.EcoCamper.dao.UserDAO;
import com.example.EcoCamper.dto.UserDTO;
import com.example.EcoCamper.entity.User;

@Service
public class UserService {
	@Autowired
	UserDAO dao;
	public User join(final UserDTO userDTO) {
		return dao.join(userDTO);
	}
	// select
	public User login(String id, String pwd, final PasswordEncoder encoder) {
		return dao.login(id, pwd, encoder);
	}
}
