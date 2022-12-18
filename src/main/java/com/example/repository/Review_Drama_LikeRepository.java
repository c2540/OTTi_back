package com.example.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.Member;
import com.example.dto.Review_Drama;
import com.example.dto.Review_Drama_Like;

@Repository
public interface Review_Drama_LikeRepository extends JpaRepository<Review_Drama_Like,Long>{
    public List<Review_Drama_Like> findByReviewdrama_id(Long no);
    List<Review_Drama_Like> findByReviewdramaAndUserid(Review_Drama reviewdrama, Member userid);
    @Transactional
    void deleteAllByReviewdrama(Review_Drama reviewdrama);
    //public List<Review_Drama_Like> findByReviewdrama_id(String userid);
}
