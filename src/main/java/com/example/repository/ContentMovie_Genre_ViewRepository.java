package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.ContentMovie_Genre_View;

@Repository
public interface ContentMovie_Genre_ViewRepository extends JpaRepository<ContentMovie_Genre_View,Long> {
    public List<ContentMovie_Genre_View> findByGenrecode(Long genre,Pageable pageable);
    public Long countByGenrecode(Long genre);
    public List<ContentMovie_Genre_View> findAllById(Long no);
}
