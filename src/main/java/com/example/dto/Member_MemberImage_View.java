package com.example.dto;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "MEMBER_MEMBERIMAGE_VIEW")
public class Member_MemberImage_View {
    @Id
    @Column(name="USERID")
    String userid;

    @Column(name="NICKNAME")
    String nickname;

    @Column(name="DELETE")
    Long delete;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    @Column(name = "REGDATE", updatable = false)
    Date regdate;
    
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "IMAGENO", referencedColumnName = "imageno")
    MemberImage no;
}
