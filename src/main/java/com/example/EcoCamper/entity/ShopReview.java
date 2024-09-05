package com.example.EcoCamper.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
public class ShopReview {
	private String shopreviewpcode;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROJECT_SEQUENCE_GENERATOR")
    @SequenceGenerator(name = "PROJECT_SEQUENCE_GENERATOR", sequenceName = "shopreviewseq", initialValue = 1, allocationSize = 1)
	private int shopreviewseq;
	private String shopreviewid;
	private String shopreviewcontent;
    private int shopreviewhit;
    @Temporal(TemporalType.DATE)
    private Date logtime;
}
