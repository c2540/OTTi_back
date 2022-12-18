package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.OTT_Drama;

@Repository
public interface OttDramaRepository extends JpaRepository<OTT_Drama,Long> {
    public List<OTT_Drama> findByDramacontent_id(Long no);
    public List<OTT_Drama> findByProvcate_id(Long no);
    public boolean existsByDramacontent_idAndProvcate_id(Long no, Long prov);
}
