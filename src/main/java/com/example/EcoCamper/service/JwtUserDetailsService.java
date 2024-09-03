package com.example.EcoCamper.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.EcoCamper.entity.User;
import com.example.EcoCamper.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return userRepository.findById(id)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }

    // DB에 해당하는 User 값이 존재한다면 UserDetails 객체로 리턴
    private UserDetails createUserDetails(User user) {
    	//System.out.println("role" + user.getRole());
        return User.builder()
        		.id(user.getId())
                .name(user.getName())
                .pwd(user.getPwd()) // encoder  필요한가?
                .role(user.getRole())
                .build();
    }
}