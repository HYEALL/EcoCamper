package com.example.EcoCamper.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "usertable")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private String name;
	@Id
	private String id;
	private String pwd;
	private int age;
	private String gender;
	private String email;
	private String tel;
	private String addr;
	@Temporal(TemporalType.DATE)
	private Date logtime;
}
