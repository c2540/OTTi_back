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
@Table(name = "PEOPLE_LIKE")
@Data
@SequenceGenerator(
    name = "PEOPLE_LIKE",
    sequenceName = "SEQ_PEOPLE_LIKE_NO",
    initialValue = 1,
    allocationSize = 1
    )
public class People_Like {
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "PEOPLE_LIKE"
    )
    @Column(name = "ID")
    Long id;
    
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @Column(name = "REGDATE", updatable = false)
    private Date regdate = null;

    @Column(name = "PEOPLE_CODE")
    Long peoplecode;
    // @ToString.Exclude
    // @ManyToOne
    // @JoinColumn(name = "PEOPLE_CODE", referencedColumnName = "ID")
    // People people;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    Member userid;
}
