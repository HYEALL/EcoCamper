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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "map")
public class Map {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "MAP_SEQUENCE_GENERATOR")
    @SequenceGenerator(name = "MAP_SEQUENCE_GENERATOR",
                        sequenceName = "place_seq_place",
                        initialValue = 1, allocationSize = 1)
    private int place_seq;

    private String place_category;
    private String place_address;
    private String place_oldaddr;
    private String place_postcode;
    private String place_pic;
    private String place_name;
    private String place_description;
    private String place_keypoint;
    private String place_keypointicon;
    private String place_precaution;
    private String place_precautionicon;
    private String place_bookinglink;
    private String place_tel;
    private double place_editorscore;
    private double place_cleanscore;
    private double place_scenescore;
    private double place_independencescore;
    private double place_facilityscore;
    private String place_facility;
    private String place_environment;
    private String place_season;
    private String place_youtube;
    private String place_youtubelink;
    private String place_youtubetitle;
    private String place_youtubevideo;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date upload_date;
}
