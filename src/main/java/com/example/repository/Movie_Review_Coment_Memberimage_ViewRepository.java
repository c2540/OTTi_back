package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.Movie_Review_Coment_Memberimage_View;

@Repository
public interface Movie_Review_Coment_Memberimage_ViewRepository extends JpaRepository<Movie_Review_Coment_Memberimage_View,Long> {
    public List<Movie_Review_Coment_Memberimage_View> findByReviewmovie_idOrderByRegdateDesc(Long no, Pageable pageable);
}
