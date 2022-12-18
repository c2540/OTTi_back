package com.example.dto;

import java.util.List;

import lombok.Data;

@Data
public class ContentDList {
    Long page;
    List<DramaContent> results;
    Long total_pages;
    Long total_results;
}
