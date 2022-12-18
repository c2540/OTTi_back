package com.example.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
@Entity
@Table(name = "MEMBER")
public class Member {
    @Id
    @Column(name = "USERID", length = 100)
    String userid;

    @Column(name = "USERPW", length = 100)
    @JsonProperty(access = Access.WRITE_ONLY)
    String userpw;

    @Column(name = "PHONE", length = 20)
    String phone;

    @Column(name = "BIRTH", length = 20)
    String birth;

    @Column(name = "ROLE", length = 20)
    String role = "MEMBER";

    @Column(name = "DELETE")
    Long delete = 0L;

    @Column(name = "BAN")
    Long ban = 0L;

    @Column(name = "NICKNAME", length = 20)
    String nickname;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @Column(name = "REGDATE", updatable = false)
    Date regdate;

    @Column(name = "SOCIAL", length = 20)
    String social;

    // @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    // List<Center> center;

    // @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    // List<Center_Answer> answer;

    // @OneToMany(mappedBy = "userid", cascade = CascadeType.REMOVE)
    // List<Movie_Later> movielater;

    // @OneToMany(mappedBy = "userid", cascade = CascadeType.REMOVE)
    // List<Drama_Later> dramalater;

    // @OneToMany(mappedBy = "userid", cascade = CascadeType.REMOVE)
    // List<Drama_Like> dramalike;

    // @OneToMany(mappedBy = "userid", cascade = CascadeType.REMOVE)
    // List<Movie_Like> movielike;

    // @OneToMany(mappedBy = "userid", cascade = CascadeType.REMOVE)
    // List<People_Like> peoplelike;

    // @OneToMany(mappedBy = "userid", cascade = CascadeType.REMOVE)
    // List<Review_Drama_Coment> reviewdramacoment;

    // @OneToMany(mappedBy = "userid", cascade = CascadeType.REMOVE)
    // List<Review_Drama_Like> reviewdramalike;

    // @OneToMany(mappedBy = "userid", cascade = CascadeType.REMOVE)
    // List<Review_Movie_Coment> reviewmoviecoment;

    // @OneToMany(mappedBy = "userid", cascade = CascadeType.REMOVE)
    // List<Review_Movie_Like> reviewmovielike;

    // @OneToMany(mappedBy = "userid", cascade = CascadeType.REMOVE)
    // List<MemberImage> memberimages;

}
