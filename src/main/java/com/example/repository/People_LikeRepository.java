package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.Member;
import com.example.dto.MovieContent;
import com.example.dto.People_Like;

@Repository
public interface People_LikeRepository extends JpaRepository<People_Like,Long>{
    List<People_Like> findByPeoplecode(MovieContent moviecode);
    List<People_Like> findByPeoplecodeAndUserid(Long code, Member userid);
    List<People_Like> findByUseridOrderByIdDesc(Member userid, Pageable pageable);
    Long countByUserid(Member member);
}
