package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.Movie_Review_View;

@Repository
public interface Movie_Review_ViewRepository extends JpaRepository <Movie_Review_View,Long>{
    public List<Movie_Review_View> findBymovieContent_idOrderByRegdateDesc(Long movieno);
    public List<Movie_Review_View> findBymovieContent_idOrderByRegdateDesc(Long movieno,Pageable pageable);
}

