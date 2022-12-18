package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.Drama_Review_Coment_Memberimage_View;

@Repository
public interface Drama_Review_Coment_Memberimage_ViewRepository extends JpaRepository<Drama_Review_Coment_Memberimage_View,Long> {
    public List<Drama_Review_Coment_Memberimage_View> findByReviewdrama_idOrderByRegdateDesc(Long no, Pageable pageable);
}
