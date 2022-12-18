package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.Review_Drama;

@Repository
public interface Review_DramaRepository extends JpaRepository<Review_Drama,Long> {
    public List<Review_Drama> findByMember_useridOrderByIdDesc(String userid,Pageable pageable);
    public List<Review_Drama> findByMember_useridOrderByScoreDescIdDesc(String userid,Pageable pageable);
    public List<Review_Drama> findByMember_userid(String userid);
    public List<Review_Drama> findBydramaContent_id(Long dramano);
    public List<Review_Drama> findAllByOrderByIdDesc (Pageable pageable);
    long countByMember_userid(String userid);
    long countByDramaContent_id(Long no);
}
