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
@Data
@Table(name = "REVIEW_DRAMA")
@SequenceGenerator(
    name = "REVIEW_DRAMA",
    sequenceName = "SEQ_REVIEW_DRAMA_ID",
    initialValue = 1,
    allocationSize = 1
    )
public class Review_Drama {
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "REVIEW_DRAMA"
         )
    @Column(name = "ID")
    Long id;

    @ManyToOne
    @JoinColumn(name = "DRAMA_CODE", referencedColumnName = "ID")
    DramaContent dramaContent;

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

    // @OneToMany(mappedBy = "reviewdrama", cascade = CascadeType.REMOVE)
    // List<Review_Drama_Coment> reviewdramacoment;

    // @ToString.Exclude
    // @OneToMany(mappedBy = "reviewdrama", cascade = CascadeType.REMOVE)
    // List<Review_Drama_Like> reviewdramalike;

}
