package com.example.EcoCamper.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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
public class Buylist {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "BUYLIST_SEQUENCE_GENERATOR")
	@SequenceGenerator(name = "BUYLIST_SEQUENCE_GENERATOR", sequenceName = "seq_Buylist", 
								initialValue = 1, allocationSize = 1)
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
}
