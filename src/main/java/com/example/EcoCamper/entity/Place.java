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
@Table(name = "place_main")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "MAP_SEQUENCE_GENERATOR")
    @SequenceGenerator(name = "MAP_SEQUENCE_GENERATOR",
                        sequenceName = "place_seq_place",
                        initialValue = 1, allocationSize = 1)
    private int place_seq;

    private String place_category;
    private String place_address;
    private String place_name;
    private String place_description;
    private String place_keypoint;
    private String place_keypointIcon;
    private String place_precaution;
    private String place_precautionIcon;
    private String place_bookingLink;
    private double place_editorscore;
    private double place_cleanscore;
    private double place_scenescore;
    private double place_independencescore;
    private double place_facilityscore;
    private String place_facilities;
    private String place_youtube;
    private String youtubeLink;
    private String place_youtubetitle;
    private String place_youtubevideo;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date upload_date;
}
