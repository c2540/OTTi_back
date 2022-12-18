package com.example.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.dto.Member;
import com.example.dto.People_Like;

@Service
public interface PeopleService {
    public List<People_Like> findLike(Long code, Member member);
    public void likeDeleteById(Long id);
    public People_Like likeSave(People_Like pLike);
    public List<People_Like> likeFindByUserid(Member member, Pageable pageable);
    public long likeTotal(Member member);
}
