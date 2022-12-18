package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.ContentMovie_Genre_View;

@Mapper
public interface MovieContentGenreViewMapper {
    public List<ContentMovie_Genre_View> GenreList(List<Long> no);
}
