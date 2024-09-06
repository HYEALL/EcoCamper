package com.example.EcoCamper.entity;

import jakarta.persistence.Column;
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
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LIKES_SEQUENCE_GENERATOR")
    @SequenceGenerator(name = "LIKES_SEQUENCE_GENERATOR", sequenceName = "likes_num", initialValue = 1, allocationSize = 1)
    private int likes_num; // 좋아요 번호

    @Column(name = "review_id")
    private int reviewId; // 리뷰 번호
    @Column(name = "user_id")
    private String userId;
}
