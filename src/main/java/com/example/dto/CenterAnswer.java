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
@Table(name = "CENTER_ANSWER")
@SequenceGenerator(
    name = "CENTER_ANSWER_SEQ",
    sequenceName = "SEQ_CENTER_ANSWER_NO",
    initialValue = 1,
    allocationSize = 1
    )
public class CenterAnswer {
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "CENTER_ANSWER_SEQ"
        )
    @Column(name = "ID")
    Long id;

    @Column(name = "CONTENT")
    String content;
    
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @Column(name = "REGDATE", updatable = false)
    private Date regdate = null;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "CENTER_NO", referencedColumnName = "ID")
    Center service;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "ADMIN_ID", referencedColumnName = "USERID")
    Member member;
    
}
