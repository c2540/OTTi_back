package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.DramaContent;
import com.example.dto.Drama_Later;
import com.example.dto.Member;

@Repository
public interface Drama_LaterRepository extends JpaRepository<Drama_Later,Long> {
    List<Drama_Later> findByDramacode(DramaContent dramacode);
    List<Drama_Later> findByDramacodeAndUserid(DramaContent dramacode, Member userid);
    List<Drama_Later> findByUseridOrderByIdDesc(Member member, Pageable pageable);
    long countByUserid(Member member);
}