package com.example.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "PEOPLE")
@Data
public class People {
    @Id
    @Column(name = "ID")
    Long id;

    @Column(name = "NAME", length = 100)
    String name;

    @Column(name = "SNS", length = 100)
    String sns;

    @OneToMany(mappedBy = "people", cascade = CascadeType.REMOVE )
    List<People_Drama> peopledrama;

    @OneToMany(mappedBy = "people", cascade = CascadeType.REMOVE )
    List<People_Movie> peoplemovie;

    // @OneToMany(mappedBy = "people", cascade = CascadeType.REMOVE )
    // List<People_Like> peoplelike;
    
}
