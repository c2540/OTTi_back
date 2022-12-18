package com.example.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.dto.ImageProjection;
import com.example.dto.Member;
import com.example.dto.MemberImage;
import com.example.dto.Member_MemberImage_View;

@Service
public interface MemberService {
    public int insertimage(MemberImage memberImage);

    public int updatepw(Member member);

    public int deleteOne(Member member);

    public Member selectOne(String id);
    
    public int existsByphone(String phone,String social);

    public int existsBynick(String nickname);

    public boolean existsById(String id);
    
    public int joinuser(Member member);
    
    public Page<Member> userlist(Pageable pageable);

    public int check(String userid);

    public Member find(String userid);

    public MemberImage findimage(Long no);

    public List<ImageProjection> findByUserid_userid(String userid);

    public int deleteimageOne(Long no);

    public Member findid(String phone, String birth, String social);

    public Member_MemberImage_View profileFindByUserid(String userid);
 
    public int deleteImage(String userid);
}
