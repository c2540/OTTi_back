package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.ContentDrama_Genre_View;

@Mapper
public interface DramaContentGenreViewMapper {
    public List<ContentDrama_Genre_View> GenreList(List<Long> no);
}
