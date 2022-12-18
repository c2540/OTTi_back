package com.example.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.Member;
import com.example.dto.Review_Drama;
import com.example.dto.Review_Drama_Coment;

@Repository
public interface Review_Drama_ReplyRepository extends JpaRepository<Review_Drama_Coment,Long>{
    public List<Review_Drama_Coment> findByReviewdrama_id(Long no);
    public List<Review_Drama_Coment> findByReviewdrama_idOrderByRegdateDesc(Long no, Pageable pageable);
    public long countByReviewdrama_id(Long reviewid);
    @Transactional
    int deleteByIdAndUserid(Long no,Member member);
    @Transactional
    int deleteAllByReviewdrama(Review_Drama reviewdrama);
}
