package com.example.EcoCamper.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Save {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAVE_SEQUENCE_GENERATOR")
    @SequenceGenerator(name = "SAVE_SEQUENCE_GENERATOR", sequenceName = "save_num", initialValue = 1, allocationSize = 1)
	private int save_num;
	private int save_seq;
	private String save_id;

}
