package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.Center;
import com.example.dto.CenterAnswer;
import com.example.dto.Member;
import com.example.jwt.JwtUtil;
import com.example.service.CenterService;


@RestController
@RequestMapping(value = "/center")
public class ServiceCenter {
    @Autowired CenterService cService;
    @Autowired JwtUtil jwtUtil;

    // 1:1문의 등록
    @PostMapping(value = "/postQNA.json")
    public Map<String,Object> inserQNA(
        @RequestBody Center board,
        @RequestHeader(name = "TOKEN") String token
    ){
        Map<String,Object> map = new HashMap<>();
        String id = jwtUtil.extractUsername(token);
        Boolean ret1 = jwtUtil.validateToken(token, id);
        
        if(ret1 == true){
            Member member = new Member();
            member.setUserid(id);
            board.setMember(member);
            int ret = cService.insertQNA(board);

            if(ret == 1){
                map.put("status", 200);
            }
            else{
                map.put("status", -1);
            }
        }

        return map;
    }

    // 1:1 문의 목록
    @GetMapping(value = "/QNAList.json")
    public Map<String,Object> QNAList(
        @RequestParam int no,
        HttpServletRequest request
    ){
        Map<String,Object> map = new HashMap<>();
        
        String role = (String) request.getAttribute("role");
        String userid = (String) request.getAttribute("username");
        if(role.equals("ADMIN")){
            List<Center> list = cService.QNAListALL(no-1);
            Long total = cService.CountQNAListALL();
            map.put("result", list);
            map.put("status", 200);
            map.put("total", total);
        }
        else{
            List<Center> list = cService.QNAList(userid, no-1);
            Long total = cService.CountQNAList(userid);

            map.put("status", 200);
            map.put("result", list);
            map.put("total", total);
        }
        return map;
    }

    // 1:1 문의 1개 상세조회
    @GetMapping(value = "/SelectQNA.json")
    public Map<String,Object> QNA(
        @RequestParam Long no
    ){
        Map<String,Object> map = new HashMap<>();
        Center qna = cService.SelectQNA(no);
        List<CenterAnswer> answer = cService.ListAnswer(no);
        map.put("status", 200);
        map.put("result", qna);
        map.put("answer", answer);

        return map;
    }
    
    // 1:1 문의 답변등록
    @PostMapping(value = "/insertanswer.json")
    public Map<String,Object> inseranswer(
        @RequestBody CenterAnswer answer,
        @RequestHeader(name = "TOKEN") String token,
        @RequestParam(name = "no") Long no
    ){
        Map<String,Object> map = new HashMap<>();
        Member member = new Member();
        Center center = new Center();
        String userid = jwtUtil.extractUsername(token);
        member.setUserid(userid);
        answer.setMember(member);
        center.setId(no);
        answer.setService(center);

        int ret = cService.insertAnswer(answer);
        if(ret == 1){
            map.put("status", 200);
            Center center1 = cService.SelectQNA(no);
            center1.setAnswerYN(1L);
            cService.insertQNA(center1);
        }
        else{
            map.put("status", -1);
        }
        return map;
    }

    // 1:1 문의 답변 삭제
    @DeleteMapping(value = "/deleteAnswer.json")
    public Map<String,Object> deleteanswer(
        @RequestBody CenterAnswer answer
    ){
        Map<String,Object> map = new HashMap<>();
        int ret = cService.deleteAnswer(answer.getId());
        if(ret == 1){
            map.put("status", 200);
        }
        else{
            map.put("status", -1);
        }
        return map;
    }
}
