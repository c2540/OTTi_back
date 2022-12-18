package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.ContentUpdatePage;

@Repository
public interface ContentUpdateRepository extends JpaRepository<ContentUpdatePage, Long> {
    Page<ContentUpdatePage> findAllByOrderByRegdateDesc(Pageable pageable);
}
