package com.example.EcoCamper.dto;

import java.util.Date;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderlistDTO {
	private int		buyseq;
	private String  buyid;
	private String  productcode;
	private int		productqty;
	private int		productprice ;
	private String  receivename;
	private String bzipcode;
	private String  baddr1;
	private String  baddr2;
	private String  bphone;
	private String  bpayment;
	private String  bcancel ;
	@Temporal(TemporalType.DATE)
	private Date    logtime;
	private String pname;
	
	
	public OrderlistDTO(int buyseq, String buyid, String productcode, int productqty, int productprice,
			String receivename, String bzipcode, String baddr1, String baddr2, String bphone, String bpayment, String bcancel, Date logtime,
			String pname) {
		super();
		this.buyseq = buyseq;
		this.buyid = buyid;
		this.productcode = productcode;
		this.productqty = productqty;
		this.productprice = productprice;
		this.receivename = receivename;
		this.bzipcode = bzipcode;
		this.baddr1 = baddr1;
		this.baddr2 = baddr2;
		this.bphone = bphone;
		this.bpayment = bpayment;
		this.bcancel = bcancel;
		this.logtime = logtime;
		this.pname = pname;
	}
	
	

	
}
