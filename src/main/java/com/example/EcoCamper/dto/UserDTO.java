package com.example.EcoCamper.dto;

import java.util.Date;

import com.example.EcoCamper.entity.User;

import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {
	private String token;
	private String name;
	private String id;
	private String pwd;
	private String gender;
	private String email;
	private String tel;
	private String addr;
	@Temporal(TemporalType.DATE)
	private Date logtime;
	@Temporal(TemporalType.DATE)
	private Date age;
	private String role;
	public User toEntity() {
		return new User(name, id, pwd, age, gender, email, tel, addr, logtime, role);
	}
}
