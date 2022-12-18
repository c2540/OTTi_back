package com.example.dto;



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
import javax.persistence.Transient;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "MEMBERIMAGE")
@Data
@SequenceGenerator(
    name = "IMAGE_NO",
    sequenceName = "SEQ_IMAGE_NO",
    initialValue = 1,
    allocationSize = 1
    )
public class MemberImage {
    @Id
    @Column(name = "IMAGENO")
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "IMAGE_NO"
    )
    Long no;

    @Column(name = "IMAGENAME", length = 200)
    String imagename;

    @Column(name = "IMAGETYPE", length = 30)
    String imagetype;

    @ToString.Exclude
    @Lob
    byte[] imagedata;

    @Column(name = "IMAGESIZE")
    Long imagesize;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "USERID", referencedColumnName = "userid")
    Member userid;

    @Transient
    String imageurl;
}
