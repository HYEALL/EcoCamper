package com.example.EcoCamper.dto;

import com.example.EcoCamper.entity.Save;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SaveDTO {
	
	private int save_num;
	private int save_seq;
	private String save_id;
	
	public Save toEntity() {
		return new Save(save_num, save_seq, save_id);
	}

}
