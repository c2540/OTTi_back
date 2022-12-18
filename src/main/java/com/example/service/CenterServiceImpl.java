package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.dto.Center;
import com.example.dto.CenterAnswer;
import com.example.repository.CenterAnswerRepository;
import com.example.repository.CenterRepository;

@Service
public class CenterServiceImpl implements CenterService {
    @Autowired CenterRepository cRepository;
    @Autowired CenterAnswerRepository caRepository;

    @Override
    public int insertQNA(Center board) {
        try{
            cRepository.save(board);

            return 1;
        }
        catch(Exception e){
            return -1;
        }
    }

    @Override
    public int insertAnswer(CenterAnswer answer) {
        try{
            caRepository.save(answer);
            return 1;
        }
        catch(Exception e){
            return -1;
        }      
    }

    @Override
    public List<Center> QNAList(String userid, int no) {
        PageRequest pageRequest = PageRequest.of(no, 15);
        return cRepository.findByMember_useridOrderByRegdateDesc(userid, pageRequest);
    }

    @Override
    public Center SelectQNA(Long no) {
        return cRepository.findById(no).orElse(null);
    }

    @Override
    public List<Center> QNAListALL(int no) {
        PageRequest pageRequest = PageRequest.of(no,15);
        return cRepository.findByOrderByRegdateDesc(pageRequest);
    }

    @Override
    public List<CenterAnswer> ListAnswer(Long no) {
        return caRepository.findByService_id(no);
    }

    @Override
    public int deleteAnswer(Long no) {
            caRepository.deleteById(no);
        return 1;
    }

    @Override
    public Long CountQNAList(String userid) {
        return cRepository.countByMember_userid(userid) ;
    }

    @Override
    public Long CountQNAListALL() {
        return cRepository.count();
    }

    
}
