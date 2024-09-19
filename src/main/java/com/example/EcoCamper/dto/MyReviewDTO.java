package com.example.EcoCamper.dto;

import java.util.Date;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class MyReviewDTO {
	

		private int shopreviewseq ;
		private String shopreviewpcode ;
		private String shopreviewid ;
		private String shopreviewcontent ;
	    private int rating ;
	    @Temporal(TemporalType.DATE)
	    private Date logtime ;
	    
	    private String pname;

		public MyReviewDTO(int shopreviewseq, String shopreviewpcode, String shopreviewid, String shopreviewcontent,
				int rating, Date logtime, String pname) {
			super();
			this.shopreviewseq = shopreviewseq;
			this.shopreviewpcode = shopreviewpcode;
			this.shopreviewid = shopreviewid;
			this.shopreviewcontent = shopreviewcontent;
			this.rating = rating;
			this.logtime = logtime;
			this.pname = pname;
		}
	    
	    
	    
	    
	    
	    
	    
}




