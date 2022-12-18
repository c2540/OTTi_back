package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.Member;

@Mapper
public interface DramaContentMapper {
    public List<Member> MemberList();
}
