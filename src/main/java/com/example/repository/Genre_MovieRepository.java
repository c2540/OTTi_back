package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.Genre_Movie;

@Repository
public interface Genre_MovieRepository extends JpaRepository<Genre_Movie,Long> {
    public List<Genre_Movie> findByMoviecontent_id(Long no);
    public boolean existsByMoviecontent_idAndGenrecate_id(Long no, Long genre);
}
