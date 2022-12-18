package com.example.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ContentDrama_View;
import com.example.dto.ContentMovie_View;
import com.example.dto.DramaContent;
import com.example.dto.Genre_Cate;
import com.example.dto.Genre_Drama;
import com.example.dto.Genre_Movie;
import com.example.dto.MovieContent;
import com.example.dto.OTT_Cate;
import com.example.dto.OTT_Drama;
import com.example.dto.OTT_Movie;
import com.example.repository.ContentDRepository;
import com.example.repository.ContentDrama_Genre_ViewRepository;
import com.example.repository.ContentDrama_Ott_ViewRepository;
import com.example.repository.ContentDrama_ViewRepository;
import com.example.repository.ContentMRepository;
import com.example.repository.ContentMovie_Genre_ViewRepository;
import com.example.repository.ContentMovie_Ott_ViewRepository;
import com.example.repository.ContentMovie_ViewRepository;
import com.example.repository.Genre_CateRepository;
import com.example.repository.Genre_DramaRepository;
import com.example.repository.Genre_MovieRepository;
import com.example.repository.OttDramaRepository;
import com.example.repository.OttMovieRepository;
import com.example.repository.Ott_CateRepository;

@RestController
@RequestMapping(value = "/DB")
public class DBController {
    @Autowired ContentDRepository cdRepository;
    @Autowired ContentMRepository cmRepository;
    @Autowired OttDramaRepository ottDramaRepository;
    @Autowired OttMovieRepository ottMovieRepository;
    @Autowired Genre_DramaRepository genre_DramaRepository;
    @Autowired Genre_MovieRepository genre_MovieRepository;
    @Autowired ContentDrama_Ott_ViewRepository doviewRepository;
    @Autowired ContentDrama_Genre_ViewRepository dgviewRepository;
    @Autowired ContentDrama_ViewRepository dviewRepository;
    @Autowired ContentMovie_Ott_ViewRepository moviewRepository;
    @Autowired ContentMovie_Genre_ViewRepository mgviewRepository;
    @Autowired ContentMovie_ViewRepository mviewRepository;
    @Autowired Ott_CateRepository oCateRepository;
    @Autowired Genre_CateRepository gCateRepository;

    // 추천페이지 / 드라마 조회수 top10
    @GetMapping(value = "/dramahit10.json")
    Map<String,Object> dramahit10(
    ){
        Map<String,Object> map = new HashMap<>();
        
        List<DramaContent> list = cdRepository.findTop10ByOrderByHitDescPopularityDesc();
        
        map.put("results",list);
        
        return map;
    }

    // 추천페이지 / 영화 조회수 top10
    @GetMapping(value = "/moviehit10.json")
    Map<String,Object> moviehit10(
    ){
        Map<String,Object> map = new HashMap<>();
        
        List<MovieContent> list = cmRepository.findTop10ByOrderByHitDescPopularityDesc();
        
        map.put("results",list);
        
        return map;
    }


    // 검색페이지 / 드라마검색
    @GetMapping(value = "/serchd.json")
    Map<String,Object> serchD(
        @RequestParam(name = "text", defaultValue = "")String name
    ){
        Map<String,Object> map = new HashMap<>();
        List<DramaContent> list = cdRepository.findByNameContaining(name);
        
        map.put("results",list);
        
        return map;
    }

    // 검색페이지 / 영화검색
    @GetMapping(value = "/serchm.json")
    Map<String,Object> serchM(
        @RequestParam(name = "text",defaultValue = "")String name
    ){
        Map<String,Object> map = new HashMap<>();
        List<MovieContent> list = cmRepository.findByTitleContaining(name);
        map.put("results",list);
        
        return map;
    }

    // OTT 조회
    @GetMapping(value = "/provider.json")
    Map<String,Object> prov(
        @RequestParam(name = "prov")Long prov
    ){
        Map<String,Object> map = new HashMap<>();
        List<OTT_Drama> list = ottDramaRepository.findByProvcate_id(prov);
        map.put("results", list);

        return map;
    }

    // 작품 1개 상세조회
    @GetMapping(value = "/selectone.json")
    Map<String,Object> selectOne(
        @RequestParam(name = "type")String type,
        @RequestParam(name = "no")Long no
    ){
        Map<String,Object> map = new HashMap<>();
        
        if(type.equals("tv")){
            DramaContent content = cdRepository.findById(no).orElse(null);
            List<OTT_Drama> ottno = ottDramaRepository.findByDramacontent_id(no);
            List<Genre_Drama> genre = genre_DramaRepository.findByDramacontent_id(no);

            List<Genre_Cate> list1 = new ArrayList<>();
            for(Genre_Drama genrecode : genre){
                list1.add(genrecode.getGenrecate());
            }

            List<OTT_Cate> list2 = new ArrayList<>();
            for(OTT_Drama drama : ottno){
                list2.add(drama.getProvcate());
            }
            if(content != null){
                content.setHit(content.getHit()+1);
                cdRepository.save(content);
                map.put("status", 200);
                map.put("genre", list1);
                map.put("provider", list2);
                map.put("result", content);
            }
            else{
                map.put("status", 0);
            }
        }
        else{
            MovieContent content = cmRepository.findById(no).orElse(null);
            List<OTT_Movie> ottno = ottMovieRepository.findByMoviecontent_id(no);
            List<Genre_Movie> genre = genre_MovieRepository.findByMoviecontent_id(no);

            List<Genre_Cate> list1 = new ArrayList<>();
            for(Genre_Movie genrecode : genre){
                list1.add(genrecode.getGenrecate());
            }

            List<OTT_Cate> list2 = new ArrayList<>();
            for(OTT_Movie movie : ottno){
                list2.add(movie.getProvcate());
            }
            if(content != null){
                content.setHit(content.getHit()+1);
                cmRepository.save(content);
                map.put("status", 200);
                map.put("genre", list1);
                map.put("provider", list2);
                map.put("result", content);
            }
            else{
                map.put("status", 0);
            }
        }
        return map;
    }

    // 카테고리 조회
    @GetMapping(value = "/category.json")
    public Map<String,Object> category(){
        Map<String,Object> map = new HashMap<>();
        List<OTT_Cate> list1 = oCateRepository.findAll();
        List<Genre_Cate> list2 = gCateRepository.findAll();
        map.put("ott", list1);
        map.put("genre", list2);
        
        return map;
    }
    
    // 메인페이지 / 드라마 신작
    @GetMapping(value = "/newtvcontent.json")
    Map<String,Object> newtvcontent(){
        Map<String,Object> map = new HashMap<>();
        Sort sort = Sort.by(Sort.Direction.DESC, "firstairdate");
        PageRequest pageRequest = PageRequest.of(0, 20, sort);
        List<ContentDrama_View> list = dviewRepository.findBy(pageRequest);
        if(list.size() != 0){
            for(ContentDrama_View obj : list){
                Long avg = Math.round(obj.getVoteaverage()* 10);
                Double avg1 = avg/10.0;                  
                obj.setVoteaverage(avg1); 
            }
        }
        map.put("results", list);

        return map;
    }

    // 메인페이지 // 영화 신작
    @GetMapping(value = "/newmoviecontent.json")
    Map<String,Object> newmoviecontent(){
        Map<String,Object> map = new HashMap<>();
        Sort sort = Sort.by(Sort.Direction.DESC, "releasedate");
        PageRequest pageRequest = PageRequest.of(0, 20, sort);
        List<ContentMovie_View> list = mviewRepository.findBy(pageRequest);
        if(list.size() != 0){
            for(ContentMovie_View obj : list){
                Long avg = Math.round(obj.getVoteaverage()* 10);
                Double avg1 = avg/10.0;                  
                obj.setVoteaverage(avg1); 
            }
        }
        map.put("results", list);

        return map;
    }

    // 메인페이지 / 개발자 추천작
    @PostMapping(value = "/editors.json")
    Map<String,Object> editors(
        @RequestBody Map<String,List<Long>> list
    ){
        Map<String,Object> map = new HashMap<>();
        List<Long> ids = list.get("id");
        List<DramaContent> lists = new ArrayList<>();
        
        for(int i=0; i<list.get("id").size();i++){
            DramaContent content = cdRepository.findById(ids.get(i)).orElse(null);
            lists.add(content);
        }
        if(lists.size() != 0){
            for(DramaContent obj : lists){
                Long avg = Math.round(obj.getVote_average()* 10);
                Double avg1 = avg/10.0;                  
                obj.setVote_average(avg1); 
            }
        }

        map.put("results", lists);
        return map;
    }

    // 메인페이지 / 고전명작
    @GetMapping(value="/oldcontent.json")
    Map<String, Object> oldcontent(){
        Map<String,Object> map = new HashMap<>();
        //Long voteavg = 8L;
        Double voteavg = 8D;
        Sort sort = Sort.by(Sort.Direction.ASC, "releasedate");
        PageRequest pageRequest = PageRequest.of(0, 20, sort);
        List<ContentMovie_View> list = mviewRepository.findByVoteaverageGreaterThan(pageRequest, voteavg);
        if(list.size() != 0){
            for(ContentMovie_View obj : list){
                Long avg = Math.round(obj.getVoteaverage()* 10);
                Double avg1 = avg/10.0;                  
                obj.setVoteaverage(avg1); 
            }
        }
        map.put("results", list);

        return map;
    }

}

