package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.dto.ImageProjection;
import com.example.dto.Member;
import com.example.dto.MemberImage;
import com.example.dto.Member_MemberImage_View;
import com.example.repository.MemberImageRepository;
import com.example.repository.MemberRepository;
import com.example.repository.Member_MemberImage_ViewRepository;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired MemberRepository mRepository;
    @Autowired MemberImageRepository memberImageRepository;
    @Autowired Member_MemberImage_ViewRepository miRepository;

    @Override
    public int joinuser(Member member) {
        mRepository.save(member);
        return 1;
    }

    @Override
    public Page<Member> userlist(Pageable pageable) {
        return mRepository.findAll(pageable);
    }

    @Override
    public int check(String userid) {
        try{
            Member member = mRepository.findByUserid(userid);
            if(member.getUserid() == null){
                return 0;
            }
            return 1;
        }
        catch(Exception e){
            return -1;
        }
    }

    @Override
    public boolean existsById(String id) {
        try{
            return mRepository.existsById(id);
        }
        catch(Exception e){
            return false;
        }
    }

    @Override
    public int existsBynick(String nickname) {
        try{
            Member member = mRepository.findByNickname(nickname);
            if(member.getNickname() == null){
                return 0;
            }
            return 1;
            
        }
        catch(Exception e){
            return 0;
        }
    }

    @Override
    public int existsByphone(String phone,String social) {
        try{
            Member member = mRepository.findByPhoneAndSocial(phone,social);
            if(member.getPhone() == null){
                return 0;
            }
            return 1;
            
        }
        catch(Exception e){
            return -1;
        }
    }

    @Override
    public Member selectOne(String id) {
        try {
          Member member = mRepository.findById(id).orElse(null);
          return member;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Member find(String userid) {
        return mRepository.findByUserid(userid);
        
    }

    @Override
    public int deleteOne(Member member) {
        try {
            mRepository.delete(member);
            
            return 1;
        } catch (Exception e) {
            return 0;
        }
        
    }

    @Override
    public int updatepw(Member member) {
        try {
            mRepository.save(member);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int insertimage(MemberImage memberImage) {
        try {
            
              memberImageRepository.save(memberImage);
            
            return 1;

        
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public MemberImage findimage(Long no) {
        return memberImageRepository.findById(no).orElse(null);
    }

    @Override
    public List<ImageProjection> findByUserid_userid(String userid) {
        return memberImageRepository.findByUserid_userid(userid);

    }

    @Override
    public int deleteimageOne(Long no) {
        try {
            memberImageRepository.deleteById(no);

            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
    @Override
    public Member findid(String phone, String birth, String social) {
        try {
            Member ret = mRepository.findByPhoneAndBirthAndSocial(phone,birth,social);
            return ret;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Member_MemberImage_View profileFindByUserid(String userid) {
        try {
            Member_MemberImage_View ret = miRepository.findById(userid).orElse(null);
            return ret;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int deleteImage(String userid) {
        try {
            Member member = mRepository.findByUserid(userid);
            int ret = memberImageRepository.deleteByUserid(member);
            return ret;
        } catch (Exception e) {
            return -1;
        }
    }

   
   
}
