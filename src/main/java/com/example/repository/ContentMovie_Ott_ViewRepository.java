package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.ContentMovie_Ott_View;

@Repository
public interface ContentMovie_Ott_ViewRepository extends JpaRepository<ContentMovie_Ott_View,Long> {
    public List<ContentMovie_Ott_View> findByOttcode(Long porv,Pageable pageable);
    public Long countByOttcode(Long porv);
    public List<ContentMovie_Ott_View> findBy(Pageable pageable);
}
