package com.example.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "OTT_CATE")
public class OTT_Cate {
    @Id
    @Column(name = "ID")
    Long id;

    @Column(name = "PROVIDER_NAME",length = 20)
    String provider_name;

    @Column(name = "LOGO_PATH", length = 100)
    String logo_path;

    // @OneToMany(mappedBy = "provcate", cascade = CascadeType.REMOVE)
    // List<OTT_Drama> ott_Dramas;

    // @OneToMany(mappedBy = "provcate", cascade = CascadeType.REMOVE)
    // List<OTT_Movie> ott_Movies;
    
}
