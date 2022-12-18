package com.example.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.dto.CustomUser;
import com.example.dto.Member;
import com.example.repository.MemberRepository;

@Service
public class CustomLoginService implements UserDetailsService {

    @Autowired MemberRepository mRepository;

    // 로그인 화면에서 전달되어 호출되는 오버라이드된 메소드
    // 로그인에서 전송되는 아이디로 db에서 정보 꺼냄
    // userdetails의 객체를 만든다음 반환하면 시큐리티 비교후 로그인
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);

        Member member = mRepository.findById(username).orElse(null);
        
        if(member != null){
            String[] str = {member.getRole()};
            Collection<GrantedAuthority> role = AuthorityUtils.createAuthorityList(str);
            User user = new CustomUser( member.getUserid(), member.getUserpw(),role, member.getPhone());

            return user;
        }
        else{
            String[] str = {"-"};
            Collection<GrantedAuthority> role = AuthorityUtils.createAuthorityList(str);
            User user = new User( "-","-",role);

            return user;
        }
    }
    
}
