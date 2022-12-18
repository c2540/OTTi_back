package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.Drama_Review_View;

@Repository
public interface Drama_Review_ViewRepository extends JpaRepository<Drama_Review_View,Long> {
    public List<Drama_Review_View> findBydramaContent_idOrderByRegdateDesc(Long dramano,Pageable pageable);
    public List<Drama_Review_View> findBydramaContent_idOrderByRegdateDesc(Long dramano);
}
