package com.example.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
        )
            throws ServletException, IOException {
        try{

        String token = request.getHeader("TOKEN");

        if(token == null){
            throw new Exception();
        }

        if(token.length() <= 0){
            throw new Exception();
        }
        
        String username = jwtUtil.extractUsername(token);
        if(jwtUtil.validateToken(token, username) == false){
            throw new Exception();
        }
        
        // 권한이 맞지않으면
        String role = jwtUtil.extractRole(token);

        // 아래가 수행되어야 restcontroller가 동작됨.
        request.setAttribute("username", username);
        request.setAttribute("role", role);
        filterChain.doFilter(request, response);
        
        }
        catch(Exception e){
            e.printStackTrace();
            response.sendError(-1, "token error");
        }
    }
    
}
