package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.DramaContent;

@Repository
public interface ContentDRepository extends JpaRepository<DramaContent,Long> {
    public List<DramaContent> findByNameContaining(String text);
    public List<DramaContent> findBy(Pageable pageable);
    public Long countBy();
    public List<DramaContent> findTop10ByOrderByHitDescPopularityDesc();
}
