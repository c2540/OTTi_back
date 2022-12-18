package com.example.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "MOVIE_LATER")
@Data
@SequenceGenerator(
    name = "LATER_MOVIE",
    sequenceName = "SEQ_LATER_MOVIE_NO",
    initialValue = 1,
    allocationSize = 1
    )
public class Movie_Later {
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "LATER_MOVIE"
         )
    @Column(name = "ID")
    Long id;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @Column(name = "REGDATE", updatable = false)
    private Date regdate = null;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "MOVIE_CODE", referencedColumnName = "ID")
    MovieContent moviecode;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    Member userid;

    
}
