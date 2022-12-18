package com.example.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Immutable
@Entity
@Table(name = "MOVIE_REVIEW_COMENT_MEMBERIMAGE_VIEW")
public class Movie_Review_Coment_Memberimage_View {
    @Id
    @Column(name = "ID")
    Long id;

    @Column(name = "CONTENT", length = 150)
    String Content;

    @ManyToOne
    @JoinColumn(name = "Movie_REVIEW_CODE", referencedColumnName = "ID")
    Review_Movie reviewmovie;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    Member userid;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @Column(name = "REGDATE", updatable = false)
    Date regdate;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "IMAGENO", referencedColumnName = "imageno")
    MemberImage no;
}
