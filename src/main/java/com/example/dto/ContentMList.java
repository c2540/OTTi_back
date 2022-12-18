package com.example.dto;

import java.util.List;

import lombok.Data;

@Data
public class ContentMList {
    Long page;
    List<MovieContent> results;
    Long total_pages;
    Long total_results;
}
