package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.ContentMovie_View;

@Repository
public interface ContentMovie_ViewRepository extends JpaRepository<ContentMovie_View,Long> {
    public List<ContentMovie_View> findBy(Pageable pageable);
    public List<ContentMovie_View> findByVoteaverageGreaterThan(Pageable pageable, Double voteavg);
    public Long countBy();
}
