package com.example.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "GENRE_CATE")
public class Genre_Cate {
    @Id
    @Column(name = "ID")
    Long id;

    @Column(name = "NAME", length = 20)
    String name;

    // @OneToMany(mappedBy = "genrecate", cascade = CascadeType.REMOVE)
    // List<Genre_Drama> genre_Dramas;

    // @OneToMany(mappedBy = "genrecate", cascade = CascadeType.REMOVE)
    // List<Genre_Movie> genre_movies;
}
