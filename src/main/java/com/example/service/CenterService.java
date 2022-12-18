package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.CenterAnswer;
import com.example.dto.Center;

@Service
public interface CenterService {

    public int insertQNA(Center board);

    public int insertAnswer(CenterAnswer answer);

    public List<Center> QNAList(String userid, int no);

    public List<Center> QNAListALL(int no);

    public Long CountQNAList(String userid);

    public Long CountQNAListALL();

    public Center SelectQNA(Long no);

    public List<CenterAnswer> ListAnswer(Long no);

    public int deleteAnswer(Long no);
    
}
