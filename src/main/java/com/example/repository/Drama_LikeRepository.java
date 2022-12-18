package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.DramaContent;
import com.example.dto.Drama_Like;
import com.example.dto.Member;

@Repository
public interface Drama_LikeRepository extends JpaRepository<Drama_Like,Long> {
    List<Drama_Like> findByDramacode(DramaContent dramacode);
    List<Drama_Like> findByDramacodeAndUserid(DramaContent dramaOcode, Member userid);
    List<Drama_Like> findByUseridOrderByIdDesc(Member userid, Pageable pageable);
    Long countByUserid(Member member);
    List<Drama_Like> findByUserid(Member userid);
}
