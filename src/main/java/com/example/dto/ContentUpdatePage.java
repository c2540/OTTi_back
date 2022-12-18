package com.example.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Table(name = "CONTENT_UPDATE_TABLE")
@Data
@SequenceGenerator(
    name = "CONTENT_UPDATE_TABLE",
    sequenceName = "SEQ_CONTENT_UPDATE_TABLE",
    initialValue = 1,
    allocationSize = 1
    )
public class ContentUpdatePage {
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "CONTENT_UPDATE_TABLE"
    )
    @Column(name = "NO")
    Long no;

    @Column(name = "PROVIDER", length = 100)
    String provider;

    @Column(name = "genre", length = 100)
    String genre;

    @Column(name = "CONTENT_CODE")
    Long contentcode;

    @Column(name = "ADULT", length = 10)
    String adult;
    
    @Column(name = "TYPE", length = 10)
    String type;

    @Column(name = "NAME", length = 100)
    String name;

    @Column(name = "OVERVIEW")
    @Lob
    String overview;

    @Column(name = "TITLE", length = 100)
    String title;

    @Column(name = "RUNTIME")
    Long run_time;

    @Column(name = "EPISODE_RUN_TIME")
    Long episode_run_time;

    @Column(name = "RELEASE_DATE", length = 20)
    String release_date;

    @Column(name = "FIRST_AIR_DATE", length = 20)
    String first_air_date;

    @Column(name = "NUMBER_OF_EPISODES")
    Long number_of_episodes;

    @Column(name = "NUMBER_OF_SEASONS")
    Long number_of_seasons;

    @Column(name = "TRAILER")
    @Lob
    String trailer;

    @Column(name = "YOUTUBE_REVIEW", length = 100)
    String youtubereview;

    @Column(name = "IMDB_ID", length = 20)
    String imdb_id;

    @Column(name = "USERID", length = 100)
    String userid;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @Column(name = "REGDATE", updatable = false)
    private Date regdate = null;
}
