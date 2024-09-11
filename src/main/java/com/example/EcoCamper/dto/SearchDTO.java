package com.example.EcoCamper.dto;

import java.util.List;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Table(name="search")
public class SearchDTO {
	private String keyword;
    private List<String> regions;
    private List<String> categories;
    private List<String> facilities;
    private List<String> environments;
    private List<String> seasons;
}