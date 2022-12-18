package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.Member_MemberImage_View;

@Repository
public interface Member_MemberImage_ViewRepository extends JpaRepository<Member_MemberImage_View,String> {
    
}
