package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.ContentMovie_Ott_Genre_View;

@Repository
public interface ContentMovie_Ott_Genre_ViewRepository extends JpaRepository<ContentMovie_Ott_Genre_View,Long> {
    public List<ContentMovie_Ott_Genre_View> findByOttcodeAndGenrecode(Long prov, Long genre,Pageable pageable);
    public Long countByOttcodeAndGenrecode(Long prov, Long genre);
}
