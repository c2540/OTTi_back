package com.example.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Immutable;

import lombok.Data;

@Data
@Immutable
@Entity
@Table(name = "CONTENT_DRAMA_Genre_VIEW")
public class ContentDrama_Genre_View {
    
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
    Long episoderuntime;

    @Column(name = "FIRST_AIR_DATE", length = 20)
    String firstairdate;

    @Column(name = "NUMBER_OF_SEASONS")
    Long number_of_seasons;

    @Lob
    @Column(name = "OVERVIEW")
    String overview;

    @Column(name = "NUMBER_OF_EPISODES")
    Long number_of_episodes;

    @Column(name = "POPULARITY")
    Long popularity;

    @Column(name = "VOTE_AVERAGE")
    Long voteaverage;

    @Column(name = "VOTE_COUNT")
    Long vote_count;

    @Column(name = "TRAILER")
    @Lob
    String trailer;

    @Column(name = "IMDB_ID", length = 20)
    String imdb_id;

    @Column(name = "GENRE_CODE")
    Long genrecode;

    @Transient
    Long cnt;
}
