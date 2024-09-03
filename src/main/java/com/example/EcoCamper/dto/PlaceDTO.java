package com.example.EcoCamper.dto;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PlaceDTO {
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
    private double place_editorScore;
    private double place_cleanScore;
    private double place_sceneScore;
    private double place_independenceScore;
    private double place_facilityScore;
    private String place_facilities;
    private String place_youtube;
    private String youtubeLink;
    private String place_youtubeTitle;
    private String place_youtubeVideo;
    private Date upload_date;
}
