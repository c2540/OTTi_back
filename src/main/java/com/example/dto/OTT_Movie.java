package com.example.dto;

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
@Data
@Table(name = "OTT_MOVIE")
@SequenceGenerator(
    name = "OTT_MOVIE",
    sequenceName = "SEQ_OTT_MOVIE_NO",
    initialValue = 1,
    allocationSize = 1
    )
public class OTT_Movie {
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "OTT_MOVIE"
    )
    Long no;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "OTT_CODE", referencedColumnName = "ID" )
    OTT_Cate provcate;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "CONTENT_CODE", referencedColumnName = "ID" )
    MovieContent moviecontent;
    
}
