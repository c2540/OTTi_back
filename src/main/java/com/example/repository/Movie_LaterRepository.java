package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.Member;
import com.example.dto.MovieContent;
import com.example.dto.Movie_Later;

@Repository
public interface Movie_LaterRepository extends JpaRepository<Movie_Later,Long> {
    List<Movie_Later> findByMoviecode(MovieContent moviecode);
    List<Movie_Later> findByMoviecodeAndUserid(MovieContent moviecode, Member userid);
    List<Movie_Later> findByUseridOrderByIdDesc(Member userid, Pageable pageable);
    long countByUserid(Member member);
}

