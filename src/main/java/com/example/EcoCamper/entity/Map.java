package com.example.EcoCamper.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
    @Column(name = "place_pic", length = 4000)
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
    
    // 다대다 관계: 지역 필터 선택
    @ManyToMany
    @JoinTable(
    		name = "placefilter_region",
    		joinColumns = @JoinColumn(name = "place_seq"),
    		inverseJoinColumns = @JoinColumn(name = "id")
    )
    private List<Region> regions = new ArrayList<>();
    
    // 다대다 관계: 공간유형 필터 선택
    @ManyToMany
    @JoinTable(
    		name = "placefilter_category",
    		joinColumns = @JoinColumn(name = "place_seq"),
    		inverseJoinColumns = @JoinColumn(name = "id")
    )
    private List<Category> categories = new ArrayList<>();

    // 다대다 관계: 편의시설 필터 선택
    @ManyToMany
    @JoinTable(
    		name = "placefilter_facility",
    		joinColumns = @JoinColumn(name = "place_seq"),
    		inverseJoinColumns = @JoinColumn(name = "id")
    )
    private List<Facility> facilities = new ArrayList<>();

    // 다대다 관계: 주변환경 필터 선택
    @ManyToMany
    @JoinTable(
    		name = "placefilter_environment",
    		joinColumns = @JoinColumn(name = "place_seq"),
    		inverseJoinColumns = @JoinColumn(name = "id")
    )
    private List<Environment> environments = new ArrayList<>();

    // 다대다 관계: 계절 필터 선택
    @ManyToMany
    @JoinTable(
    		name = "placefilter_season",
    		joinColumns = @JoinColumn(name = "place_seq"),
    		inverseJoinColumns = @JoinColumn(name = "id")
    )
    private List<Season> seasons = new ArrayList<>();
    
    // 다대일 관계: 검색어 하나에 여러개의 장소
    @ManyToOne
    @JoinColumn(name = "id")
    private Keyword keyword;
}
