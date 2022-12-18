package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member,String> {
    public Member findByUserid(String userid);
    public Member findByPhoneAndSocial(String phone, String social);
    public Member findByPhoneAndBirthAndSocial(String phone,String Birth, String social);
    public Member findByNickname(String nickname);
    public Page<Member> findAll(Pageable pageable);
}

