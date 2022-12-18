package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.ContentDrama_Ott_View;

@Repository
public interface ContentDrama_Ott_ViewRepository extends JpaRepository<ContentDrama_Ott_View,Long> {
    public List<ContentDrama_Ott_View> findByOttcode(Long porv,Pageable pageable);
    public Long countByOttcode(Long porv);
    public List<ContentDrama_Ott_View> findBy(Pageable pageable);
}
