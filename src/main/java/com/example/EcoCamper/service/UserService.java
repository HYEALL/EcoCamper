package com.example.EcoCamper.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.EcoCamper.dao.UserDAO;
import com.example.EcoCamper.dto.UserDTO;
import com.example.EcoCamper.dto.UserResponseDTO;
import com.example.EcoCamper.entity.User;
import com.example.EcoCamper.jwt.TokenProvider;

@Service
public class UserService {
	@Autowired
	private UserDAO dao;

	
	public User join(final UserDTO userDTO) {
		return dao.join(userDTO);
	}

	public User login(String id, String pwd, final PasswordEncoder encoder) {
		return dao.login(id, pwd, encoder);
	}
	
	public User getUser(String id) {
		return dao.getUser(id);
	}

	public boolean checkId(String id) {
		return dao.checkId(id);
	}
	public Page<User> findAll(int page, int size) {
		return dao.findAll(page, size);
	}
	public int getTotalA() {
		return dao.getTotalA();
	}
	
	public boolean userModify(UserDTO dto) {
		return dao.userModify(dto);
	}
	
	
}
