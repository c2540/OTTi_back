package com.example.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.Member;
import com.example.dto.Review_Movie;
import com.example.dto.Review_Movie_Coment;

@Repository
public interface Review_Movie_ReplyRepository extends JpaRepository<Review_Movie_Coment,Long>{
    public List<Review_Movie_Coment> findByReviewmovie_id(Long no);
    public List<Review_Movie_Coment> findByReviewmovie_idOrderByRegdateDesc(Long no, Pageable pageable);
    public long countByReviewmovie_id(Long reviewid);
    @Transactional
    int deleteByIdAndUserid(Long no,Member member);
    @Transactional
    int deleteAllByReviewmovie(Review_Movie reviewmovie);
}
