package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.Genre_Cate;

@Repository
public interface Genre_CateRepository extends JpaRepository<Genre_Cate,Long> {
    
}
