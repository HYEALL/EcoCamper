package com.example.EcoCamper.dto;

import java.util.ArrayList;

import com.example.EcoCamper.entity.Tag;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TagDTO {
    private int tag_seq; // 태그의 고유 ID
    private String tag_name; // 태그 이름
    
    public String getTagName() {
        return tag_name;
    }


    // TagDTO를 Tag 엔티티로 변환
    public Tag toEntity() {
        return new Tag(tag_seq, tag_name, new ArrayList<>());
    }
}