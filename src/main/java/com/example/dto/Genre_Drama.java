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
@Data
@Table(name = "GENRE_DRAMA")
@SequenceGenerator(
    name = "GENRE_DRAMA",
    sequenceName = "SEQ_GENRE_DRAMA_NO",
    initialValue = 1,
    allocationSize = 1
    )
public class Genre_Drama {
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "GENRE_DRAMA"
         )
    @Column(name = "ID")
    Long no;
    
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "GENRE_CODE", referencedColumnName = "ID" )
    Genre_Cate genrecate;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "CONTENT_CODE", referencedColumnName = "ID" )
    DramaContent dramacontent;
    
}
