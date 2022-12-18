package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.CenterAnswer;

@Repository
public interface CenterAnswerRepository extends JpaRepository<CenterAnswer,Long> {
    public List<CenterAnswer> findByService_id(Long no);
}
