package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.ContentDrama_Genre_View;

@Repository
public interface ContentDrama_Genre_ViewRepository extends JpaRepository<ContentDrama_Genre_View,Long> {
    public List<ContentDrama_Genre_View> findByGenrecode(Long genre,Pageable pageable);
    public Long countByGenrecode(Long genre);
}
