package com.example.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.dto.Member;
import com.example.repository.MemberRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
    @Autowired MemberRepository mRepository;

    // 토큰 생성용 보안키
    private final String SECRETKEY = "gyuf6r567546sd546e56";

    // 정보 추출용 메소드
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    // 토큰 생성(아이디 정보를 이용한 토큰 생성)
    // 아이디만포함. json -> string -> 토큰 -> string -> json
    // {"uid":"aaa", "role":"CUSTOMER"}
    public String generateToken(String username) {
        JSONObject jobj = new JSONObject();
        jobj.put("username", username);
        Member member = mRepository.findById(username).orElse(null);
        jobj.put("role", member.getRole());
        

        // ex) 30분 => 1000 * 60 * 30
        // long tokenValidTime = 1000 * 60 * 60 * 4; // 4시간

        Map<String, Object> claims = new HashMap<>();
        String token = Jwts.builder().setClaims(claims)
                .setSubject(jobj.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // .setExpiration(new Date(System.currentTimeMillis() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, SECRETKEY).compact();

        return token;
    }

    // 토큰 검증
    public Boolean validateToken(String token, String userid) {
        // 토큰에서 아이디 정보 추출
        final String username = this.extractUsername(token);
        // if (username.equals(userid) && !isTokenExpired(token)) {
        if (username.equals(userid)) {
            return true;
        }
        return false;
    }

    // 토큰에서 아이디 정보 추출하기
    public String extractUsername(String token) {
        String sObj = extractClaim(token, Claims::getSubject);
        JSONObject jobj = new JSONObject(sObj);
        return jobj.getString("username");
    }

    // 토큰에서 권한 정보 정보 추출하기
    public String extractRole(String token) {
        String sObj = extractClaim(token, Claims::getSubject);
        JSONObject jobj = new JSONObject(sObj);
        return jobj.getString("role");
    }

    // // 토큰에서 만료 시간 추출하기
    // public Date extractExpiration(String token) {
    //     return extractClaim(token, Claims::getExpiration);
    // }

    // // 토큰의 만료시간이 유효한지 확인
    // public Boolean isTokenExpired(String token) {
    //     // 만료시간 가져와서 현재시간보다 이전인지 확인
    //     return this.extractExpiration(token).before(new Date());
    // }

}