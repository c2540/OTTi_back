package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.ContentDrama_View;

@Repository
public interface ContentDrama_ViewRepository extends JpaRepository<ContentDrama_View,Long> {
    public List<ContentDrama_View> findBy(Pageable pageable);
    public Long countBy();
}
