package com.example.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "CENTER")
@SequenceGenerator(
    name = "CENTER_SEQ",
    sequenceName = "SEQ_CENTER_NO",
    initialValue = 1,
    allocationSize = 1
    )
public class Center {
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "CENTER_SEQ"
        )
    @Column(name = "ID")
    Long id;

    @Column(name = "TITLE", length = 100)
    String title;

    @Lob
    @Column(name = "CONTENT")
    String content;

    @Column(name = "ANSWER")
    Long answerYN=0L;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @Column(name = "REGDATE", updatable = false)
    private Date regdate = null;

    // @OneToMany(mappedBy= "service", cascade = CascadeType.REMOVE )
    // List<Center_Answer> answer;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    Member member;
}
