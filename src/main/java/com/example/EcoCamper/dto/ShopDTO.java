package com.example.EcoCamper.dto;

import java.util.Date;

import com.example.EcoCamper.entity.Shop;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ShopDTO {

	private String pcode;
	private String pname;
	private String ptype;
	private int pprice;
	private int pqty;
	private int phit;
	private String pimg;
	private Date logtime;

	public Shop toEntity() {
		return new Shop(pcode, pname, ptype, pprice, pqty, phit, pimg, logtime);
	}
}
