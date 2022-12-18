package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.Member;
import com.example.dto.People_Like;
import com.example.service.MemberService;
import com.example.service.PeopleService;

@RestController
@RequestMapping(value = "/people")
public class PeopleController {
    @Autowired PeopleService pService;
    @Autowired MemberService mService;

    // 좋아요한 인물 등록여부 조회
    @GetMapping(value = "/likeview.json")
    public Map<String,Object> LikeGET(
        @RequestParam(name="code") Long code,
        HttpServletRequest request
    ){
        Map<String, Object> map = new HashMap<>();
        try{
            String username = (String) request.getAttribute("username");
            Member member = mService.selectOne(username);
            
            List<People_Like> list = pService.findLike(code, member);
            if(list.size()!=0){
                for(int i=0; i<list.size();i++){
                    if(list.get(i).getUserid() == member){
                        map.put("status", 200);
                        map.put("type", 1);
                        break;
                    }
                    if(i == list.size()-1){
                        map.put("status", 200);
                        map.put("type", 0);
                    }
                }
            }else{
                map.put("status", 200);
                map.put("type", 0);
            }
        }
        catch(Exception e){
            map.put("status", -1);
            map.put("result", e.getMessage());
        }
        return map;
    }

    // 좋아요한 인물 등록
    @PostMapping(value = "/likeinsert.json")
    public Map<String,Object> dramaLikeInsertPOST(
        @RequestParam(name="code") Long code,
        @RequestParam(name="type") Long type,
        HttpServletRequest request
    ){
        Map<String, Object> map = new HashMap<>();
        try{
            People_Like pLike = new People_Like();
            String username = (String) request.getAttribute("username");
            Member member = mService.selectOne(username);
            pLike.setUserid(member);
            pLike.setPeoplecode(code);
            if(type==0){
                People_Like ret = pService.likeSave(pLike);
                if(ret != null){
                    map.put("status", 200);
                    map.put("result", "등록성공");
                }
                else{
                    map.put("status", 0);
                    map.put("result", "등록실패");
                }
            }else if(type==1){
                List<People_Like> list = pService.findLike(code, member);
                if(list.size() != 0){
                    for(int i=0;i<list.size();i++){
                        pService.likeDeleteById(list.get(i).getId());
                    }
                    map.put("status", 200);
                    map.put("result", "취소성공");
                }else{
                    map.put("status", 0);
                    map.put("result", "취소실패");
                }
            }
        }
        catch(Exception e){
            map.put("status", -1);
            map.put("result", e.getMessage());
        }
        return map;
    }

    // 본인이 좋아요한 인물 목록 조회
    @GetMapping(value = "/likelist.json")
    public Map<String,Object> LikeListGET(HttpServletRequest request, @RequestParam(name="page") int page){
        Map<String, Object> map = new HashMap<>();
        try{
            int size = 5;
            Pageable pageable = PageRequest.of(page-1, size);
            String username = (String) request.getAttribute("username");
            Member member = mService.selectOne(username);
            long total = pService.likeTotal(member);
            List<People_Like> pList= pService.likeFindByUserid(member, pageable);
            if(pList != null){
                map.put("status", 200);
                map.put("result", pList);
                map.put("pages", (total-1)/5+1);
            }
            else{
                map.put("status", 0);
            }
        }
        catch(Exception e){
            map.put("status", -1);
            map.put("result", e.getMessage());
        }
        return map;
    }

    
}
