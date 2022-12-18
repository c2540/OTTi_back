package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.Review_Movie;

@Repository
public interface Review_MovieRepository extends JpaRepository<Review_Movie,Long>{
    public List<Review_Movie> findByMember_useridOrderByIdDesc(String userid,Pageable pageable);
    public List<Review_Movie> findByMember_useridOrderByScoreDescIdDesc(String userid,Pageable pageable);
    public List<Review_Movie> findByMember_userid(String userid);
    public List<Review_Movie> findBymovieContent_id(Long dramano);
    public List<Review_Movie> findAllByOrderByIdDesc(Pageable pageable);
    long countByMember_userid(String userid);
    long countByMovieContent_id(Long no);
}
