package com.example.EcoCamper.dto;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MapDTO {
    private int place_seq;
    private String place_category;
    private String place_address;
    private String place_oldaddr;
    private String place_postcode;
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
    private Date upload_date;
}
