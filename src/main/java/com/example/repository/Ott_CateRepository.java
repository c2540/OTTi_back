package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.OTT_Cate;

@Repository
public interface Ott_CateRepository extends JpaRepository<OTT_Cate,Long> {
    
}
