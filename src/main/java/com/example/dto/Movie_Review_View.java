package com.example.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "MOVIE_REVIEW_VIEW")
public class Movie_Review_View {
    @Id
    @Column(name = "ID")
    Long id;

    @ManyToOne
    @JoinColumn(name = "MOVIE_CODE", referencedColumnName = "ID")
    MovieContent movieContent;
    
    @Column(name ="SCORE")
    Long score;

    @Column(name = "CONTENT", length = 200)
    String content;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    Member member;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @Column(name = "REGDATE", updatable = false)
    Date regdate;

    @OneToMany(mappedBy = "reviewmovie", cascade = CascadeType.REMOVE)
    List<Review_Movie_Coment> reviewmoviecoment;

    @OneToMany(mappedBy = "reviewmovie", cascade = CascadeType.REMOVE)
    List<Review_Movie_Like> reviewmovielike;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "IMAGENO", referencedColumnName = "imageno")
    MemberImage no;
}
