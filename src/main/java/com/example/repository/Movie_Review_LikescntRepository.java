package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.Movie_Review_Likescnt_View;

@Repository
public interface Movie_Review_LikescntRepository extends JpaRepository <Movie_Review_Likescnt_View,Long>{
    public List<Movie_Review_Likescnt_View> findByMoviecontent_id(Long movieno,Pageable pageable);
}

