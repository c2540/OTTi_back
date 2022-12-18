package com.example.repository;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.ImageProjection;
import com.example.dto.Member;
import com.example.dto.MemberImage;



@Repository
public interface MemberImageRepository extends JpaRepository<MemberImage,Long>{
    public List<ImageProjection> findByUserid_userid(String userid);
    @Transactional
    int deleteByUserid(Member member);
}
