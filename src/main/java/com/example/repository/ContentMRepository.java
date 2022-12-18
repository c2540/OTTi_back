package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.MovieContent;

@Repository
public interface ContentMRepository extends JpaRepository<MovieContent,Long> {
    public List<MovieContent> findByTitleContaining(String text);
    public List<MovieContent> findTop10ByOrderByHitDescPopularityDesc();
}
