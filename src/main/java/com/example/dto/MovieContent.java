package com.example.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "MOVIE_CONTENT")
public class MovieContent {
    @Id
    @Column(name = "ID")
    Long id;

    @Column(name = "TITLE", length = 100)
    String title;

    @Column(name = "ADULT", length = 10)
    String adult;
    
    @Column(name = "POSTER_PATH", length = 100)
    String poster_path;

    @Column(name = "BACKDROP_PATH", length = 100)
    String backdrop_path;

    @Column(name = "RUNTIME")
    Long runtime;

    @Column(name = "RELEASE_DATE", length = 20)
    String release_date;

    @Lob
    @Column(name = "OVERVIEW")
    String overview;

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

    // @OneToMany(mappedBy = "moviecontent", cascade = CascadeType.REMOVE)
    // List<OTT_Movie> ottcate;

    // @OneToMany(mappedBy = "moviecontent", cascade = CascadeType.REMOVE)
    // List<Genre_Movie> genrecate;

    // @OneToMany(mappedBy = "moviecontent", cascade = CascadeType.REMOVE)
    // List<People_Movie> peopemovie;

    // @OneToMany(mappedBy = "moviecode", cascade = CascadeType.REMOVE)
    // List<Movie_Later> movielater;

    // @OneToMany(mappedBy = "moviecode", cascade = CascadeType.REMOVE)
    // List<Movie_Like> movielike;

}
