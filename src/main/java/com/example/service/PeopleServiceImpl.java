package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.dto.Member;
import com.example.dto.People_Like;
import com.example.repository.People_LikeRepository;

@Service
public class PeopleServiceImpl implements PeopleService {
    @Autowired People_LikeRepository people_LikeRepository;

    @Override
    public List<People_Like> findLike(Long code, Member member) {
        try{
            List<People_Like> retList = people_LikeRepository.findByPeoplecodeAndUserid(code, member);

            return retList;
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public void likeDeleteById(Long id) {
        people_LikeRepository.deleteById(id);
    }

    @Override
    public List<People_Like> likeFindByUserid(Member member, Pageable pageable) {
        List<People_Like> ret = people_LikeRepository.findByUseridOrderByIdDesc(member, pageable);
        return ret;
    }

    @Override
    public People_Like likeSave(People_Like pLike) {
        People_Like ret = people_LikeRepository.save(pLike);
        return ret;
    }

    @Override
    public long likeTotal(Member member) {
        try{
            long total = people_LikeRepository.countByUserid(member);
            return total;            
        }
        catch(Exception e){
            return -1;
        }
    }

}
