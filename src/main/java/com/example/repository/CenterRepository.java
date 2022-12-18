package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.Center;

@Repository
public interface CenterRepository extends JpaRepository<Center,Long> {
    public List<Center> findByMember_useridOrderByRegdateDesc(String userid, Pageable pageable);
    public List<Center> findByOrderByRegdateDesc(Pageable pageable);
    public Long countByMember_userid(String userid);
}
