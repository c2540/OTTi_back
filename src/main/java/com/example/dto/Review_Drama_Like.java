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
@Table(name = "REVIEW_DRAMA_LIKE")
@SequenceGenerator(
    name = "REVIEW_DRAMA_LIKE",
    sequenceName = "SEQ_REVIEW_DRAMA_LIKE_ID",
    initialValue = 1,
    allocationSize = 1
    )
public class Review_Drama_Like {
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "REVIEW_DRAMA_LIKE"
         )
    @Column(name = "ID")
    Long id;

    @ManyToOne
    @JoinColumn(name = "DRAMA_REVIEW_CODE", referencedColumnName = "ID")
    Review_Drama reviewdrama;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    Member userid;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @Column(name = "REGDATE", updatable = false)
    Date regdate;
}