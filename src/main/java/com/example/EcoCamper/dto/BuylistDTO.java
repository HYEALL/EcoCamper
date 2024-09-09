package com.example.EcoCamper.dto;

import java.util.Date;

import com.example.EcoCamper.entity.Buylist;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BuylistDTO {
	private int		buyseq;
	private String  buyid;
	private String  productcode;
	private int		productqty;
	private int		productprice ;
	private String  receivename;
	private String  baddress;
	private String  bphone;
	private String  bpayment;
	private String  bcancel ;
	@Temporal(TemporalType.DATE)
	private Date    logtime;
	
	public Buylist toEntity() {
		return new Buylist(buyseq, buyid, productcode, productqty, productprice, receivename, baddress, bphone, bpayment, bcancel, logtime);
	}
}
