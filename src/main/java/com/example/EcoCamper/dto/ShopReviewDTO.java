package com.example.EcoCamper.dto;

import java.util.Date;

import com.example.EcoCamper.entity.ShopReview;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ShopReviewDTO {
	private int shopreviewseq ;
	private String shopreviewpcode ;
	private String shopreviewid ;
	private String shopreviewcontent ;
    private int rating ;
    @Temporal(TemporalType.DATE)
    private Date logtime ;
    
    public ShopReview toEntity() {
		return new ShopReview(shopreviewseq, shopreviewpcode, shopreviewid, shopreviewcontent, rating, logtime);
	}
}
