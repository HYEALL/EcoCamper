package com.example.EcoCamper.entity;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "usertable")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
	private String name;
	@Id
	private String id;
	private String pwd;
	@Temporal(TemporalType.DATE)
	private Date age;
	private String gender;
	private String email;
	private String tel;
	private String zipcode;
	private String addr1;
	private String addr2;
	@Temporal(TemporalType.DATE)
	private Date logtime;
	private String role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { // 권한 확인
		Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.role));
        return authorities;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return pwd;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return id;
	}

}
