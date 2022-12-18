package com.example.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "PEOPLE_Movie")
@Data
@SequenceGenerator(
    name = "PEOPLE_MOVIE",
    sequenceName = "SEQ_PEOPLE_MOVIE_NO",
    initialValue = 1,
    allocationSize = 1
    )
public class People_Movie {
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "PEOPLE_MOVIE"
         )
    @Column(name = "ID")
    Long id;

    @Column(name = "PART", length = 20)
    String part;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "PEOPLE_CODE", referencedColumnName = "ID")
    People people;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "MOVIE_CODE", referencedColumnName = "ID")
    DramaContent moviecontent;
    
}
