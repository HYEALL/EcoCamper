package com.example.EcoCamper.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

	// 회원 정보 수정
	public boolean userModify(UserDTO dto) {
		// 1. 기존 데이터 확인
		User user = repository.findById(dto.getId()).orElse(null);
		boolean result = false;

		if (user != null) {
			// 2. 데이터 수정
			user.setName(dto.getName());
			user.setPwd(passwordEncoder.encode(dto.getPwd()));
			user.setGender(dto.getGender());
			user.setTel(dto.getTel());
			user.setAddr(dto.getAddr());
			// 3. 저장
			User user_result = repository.save(user);
			if (user_result != null)
				result = true;
		}
		return result;
	}

	public User kakaoLogin(Map<String, Object> userInfo) {
		String email = (String) userInfo.get("email");
		//String nickname = (String) userInfo.get("nickname");
     
        // 카카오 로그인 토큰은 email 과 password 로 만들어줌 
        //String username = email;
        // 패스워드 = 카카오 Id + ADMIN TOKEN
        //String password = kakaoId + ADMIN_TOKEN;

        // DB 에 Kakao email과 같은 회원이 있는지 확인
        User kakaoUser = repository.findByEmail(email).orElse(null);
        
        // 있으면 로그인
        if (kakaoUser != null) {
        	 return kakaoUser;
        } else {
        	return null;
        }
       /* // 없으면 카카오 정보로 회원가입
        if (kakaoUser == null) {
            // 패스워드 인코딩
            String encodedPassword = passwordEncoder.encode(password);
            // ROLE = 사용자


            kakaoUser = new User(username, encodedPassword, nickname,  kakaoId);
            userRepository.save(kakaoUser);
        }*/
        

        
    }
}
