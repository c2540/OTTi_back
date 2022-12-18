package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.Genre_Drama;

@Repository
public interface Genre_DramaRepository extends JpaRepository<Genre_Drama,Long> {
    public List<Genre_Drama> findByDramacontent_id(Long no);
    public boolean existsByDramacontent_idAndGenrecate_id(Long no, Long genre);
}
