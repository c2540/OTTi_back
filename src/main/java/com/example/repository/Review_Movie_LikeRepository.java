package com.example.repository;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.Member;
import com.example.dto.Review_Movie;
import com.example.dto.Review_Movie_Like;

@Repository
public interface Review_Movie_LikeRepository extends JpaRepository<Review_Movie_Like,Long>{
    public List<Review_Movie_Like> findByReviewmovie_id(Long no);
    List<Review_Movie_Like> findByReviewmovieAndUserid(Review_Movie reviewmovie, Member userid);
    @Transactional
    void deleteAllByReviewmovie(Review_Movie reviewmovie);
}
