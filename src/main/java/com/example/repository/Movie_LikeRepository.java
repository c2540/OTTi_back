package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.Member;
import com.example.dto.MovieContent;
import com.example.dto.Movie_Like;

@Repository
public interface Movie_LikeRepository extends JpaRepository<Movie_Like,Long> {
    List<Movie_Like> findByMoviecode(MovieContent moviecode);
    List<Movie_Like> findByMoviecodeAndUserid(MovieContent moviecode, Member userid);
    List<Movie_Like> findByUseridOrderByIdDesc(Member userid, Pageable pageable);
    Long countByUserid(Member member);
    List<Movie_Like> findByUserid(Member userid);
}
