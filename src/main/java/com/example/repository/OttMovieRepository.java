package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.OTT_Movie;

@Repository
public interface OttMovieRepository extends JpaRepository<OTT_Movie,Long> {
    public List<OTT_Movie> findByMoviecontent_id(Long no);
    public List<OTT_Movie> findByProvcate_id(Long no);
    public boolean existsByMoviecontent_idAndProvcate_id(Long no, Long prov);
}
