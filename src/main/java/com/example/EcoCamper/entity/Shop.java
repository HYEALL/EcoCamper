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

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Shop {
	@Id
	private String pcode;
	private String pname;
	private String ptype;
	private int pprice;
	private int pqty;
	private int phit;
	private String pimg;
	@Temporal(TemporalType.DATE)
	private Date logtime;
	
}
