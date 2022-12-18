package com.example.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "Drama_CONTENT")
public class DramaContent {
    @Id
    @Column(name = "ID")
    Long id;

    @Column(name = "NAME", length = 100)
    String name;

    @Column(name = "ADULT", length = 10)
    String adult;
    
    @Column(name = "POSTER_PATH" ,length = 100)
    String poster_path;

    @Column(name = "BACKDROP_PATH", length = 100)
    String backdrop_path;

    @Column(name = "EPISODE_RUN_TIME")
    Long episode_run_time;

    @Column(name = "FIRST_AIR_DATE", length = 20)
    String first_air_date;

    @Column(name = "NUMBER_OF_SEASONS")
    Long number_of_seasons;

    // @Column(name = "SEASONS")
    // String seasons;

    @Lob
    @Column(name = "OVERVIEW")
    String overview;

    @Column(name = "NUMBER_OF_EPISODES")
    Long number_of_episodes;

    @Column(name = "POPULARITY")
    Long popularity;

    @Column(name = "VOTE_AVERAGE")
    Double vote_average;

    @Column(name = "VOTE_COUNT")
    Long vote_count;

    @Column(name = "TRAILER")
    @Lob
    String trailer;

    @Column(name = "YOUTUBE_REVIEW", length = 100)
    String youtubereview;

    @Column(name = "IMDB_ID", length = 20)
    String imdb_id;
    
    @Column(name = "HIT")
    Long hit = 0L;

    // @OneToMany(mappedBy = "dramacontent", cascade = CascadeType.REMOVE)
    // List<OTT_Drama> ottcate;

    // @OneToMany(mappedBy = "dramacontent", cascade = CascadeType.REMOVE)
    // List<Genre_Drama> genrecate;

    // @OneToMany(mappedBy = "dramacontent", cascade = CascadeType.REMOVE)
    // List<People_Drama> peopledrama;

    // @OneToMany(mappedBy = "dramacode", cascade = CascadeType.REMOVE)
    // List<Drama_Later> dramalater;

    // @OneToMany(mappedBy = "dramacode", cascade = CascadeType.REMOVE)
    // List<Drama_Like> dramalike;
}
