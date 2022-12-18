package com.example.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ContentDrama_Genre_View;
import com.example.dto.ContentDrama_Ott_Genre_View;
import com.example.dto.ContentDrama_Ott_View;
import com.example.dto.ContentDrama_View;
import com.example.dto.ContentMovie_Genre_View;
import com.example.dto.ContentMovie_Ott_Genre_View;
import com.example.dto.ContentMovie_Ott_View;
import com.example.dto.ContentMovie_View;
import com.example.dto.Drama_Like;
import com.example.dto.Member;
import com.example.dto.Movie_Like;
import com.example.mapper.DramaContentGenreViewMapper;
import com.example.mapper.MovieContentGenreViewMapper;
import com.example.repository.ContentDrama_Genre_ViewRepository;
import com.example.repository.ContentDrama_Ott_Genre_ViewRepository;
import com.example.repository.ContentDrama_Ott_ViewRepository;
import com.example.repository.ContentDrama_ViewRepository;
import com.example.repository.ContentMovie_Genre_ViewRepository;
import com.example.repository.ContentMovie_Ott_Genre_ViewRepository;
import com.example.repository.ContentMovie_Ott_ViewRepository;
import com.example.repository.ContentMovie_ViewRepository;
import com.example.repository.Drama_LikeRepository;
import com.example.repository.Genre_CateRepository;
import com.example.repository.Genre_DramaRepository;
import com.example.repository.Genre_MovieRepository;
import com.example.repository.Movie_LikeRepository;

@RestController
@RequestMapping(value = "/contentlist")

public class ContentListController {

// CREATE VIEW CONTENT_DRAMA_OTT_VIEW AS
// SELECT CD.*,OD.OTT_CODE
// FROM DRAMA_CONTENT CD
// INNER JOIN OTT_DRAMA OD
// ON CD.ID = OD.CONTENT_CODE;

// CREATE VIEW CONTENT_DRAMA_GENRE_VIEW AS
// SELECT CD.*,GD.GENRE_CODE
// FROM DRAMA_CONTENT CD
// INNER JOIN GENRE_DRAMA GD
// ON CD.ID = GD.CONTENT_CODE;

// CREATE VIEW CONTENT_MOVIE_OTT_VIEW AS
// SELECT CM.*,OM.OTT_CODE
// FROM MOVIE_CONTENT CM
// INNER JOIN OTT_MOVIE OM
// ON CM.ID = OM.CONTENT_CODE;

// CREATE VIEW CONTENT_MOVIE_GENRE_VIEW AS
// SELECT CM.*,GM.GENRE_CODE
// FROM MOVIE_CONTENT CM
// INNER JOIN GENRE_MOVIE GM
// ON CM.ID = GM.CONTENT_CODE;

// CREATE VIEW CONTENT_DRAMA_VIEW AS
// SELECT CM.*
// FROM DRAMA_CONTENT CM;

// CREATE VIEW CONTENT_MOVIE_VIEW AS
// SELECT CM.*
// FROM MOVIE_CONTENT CM;


// CREATE VIEW CONTENT_DRAMA_OTT_GENRE_VIEW AS
// SELECT A.*,GD.GENRE_CODE
// FROM
// (SELECT CD.*,OD.OTT_CODE
// FROM DRAMA_CONTENT CD
// INNER JOIN OTT_DRAMA OD
// ON CD.ID = OD.CONTENT_CODE) A
// INNER JOIN GENRE_DRAMA  GD
// ON A.ID =GD.CONTENT_CODE;


// CREATE VIEW CONTENT_MOVIE_OTT_GENRE_VIEW AS
// SELECT A.*,GM.GENRE_CODE
// FROM
// (SELECT CM.*,OM.OTT_CODE
// FROM MOVIE_CONTENT CM
// INNER JOIN OTT_MOVIE OM
// ON CM.ID = OM.CONTENT_CODE) A
// INNER JOIN GENRE_MOVIE  GM
// ON A.ID =GM.CONTENT_CODE;

    @Autowired ContentDrama_Ott_ViewRepository doviewRepository;
    @Autowired ContentDrama_Genre_ViewRepository dgviewRepository;
    @Autowired ContentDrama_ViewRepository dviewRepository;
    @Autowired ContentDrama_Ott_Genre_ViewRepository allViewDRepository;

    @Autowired Genre_CateRepository genre_CateRepository;

    @Autowired ContentMovie_Ott_ViewRepository moviewRepository;
    @Autowired ContentMovie_Genre_ViewRepository mgviewRepository;
    @Autowired ContentMovie_ViewRepository mviewRepository;
    @Autowired ContentMovie_Ott_Genre_ViewRepository allViewMRepository;

    @Autowired Movie_LikeRepository mLikeRepository;
    @Autowired Drama_LikeRepository dLikeRepository;

    @Autowired Genre_CateRepository gRepository;
    @Autowired DramaContentGenreViewMapper DGMapper;
    @Autowired MovieContentGenreViewMapper MGMapper;

    @Autowired Genre_DramaRepository gDramaRepository;
    @Autowired Genre_MovieRepository gMovieRepository;

    // 컨텐츠 페이지 / 드라마 조회
    @GetMapping(value = "/tvlist.json")
    Map<String,Object> tvlist(
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "provider") Long prov,
        @RequestParam(name = "sort") String type,
        @RequestParam(name = "genre") Long genre
    ){
        Map<String,Object> map = new HashMap<>();
        if(prov != 1 && genre == 1){
            if(type.equals("vote_average.desc")){
                Sort sort = Sort.by(Sort.Direction.DESC, "voteaverage");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentDrama_Ott_View> list2 = doviewRepository.findByOttcode(prov,pageRequest);
                map.put("result", list2);
                Long total = doviewRepository.countByOttcode(prov);
                map.put("total", total);
            }
            else if(type.equals("vote_average.asc")){
                Sort sort = Sort.by(Sort.Direction.ASC, "voteaverage");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentDrama_Ott_View> list2 = doviewRepository.findByOttcode(prov,pageRequest);
                map.put("result", list2);
                Long total = doviewRepository.countByOttcode(prov);
                map.put("total", total);
            }
            else if(type.equals("release_date.desc")){
                Sort sort = Sort.by(Sort.Direction.DESC, "firstairdate");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentDrama_Ott_View> list2 = doviewRepository.findByOttcode(prov,pageRequest);
                map.put("result", list2);
                Long total = doviewRepository.countByOttcode(prov);
                map.put("total", total);
            }
            else if(type.equals("release_date.asc")){
                Sort sort = Sort.by(Sort.Direction.ASC, "firstairdate");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentDrama_Ott_View> list2 = doviewRepository.findByOttcode(prov,pageRequest);
                map.put("result", list2);
                Long total = doviewRepository.countByOttcode(prov);
                map.put("total", total);
            }
            else{
                Sort sort = Sort.by(Sort.Direction.DESC, "id");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentDrama_Ott_View> list2 = doviewRepository.findByOttcode(prov,pageRequest);
                map.put("result", list2);
                Long total = doviewRepository.countByOttcode(prov);
                map.put("total", total);
            }
        }
        else if(prov == 1 && genre != 1){
            if(type.equals("vote_average.desc")){
                Sort sort = Sort.by(Sort.Direction.DESC, "voteaverage");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentDrama_Genre_View> list = dgviewRepository.findByGenrecode(genre,pageRequest);
                Long total = dgviewRepository.countByGenrecode(genre);
                map.put("result", list);
                map.put("total", total);
            }
            else if(type.equals("vote_average.asc")){
                Sort sort = Sort.by(Sort.Direction.ASC, "voteaverage");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentDrama_Genre_View> list = dgviewRepository.findByGenrecode(genre,pageRequest);
                Long total = dgviewRepository.countByGenrecode(genre);
                map.put("result", list);
                map.put("total", total);
            }
            else if(type.equals("release_date.desc")){
                Sort sort = Sort.by(Sort.Direction.DESC, "firstairdate");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentDrama_Genre_View> list = dgviewRepository.findByGenrecode(genre,pageRequest);
                Long total = dgviewRepository.countByGenrecode(genre);
                map.put("result", list);
                map.put("total", total);
            }
            else if(type.equals("release_date.asc")){
                Sort sort = Sort.by(Sort.Direction.ASC, "firstairdate");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentDrama_Genre_View> list = dgviewRepository.findByGenrecode(genre,pageRequest);
                Long total = dgviewRepository.countByGenrecode(genre);
                map.put("result", list);
                map.put("total", total);
            }
            else{
                Sort sort = Sort.by(Sort.Direction.DESC, "id");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentDrama_Genre_View> list = dgviewRepository.findByGenrecode(genre,pageRequest);
                Long total = dgviewRepository.countByGenrecode(genre);
                map.put("result", list);
                map.put("total", total);
            }
        }
        else if(prov != 1 && genre != 1){
            if(type.equals("vote_average.desc")){
                Sort sort = Sort.by(Sort.Direction.DESC, "voteaverage");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentDrama_Ott_Genre_View> list2 = allViewDRepository.findByOttcodeAndGenrecode(prov,genre,pageRequest);
                map.put("result", list2);
                Long total = allViewDRepository.countByOttcodeAndGenrecode(prov,genre);
                map.put("total", total);
            }
            else if(type.equals("vote_average.asc")){
                Sort sort = Sort.by(Sort.Direction.ASC, "voteaverage");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentDrama_Ott_Genre_View> list2 = allViewDRepository.findByOttcodeAndGenrecode(prov,genre,pageRequest);
                map.put("result", list2);
                Long total = allViewDRepository.countByOttcodeAndGenrecode(prov,genre);
                map.put("total", total);
            }
            else if(type.equals("release_date.desc")){
                Sort sort = Sort.by(Sort.Direction.DESC, "firstairdate");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentDrama_Ott_Genre_View> list2 = allViewDRepository.findByOttcodeAndGenrecode(prov,genre,pageRequest);
                map.put("result", list2);
                Long total = allViewDRepository.countByOttcodeAndGenrecode(prov,genre);
                map.put("total", total);
            }
            else if(type.equals("release_date.asc")){
                Sort sort = Sort.by(Sort.Direction.ASC, "firstairdate");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentDrama_Ott_Genre_View> list2 = allViewDRepository.findByOttcodeAndGenrecode(prov,genre,pageRequest);
                map.put("result", list2);
                Long total = allViewDRepository.countByOttcodeAndGenrecode(prov,genre);
                map.put("total", total);
            }
            else{
                Sort sort = Sort.by(Sort.Direction.DESC, "id");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentDrama_Ott_Genre_View> list2 = allViewDRepository.findByOttcodeAndGenrecode(prov,genre,pageRequest);
                map.put("result", list2);
                Long total = allViewDRepository.countByOttcodeAndGenrecode(prov,genre);
                map.put("total", total);
            }
        }
        return map;
    }
    
    // 컨텐츠 페이지 / 영화 조회
    @GetMapping(value = "/movielist.json")
    Map<String,Object> movieKist(
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "provider") Long prov,
        @RequestParam(name = "sort") String type,
        @RequestParam(name = "genre") Long genre
    ){
        Map<String,Object> map = new HashMap<>();
        if(prov != 1 && genre == 1){
            if(type.equals("vote_average.desc")){
                Sort sort = Sort.by(Sort.Direction.DESC, "voteaverage");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentMovie_Ott_View> list2 = moviewRepository.findByOttcode(prov,pageRequest);
                map.put("result", list2);
                Long total = moviewRepository.countByOttcode(prov);
                map.put("total", total);
            }
            else if(type.equals("vote_average.asc")){
                Sort sort = Sort.by(Sort.Direction.ASC, "voteaverage");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentMovie_Ott_View> list2 = moviewRepository.findByOttcode(prov,pageRequest);
                map.put("result", list2);
                Long total = moviewRepository.countByOttcode(prov);
                map.put("total", total);
            }
            else if(type.equals("release_date.desc")){
                Sort sort = Sort.by(Sort.Direction.DESC, "releasedate");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentMovie_Ott_View> list2 = moviewRepository.findByOttcode(prov,pageRequest);
                map.put("result", list2);
                Long total = moviewRepository.countByOttcode(prov);
                map.put("total", total);
            }
            else if(type.equals("release_date.asc")){
                Sort sort = Sort.by(Sort.Direction.ASC, "releasedate");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentMovie_Ott_View> list2 = moviewRepository.findByOttcode(prov,pageRequest);
                map.put("result", list2);
                Long total = moviewRepository.countByOttcode(prov);
                map.put("total", total);
            }
            else{
                Sort sort = Sort.by(Sort.Direction.DESC, "id");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentMovie_Ott_View> list2 = moviewRepository.findByOttcode(prov,pageRequest);
                map.put("result", list2);
                Long total = moviewRepository.countByOttcode(prov);
                map.put("total", total);
            }
        }
        else if(prov == 1 && genre != 1){
            if(type.equals("vote_average.desc")){
                Sort sort = Sort.by(Sort.Direction.DESC, "voteaverage");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentMovie_Genre_View> list = mgviewRepository.findByGenrecode(genre,pageRequest);
                Long total = mgviewRepository.countByGenrecode(genre);
                map.put("result", list);
                map.put("total", total);
            }
            else if(type.equals("vote_average.asc")){
                Sort sort = Sort.by(Sort.Direction.ASC, "voteaverage");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentMovie_Genre_View> list = mgviewRepository.findByGenrecode(genre,pageRequest);
                Long total = mgviewRepository.countByGenrecode(genre);
                map.put("result", list);
                map.put("total", total);
            }
            else if(type.equals("release_date.desc")){
                Sort sort = Sort.by(Sort.Direction.DESC, "releasedate");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentMovie_Genre_View> list = mgviewRepository.findByGenrecode(genre,pageRequest);
                Long total = mgviewRepository.countByGenrecode(genre);
                map.put("result", list);
                map.put("total", total);
            }
            else if(type.equals("release_date.asc")){
                Sort sort = Sort.by(Sort.Direction.ASC, "releasedate");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentMovie_Genre_View> list = mgviewRepository.findByGenrecode(genre,pageRequest);
                Long total = mgviewRepository.countByGenrecode(genre);
                map.put("result", list);
                map.put("total", total);
            }
            else{
                Sort sort = Sort.by(Sort.Direction.DESC, "id");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentMovie_Genre_View> list = mgviewRepository.findByGenrecode(genre,pageRequest);
                Long total = mgviewRepository.countByGenrecode(genre);
                map.put("result", list);
                map.put("total", total);
            }
        }
        else if(prov != 1 && genre != 1){
            if(type.equals("vote_average.desc")){
                Sort sort = Sort.by(Sort.Direction.DESC, "voteaverage");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentMovie_Ott_Genre_View> list2 = allViewMRepository.findByOttcodeAndGenrecode(prov,genre,pageRequest);
                map.put("result", list2);
                Long total = allViewMRepository.countByOttcodeAndGenrecode(prov,genre);
                map.put("total", total);
            }
            else if(type.equals("vote_average.asc")){
                Sort sort = Sort.by(Sort.Direction.ASC, "voteaverage");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentMovie_Ott_Genre_View> list2 = allViewMRepository.findByOttcodeAndGenrecode(prov,genre,pageRequest);
                map.put("result", list2);
                Long total = allViewMRepository.countByOttcodeAndGenrecode(prov,genre);
                map.put("total", total);
            }
            else if(type.equals("release_date.desc")){
                Sort sort = Sort.by(Sort.Direction.DESC, "releasedate");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentMovie_Ott_Genre_View> list2 = allViewMRepository.findByOttcodeAndGenrecode(prov,genre,pageRequest);
                map.put("result", list2);
                Long total = allViewMRepository.countByOttcodeAndGenrecode(prov,genre);
                map.put("total", total);
            }
            else if(type.equals("release_date.asc")){
                Sort sort = Sort.by(Sort.Direction.ASC, "releasedate");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentMovie_Ott_Genre_View> list2 = allViewMRepository.findByOttcodeAndGenrecode(prov,genre,pageRequest);
                map.put("result", list2);
                Long total = allViewMRepository.countByOttcodeAndGenrecode(prov,genre);
                map.put("total", total);
            }
            else{
                Sort sort = Sort.by(Sort.Direction.DESC, "id");
                PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
                List<ContentMovie_Ott_Genre_View> list2 = allViewMRepository.findByOttcodeAndGenrecode(prov,genre,pageRequest);
                map.put("result", list2);
                Long total = allViewMRepository.countByOttcodeAndGenrecode(prov,genre);
                map.put("total", total);
            }
        }
        return map;
    }
    
    @GetMapping(value = "/alltv")
    Map<String,Object> all(
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "sort") String type
    ){
        Map<String,Object> map = new HashMap<>();
        if(type.equals("vote_average.desc")){
            Sort sort = Sort.by(Sort.Direction.DESC, "voteaverage");
            PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
            List<ContentDrama_View> list = dviewRepository.findBy(pageRequest);
            
            if(list.size() != 0){
                for(ContentDrama_View obj : list){
                    Long avg = Math.round(obj.getVoteaverage()* 10);
                    Double avg1 = avg/10.0;                  
                    obj.setVoteaverage(avg1); 
                }
            }
            map.put("result", list);
        }
        else if(type.equals("vote_average.asc")){
            Sort sort = Sort.by(Sort.Direction.ASC, "voteaverage");
            PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
            List<ContentDrama_View> list = dviewRepository.findBy(pageRequest);
            
            if(list.size() != 0){
                for(ContentDrama_View obj : list){
                    Long avg = Math.round(obj.getVoteaverage()* 10);
                    Double avg1 = avg/10.0;                  
                    obj.setVoteaverage(avg1); 
                }
            }
             map.put("result", list);
        }
        else if(type.equals("release_date.desc")){
            Sort sort = Sort.by(Sort.Direction.DESC, "firstairdate");
            PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
            List<ContentDrama_View> list = dviewRepository.findBy(pageRequest);
            if(list.size() != 0){
                for(ContentDrama_View obj : list){
                    Long avg = Math.round(obj.getVoteaverage()* 10);
                    Double avg1 = avg/10.0;                  
                    obj.setVoteaverage(avg1); 
                }
            }
            map.put("result", list);
        }
        else if(type.equals("release_date.asc")){
            Sort sort = Sort.by(Sort.Direction.ASC, "firstairdate");
            PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
            List<ContentDrama_View> list = dviewRepository.findBy(pageRequest);
            if(list.size() != 0){
                for(ContentDrama_View obj : list){
                    Long avg = Math.round(obj.getVoteaverage()* 10);
                    Double avg1 = avg/10.0;                  
                    obj.setVoteaverage(avg1); 
                }
            }
            map.put("result", list);
        }
        else{
            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
            List<ContentDrama_View> list = dviewRepository.findBy(pageRequest);
            if(list.size() != 0){
                for(ContentDrama_View obj : list){
                    Long avg = Math.round(obj.getVoteaverage()* 10);
                    Double avg1 = avg/10.0;                  
                    obj.setVoteaverage(avg1); 
                }
            }
            map.put("result", list);
        }

        Long total = dviewRepository.countBy();
        map.put("total", total);
        return map;
    }

    @GetMapping(value = "/allmovie")
    Map<String,Object> allM(
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "sort") String type
    ){
        Map<String,Object> map = new HashMap<>();
        if(type.equals("vote_average.desc")){
            Sort sort = Sort.by(Sort.Direction.DESC, "voteaverage");
            PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
            List<ContentMovie_View> list = mviewRepository.findBy(pageRequest);
            map.put("result", list);
        }
        else if(type.equals("vote_average.asc")){
            Sort sort = Sort.by(Sort.Direction.ASC, "voteaverage");
            PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
            List<ContentMovie_View> list = mviewRepository.findBy(pageRequest);
            map.put("result", list);
        }
        else if(type.equals("release_date.desc")){
            Sort sort = Sort.by(Sort.Direction.DESC, "releasedate");
            PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
            List<ContentMovie_View> list = mviewRepository.findBy(pageRequest);
            map.put("result", list);
        }
        else if(type.equals("release_date.asc")){
            Sort sort = Sort.by(Sort.Direction.ASC, "releasedate");
            PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
            List<ContentMovie_View> list = mviewRepository.findBy(pageRequest);
            map.put("result", list);
        }
        else{
            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            PageRequest pageRequest = PageRequest.of(page-1, 10, sort);
            List<ContentMovie_View> list = mviewRepository.findBy(pageRequest);
            map.put("result", list);
        }

        Long total = mviewRepository.countBy();
        map.put("total", total);
        return map;
    }


    // 추천페이지 / 추천
    // 현재 사용X 프론트 요청으로 드라마/영화 주소 분할
    @GetMapping(value = "/recommend.json")
    Map<String,Object> recommend(
        HttpServletRequest request
    ){
        Map<String,Object> map = new HashMap<>();
        String userid = (String) request.getAttribute("username");
        List<Long> dlist = new ArrayList<>();
        List<Long> mlist = new ArrayList<>();
        Member member = new Member();
        member.setUserid(userid);
        List<Movie_Like> mlike = mLikeRepository.findByUserid(member);

        List<Drama_Like> dlike = dLikeRepository.findByUserid(member);
        if(dlike.size() > 0){
            for(Drama_Like like : dlike){
                dlist.add(like.getDramacode().getId());
            }
            Sort sortd1 = Sort.by(Sort.Direction.DESC, "firstairdate");
            Sort sortd2 = Sort.by(Sort.Direction.DESC, "voteaverage");
            PageRequest pageRequestd = PageRequest.of(0, 10, sortd1);
            PageRequest pageRequestd2 = PageRequest.of(0, 10, sortd2);
            List<ContentDrama_Genre_View> likedgenrelist = DGMapper.GenreList(dlist);
            if(likedgenrelist.size()>2){
                List<ContentDrama_Genre_View> resultd1 = dgviewRepository.findByGenrecode( likedgenrelist.get(0).getGenrecode(), pageRequestd);
                List<ContentDrama_Genre_View> resultd2 = dgviewRepository.findByGenrecode( likedgenrelist.get(1).getGenrecode(), pageRequestd2);
                List<ContentDrama_Genre_View> resultd3 = dgviewRepository.findByGenrecode( likedgenrelist.get(2).getGenrecode(), pageRequestd2);
                
                map.put("status", 200);
                map.put("dr1", resultd1);
                map.put("dr2", resultd2);
                map.put("dr3", resultd3);
                map.put("dr11", genre_CateRepository.findById(likedgenrelist.get(0).getGenrecode()).orElse(null));
                map.put("dr22", genre_CateRepository.findById(likedgenrelist.get(1).getGenrecode()).orElse(null));
                map.put("dr33", genre_CateRepository.findById(likedgenrelist.get(2).getGenrecode()).orElse(null));

                if(mlike.size() > 0){
                    for(Movie_Like like : mlike){
                        mlist.add(like.getMoviecode().getId());
                    }
                    Sort sort1 = Sort.by(Sort.Direction.DESC, "releasedate");
                    Sort sort2 = Sort.by(Sort.Direction.DESC, "voteaverage");
                    PageRequest pageRequest = PageRequest.of(0, 10, sort1);
                    PageRequest pageRequest2 = PageRequest.of(0, 10, sort2);
                    List<ContentMovie_Genre_View> likemgenrelist = MGMapper.GenreList(mlist);
        
                    if(likemgenrelist.size()>2){
                        List<ContentMovie_Genre_View> resultm1 = mgviewRepository.findByGenrecode( likemgenrelist.get(0).getGenrecode(), pageRequest);
                        List<ContentMovie_Genre_View> resultm2 = mgviewRepository.findByGenrecode( likemgenrelist.get(1).getGenrecode(), pageRequest2);
                        List<ContentMovie_Genre_View> resultm3 = mgviewRepository.findByGenrecode( likemgenrelist.get(2).getGenrecode(), pageRequest2);
                        map.put("status", 200);
                        map.put("mr1", resultm1);
                        map.put("mr2", resultm2);
                        map.put("mr3", resultm3);
                        map.put("mr11", genre_CateRepository.findById(likemgenrelist.get(0).getGenrecode()).orElse(null));
                        map.put("mr22", genre_CateRepository.findById(likemgenrelist.get(1).getGenrecode()).orElse(null));
                        map.put("mr33", genre_CateRepository.findById(likemgenrelist.get(2).getGenrecode()).orElse(null));
                    }
                    else{
                        map.put("status", 0);
                        map.put("result", "추천할 장르의 갯수가 부족합니다");
                    }
                }
                else{
                    map.put("status", 0);
                    map.put("result", "좋아요 한 컨텐츠가 없습니다.");
                }

            }
            else{
                map.put("status", 0);
                map.put("result", "추천할 장르의 갯수가 부족합니다");
            }
        }
        else{
            map.put("status", 0);
            map.put("result", "좋아요 한 컨텐츠가 없습니다.");
        }
         
        return map;
    }
    
    // 추천페이지 / 영화 추천
    @GetMapping(value = "/recommendmovie.json")
    Map<String,Object> recommendmovie(
        HttpServletRequest request
    ){
        Map<String,Object> map = new HashMap<>();
        String userid = (String) request.getAttribute("username");
        List<Long> mlist = new ArrayList<>();
        Member member = new Member();
        member.setUserid(userid);
        List<Movie_Like> mlike = mLikeRepository.findByUserid(member);


        if(mlike.size() > 0){
            for(Movie_Like like : mlike){
                mlist.add(like.getMoviecode().getId());
            }
            Sort sort1 = Sort.by(Sort.Direction.DESC, "releasedate");
            Sort sort2 = Sort.by(Sort.Direction.DESC, "voteaverage");
            PageRequest pageRequest = PageRequest.of(0, 10, sort1);
            PageRequest pageRequest2 = PageRequest.of(0, 10, sort2);
            List<ContentMovie_Genre_View> likemgenrelist = MGMapper.GenreList(mlist);

            if(likemgenrelist.size()>2){
                List<ContentMovie_Genre_View> resultm1 = mgviewRepository.findByGenrecode( likemgenrelist.get(0).getGenrecode(), pageRequest);
                List<ContentMovie_Genre_View> resultm2 = mgviewRepository.findByGenrecode( likemgenrelist.get(1).getGenrecode(), pageRequest2);
                List<ContentMovie_Genre_View> resultm3 = mgviewRepository.findByGenrecode( likemgenrelist.get(2).getGenrecode(), pageRequest2);
                map.put("status", 200);
                map.put("mr1", resultm1);
                map.put("mr2", resultm2);
                map.put("mr3", resultm3);
                map.put("mr11", genre_CateRepository.findById(likemgenrelist.get(0).getGenrecode()).orElse(null));
                map.put("mr22", genre_CateRepository.findById(likemgenrelist.get(1).getGenrecode()).orElse(null));
                map.put("mr33", genre_CateRepository.findById(likemgenrelist.get(2).getGenrecode()).orElse(null));
            }
            else{
                map.put("status", 0);
                map.put("result", "추천할 장르의 갯수가 부족합니다");
            }
        }
        else{
            map.put("status", 0);
            map.put("result", "좋아요 한 컨텐츠가 없습니다.");
        }
        return map;
    }

    // 추천페이지 / 드라마 추천
    @GetMapping(value = "/recommenddrama.json")
    Map<String,Object> recommenddrama(
        HttpServletRequest request
    ){
        Map<String,Object> map = new HashMap<>();
        String userid = (String) request.getAttribute("username");
        List<Long> dlist = new ArrayList<>();
        Member member = new Member();
        member.setUserid(userid);
        List<Drama_Like> dlike = dLikeRepository.findByUserid(member);
        if(dlike.size() > 0){
            for(Drama_Like like : dlike){
                dlist.add(like.getDramacode().getId());
            }
            Sort sortd1 = Sort.by(Sort.Direction.DESC, "firstairdate");
            Sort sortd2 = Sort.by(Sort.Direction.DESC, "voteaverage");
            PageRequest pageRequestd = PageRequest.of(0, 10, sortd1);
            PageRequest pageRequestd2 = PageRequest.of(0, 10, sortd2);
            List<ContentDrama_Genre_View> likedgenrelist = DGMapper.GenreList(dlist);
            if(likedgenrelist.size()>2){
                List<ContentDrama_Genre_View> resultd1 = dgviewRepository.findByGenrecode( likedgenrelist.get(0).getGenrecode(), pageRequestd);
                List<ContentDrama_Genre_View> resultd2 = dgviewRepository.findByGenrecode( likedgenrelist.get(1).getGenrecode(), pageRequestd2);
                List<ContentDrama_Genre_View> resultd3 = dgviewRepository.findByGenrecode( likedgenrelist.get(2).getGenrecode(), pageRequestd2);
                
                map.put("status", 200);
                map.put("dr1", resultd1);
                map.put("dr2", resultd2);
                map.put("dr3", resultd3);
                map.put("dr11", genre_CateRepository.findById(likedgenrelist.get(0).getGenrecode()).orElse(null));
                map.put("dr22", genre_CateRepository.findById(likedgenrelist.get(1).getGenrecode()).orElse(null));
                map.put("dr33", genre_CateRepository.findById(likedgenrelist.get(2).getGenrecode()).orElse(null));
            }
            else{
                map.put("status", 0);
                map.put("result", "추천할 장르의 갯수가 부족합니다");
            }
        }
        else{
            map.put("status", 0);
            map.put("result", "좋아요 한 컨텐츠가 없습니다.");
        }
         
        return map;
    }
        
}
    

