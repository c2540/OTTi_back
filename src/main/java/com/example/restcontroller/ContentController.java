package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ContentUpdatePage;
import com.example.dto.DramaContent;
import com.example.dto.Drama_Later;
import com.example.dto.Drama_Like;
import com.example.dto.Drama_Review_Coment_Memberimage_View;
import com.example.dto.Drama_Review_Likescnt_View;
import com.example.dto.Drama_Review_View;
import com.example.dto.Genre_Cate;
import com.example.dto.Member;
import com.example.dto.Member_MemberImage_View;
import com.example.dto.MovieContent;
import com.example.dto.Movie_Later;
import com.example.dto.Movie_Like;
import com.example.dto.Movie_Review_Coment_Memberimage_View;
import com.example.dto.Movie_Review_Likescnt_View;
import com.example.dto.Movie_Review_View;
import com.example.dto.OTT_Cate;
import com.example.dto.Review_Drama;
import com.example.dto.Review_Drama_Coment;
import com.example.dto.Review_Drama_Like;
import com.example.dto.Review_Movie;
import com.example.dto.Review_Movie_Coment;
import com.example.dto.Review_Movie_Like;
import com.example.repository.Genre_CateRepository;
import com.example.repository.Ott_CateRepository;
import com.example.service.ContentService;
import com.example.service.MemberService;


@RestController
@RequestMapping(value = "/content")
public class ContentController {
    @Autowired ContentService contentService;
    @Autowired MemberService mService;
    @Autowired Genre_CateRepository gCateRepository;
    @Autowired Ott_CateRepository oCateRepository;

    // =========================================== 리뷰 ==============================================

    //드라마 리뷰하나 조회
    @GetMapping(value = "/selectonereviewdrama.json")
    public Map<String,Object> selectonereviewdrama(
        @RequestParam(name="id") Long id
        ){
            Map<String,Object> map = new HashMap<>();
            try {
                
                Review_Drama review_Drama = contentService.selectOneDramaReview(id);
                Member_MemberImage_View member_MemberImage_View = mService.profileFindByUserid(review_Drama.getMember().getUserid());

                map.put("status", 200);
                map.put("result", review_Drama);
                if(member_MemberImage_View.getNo() != null){
                    map.put("imageno", member_MemberImage_View.getNo());
                }
            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", -1);
            }
        return map;
        }
        
    //영화 리뷰하나 조회
    @GetMapping(value = "/selectonereviewmovie.json")
    public Map<String,Object> selectonereviewmovie(
        @RequestParam(name="id") Long id
        ){
            Map<String,Object> map = new HashMap<>();
            try {
                
                Review_Movie review_Movie = contentService.selectOneMovieReview(id);
                Member_MemberImage_View member_MemberImage_View = mService.profileFindByUserid(review_Movie.getMember().getUserid());

                map.put("status", 200);
                map.put("result", review_Movie);
                if(member_MemberImage_View.getNo() != null){
                    map.put("imageno", member_MemberImage_View.getNo());
                }
            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", -1);
            }
        return map;
        }



    //리뷰 드라마 댓글 작성
    @PostMapping(value = "/dramareviewreply.json")
    public Map<String,Object> dramareviewreply(
        @RequestBody Review_Drama_Coment review_Drama_Coment,
        @RequestParam(name="id")Long reviewid,
        HttpServletRequest request
        ) {
            Map<String,Object> map = new HashMap<>();
            try {
                String username = (String) request.getAttribute("username");
                Member member = mService.selectOne(username);

                Review_Drama review_Drama = new Review_Drama();
                review_Drama.setId(reviewid);

                review_Drama_Coment.setReviewdrama(review_Drama);
                review_Drama_Coment.setUserid(member);
                
                int ret = contentService.insertreplyDrama(review_Drama_Coment);
                if(ret == 1){
                    map.put("status", 200);
                    map.put("result", "댓글 등록");
                }else{
                    map.put("status", 0);
                }
                          
            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", -1);
            }
        return map;
        }

    //리뷰 드라마 댓글 조회
    @GetMapping(value = "/dramareviewreplyselect.json")
    public Map<String,Object> dramareviewreplyselect(
        @RequestParam(name="id")Long reviewid,
        @RequestParam(name="page") int page
        ) {
            Map<String,Object> map = new HashMap<>();
            try {
                int size = 10;
                Pageable pageable = PageRequest.of(page-1, size);
                
                long total = contentService.dramaReviewTotal(reviewid);
                List<Drama_Review_Coment_Memberimage_View> list = contentService.dramaReviewReplySelect(reviewid, pageable);
                if(list != null){
                    map.put("status", 200);
                    map.put("results", list);
                    map.put("pages", (total-1)/size+1);
                }
                else{
                    map.put("status", 0);
                    map.put("results", null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", -1);
            }
        return map;
        }


    //리뷰 드라마 댓글 삭제
    @DeleteMapping(value = "/deletedramareviewreply.json")
    public Map<String,Object> deletetvreviewreply(
        @RequestParam(name="id") Long id,
        HttpServletRequest request
        ) {
            Map<String,Object> map = new HashMap<>();
            try {              
                String username = (String) request.getAttribute("username");
                Member member = mService.selectOne(username);

                int ret = contentService.deleteDramaReviewReply(id,member);
               
                if(ret == 1){
                    map.put("status", 200);
                    map.put("result", "리뷰 삭제완료");
                }

                else if (ret == 0){
                    map.put("status", 0);
                }

            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", -1);
            }
        return map;
        }    


    //리뷰 영화댓글 작성
    @PostMapping(value = "/moviereviewreply.json")
    public Map<String,Object> moviereviewreply(
        @RequestBody Review_Movie_Coment review_movie_Coment,
        @RequestParam(name="id")Long reviewid,
        HttpServletRequest request
        ) {
            Map<String,Object> map = new HashMap<>();
            try {
                String username = (String) request.getAttribute("username");
                Member member = mService.selectOne(username);

                Review_Movie review_movie = new Review_Movie();
                review_movie.setId(reviewid);

                review_movie_Coment.setReviewmovie(review_movie);
                review_movie_Coment.setUserid(member);
                
                int ret = contentService.insertreplyMovie(review_movie_Coment);
                if(ret == 1){
                    map.put("status", 200);
                    map.put("result", "댓글 등록");
                }
                          
            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", -1);
            }
        return map;
        }
        
    //리뷰 영화댓글 조회
    @GetMapping(value = "/moviereviewreplyselect.json")
    public Map<String,Object> moviereviewreplyselect(
        @RequestParam(name="id")Long reviewid,
        @RequestParam(name="page") int page
        ) {
            Map<String,Object> map = new HashMap<>();
            try {
                int size = 10;
                Pageable pageable = PageRequest.of(page-1, size);
                long total = contentService.movieReviewTotal(reviewid);
                List<Movie_Review_Coment_Memberimage_View> list = contentService.movieReviewReplySelect(reviewid, pageable);
                if(list != null){
                    map.put("status", 200);
                    map.put("results", list);
                    map.put("pages", (total-1)/10+1);

                }
                else{
                    map.put("status", 0);
                    map.put("results", null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", -1);
            }
        return map;
        }

    //리뷰 영화댓글 삭제
    @DeleteMapping(value = "/deletemoviereviewreply.json")
    public Map<String,Object> deletemoviereviewreply(
        @RequestParam(name="id") Long id,
        HttpServletRequest request
        ) {
            Map<String,Object> map = new HashMap<>();
            try {              
                String username = (String) request.getAttribute("username");
                Member member = mService.selectOne(username);

                int ret = contentService.deleteMovieReviewReply(id,member);
               
                if(ret == 1){
                    map.put("status", 200);
                    map.put("result", "리뷰 삭제완료");
                }

                else if (ret == 0){
                    map.put("status", 0);
                }

            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", -1);
            }
        return map;
        }    




    // 드라마 리뷰 좋아요 수
    @GetMapping(value = "/dramareviewlikecnt.json")
    public Map<String,Object> dramareviewlikecnt(
        @RequestParam(name="id")Long reviewid
        ) {
            Map<String,Object> map = new HashMap<>();
            try {

                List<Review_Drama_Like> list = contentService.dramaReviewLikeCnt(reviewid);

                map.put("status", 200);
                map.put("result", list.size());
            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", -1);
            }
        return map;
    }


    // 영화 리뷰 좋아요 수
    @GetMapping(value = "/moviereviewlikecnt.json")
    public Map<String,Object> moviereviewlikecnt(
        @RequestParam(name="id")Long reviewid
    ) {
            Map<String,Object> map = new HashMap<>();
            try {
                List<Review_Movie_Like> list = contentService.movieReviewLikeCnt(reviewid);
                
                map.put("status", 200);
                map.put("result", list.size());
            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", -1);
            }
        return map;
    }


    //드라마 댓글 수
    @GetMapping(value = "/dramareviewreplycnt.json")
    public Map<String,Object> dramareviewreplycnt(
        @RequestParam(name="id")Long reviewid
        ) {
            Map<String,Object> map = new HashMap<>();
            try {

                List<Review_Drama_Coment> list = contentService.dramaReviewReply(reviewid);

                map.put("status", 200);
                map.put("result", list.size());
            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", -1);
            }
        return map;
    }

    //영화 댓글 수
    @GetMapping(value = "/moviereviewreplycnt.json")
    public Map<String,Object> moviereviewreplycnt(
        @RequestParam(name="id")Long reviewid
        ) {
            Map<String,Object> map = new HashMap<>();
            try {

                List<Review_Movie_Coment> list = contentService.movieReviewReply(reviewid);

                map.put("status", 200);
                map.put("result", list.size());
            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", -1);
            }
        return map;
    }




    //드라마 리뷰좋아요
    @PostMapping(value = "/dramareviewlike.json")
    public Map<String,Object> dramareviewlike(
        @RequestBody Review_Drama_Like review_Drama_Like,
        @RequestParam(name="id")Long reviewid,
        HttpServletRequest request
        ) {
            Map<String,Object> map = new HashMap<>();
            try {
                String username = (String) request.getAttribute("username");
                Member member = mService.selectOne(username);

                Review_Drama review_Drama = new Review_Drama();
                review_Drama.setId(reviewid);

                List<Review_Drama_Like> list = contentService.dramaReviewLikechk(review_Drama,member);

                //중복체크
                if(list.size() == 0){  
                    review_Drama_Like.setReviewdrama(review_Drama); 
                    review_Drama_Like.setUserid(member);
                    contentService.insertLikeDrama(review_Drama_Like);
                    map.put("status", 200);
                    map.put("result", "like");
                }

                else if(list.size() != 0){
                    map.put("status", 0);
                    map.put("result", "already like");
                }
            
            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", -1);
            }
        return map;
        }



    //영화 리뷰 좋아요
    @PostMapping(value = "/moviereviewlike.json")
    public Map<String,Object> moviereviewlike(
        @RequestBody Review_Movie_Like review_Movie_Like,
        @RequestParam(name="id")Long reviewid,
        HttpServletRequest request
        ) {
            Map<String,Object> map = new HashMap<>();
            try {
                String username = (String) request.getAttribute("username");
                Member member = mService.selectOne(username);

                Review_Movie review_Movie = new Review_Movie();
                review_Movie.setId(reviewid);
                
                List<Review_Movie_Like> list = contentService.movieReviewLikechk(review_Movie,member);

                //중복체크
                if(list.size() == 0){
                    review_Movie_Like.setReviewmovie(review_Movie); 
                    review_Movie_Like.setUserid(member);
                    contentService.insertLikeMovie(review_Movie_Like);
                    map.put("status", 200);
                    map.put("result", "like");
                }
                else{
                    map.put("status", 0);
                    map.put("result", "already like");
                }
            
            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", -1);
            }
        return map;
        }
    

   
    //본인 드라마 리뷰 조회(true false)
    @GetMapping(value = "/mydramareviewchk.json")
    public Map<String,Object> mytvreviewchk(
        HttpServletRequest request,
        @RequestParam(name="no")Long dramano
        ) {
            Map<String,Object> map = new HashMap<>();
            try {
                String username = (String) request.getAttribute("username");

                List<Drama_Review_View> list = contentService.selectOneReviewDrama(dramano);

                for(int i=0 ; i<list.size() ;i++){
                    if( list.get(i).getMember().getUserid().equals(username)){

                        Drama_Review_View drama_Review_View = new Drama_Review_View();
                        drama_Review_View.setContent(list.get(i).getContent());
                        drama_Review_View.setScore(list.get(i).getScore());
                        drama_Review_View.setId(list.get(i).getId());
                 
                        map.put("status", 200);
                        map.put("result", "true");
                        map.put("content", drama_Review_View.getContent());
                        map.put("score", drama_Review_View.getScore());
                        map.put("id", drama_Review_View.getId());
    
                        break;
                    }
                    map.put("status", 0);
                    map.put("result", "false");
                }

            
            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", -1);
            }
        return map;
        }

    //본인 영화 리뷰 조회(true false)
    @GetMapping(value = "/mymoviereviewchk.json")
    public Map<String,Object> mymoviereviewchk(
        HttpServletRequest request,
        @RequestParam(name="no")Long movieno
        ) {
            Map<String,Object> map = new HashMap<>();
            try {
                String username = (String) request.getAttribute("username");

                List<Movie_Review_View> list =  contentService.selectOneReviewMovie(movieno);

                
                
                for(int i=0 ; i<list.size() ;i++){
                    if( list.get(i).getMember().getUserid().equals(username)){

                        Movie_Review_View movie_Review_View = new Movie_Review_View();
                        movie_Review_View.setContent(list.get(i).getContent());
                        movie_Review_View.setScore(list.get(i).getScore());
                        movie_Review_View.setId(list.get(i).getId());

                        map.put("status", 200);
                        map.put("result", "true");
                        map.put("content", movie_Review_View.getContent());
                        map.put("score", movie_Review_View.getScore());
                        map.put("id", movie_Review_View.getId());
                        
                        break;
                    }
                    map.put("status", 0);
                    map.put("result", "false");
                    
                }

            
            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", -1);
            }
        return map;
        }


    //드라마 1개 리뷰 조회(추천순)
    @GetMapping(value = "/dramareviewonelike.json")
    public Map<String,Object> DramaReviewOneLike(
        @RequestParam(name="no")Long dramano,
        @RequestParam(name="page") int page,
        @RequestParam(name="sort") Long type
        ){
        Map<String,Object> map = new HashMap<>();
        try {

            int size = 5;
            long total = contentService.DramaReviewTotal(dramano);
            if(type==1){
                
                Sort sort = Sort.by(Sort.Direction.DESC, "regdate");
                PageRequest pageable = PageRequest.of(page-1, size, sort);
                List<Drama_Review_Likescnt_View> list = contentService.selectOneReviewDramaLike(dramano,pageable);
                if(list.size() != 0){
                                    
                    map.put("status", 200);
                    map.put("result", list);
                    map.put("pages", (total-1)/5+1);
                    
                }
            }
            else{ 
                Sort sort = Sort.by(Sort.Direction.DESC, "likes");
                PageRequest pageable = PageRequest.of(page-1, size, sort);
                List<Drama_Review_Likescnt_View> list = contentService.selectOneReviewDramaLike(dramano,pageable);
                if(list.size() != 0){
                                    
                    map.put("status", 200);
                    map.put("result", list);
                    map.put("pages", (total-1)/5+1);
                    
                }
            }
            
            Double sum = 0D;
            Double avg = 0D;

            
            List<Drama_Review_View> list1 = contentService.selectOneReviewDrama(dramano);

            if(list1.size() != 0){
                for(int i=0; i<list1.size();i++){
                    sum += list1.get(i).getScore();
                }
                avg = sum / list1.size();
                map.put("avg",  Math.round(avg*100)/100.0);
            }

            
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
    return map;
    }


    //영화 1개 리뷰 조회(추천순)
    @GetMapping(value = "/moviereviewonelike.json")
    public Map<String,Object> MovieReviewOneLike(
        @RequestParam(name="no")Long movieno,
        @RequestParam(name="page") int page,
        @RequestParam(name="sort") Long type
        ){
        Map<String,Object> map = new HashMap<>();
        try {
            int size = 5;
            long total = contentService.MovieReviewTotal(movieno);
            if(type==1){
                Sort sort = Sort.by(Sort.Direction.DESC, "regdate");
                PageRequest pageable = PageRequest.of(page-1, size, sort);
                List<Movie_Review_Likescnt_View> list = contentService.selectOneReviewMovieLike(movieno,pageable);
                if(list.size() != 0){
                    map.put("status", 200);
                    map.put("result", list);
                    map.put("pages", (total-1)/5+1);
                }
            }
          
            else{
                Sort sort = Sort.by(Sort.Direction.DESC, "likes");
                PageRequest pageable = PageRequest.of(page-1, size, sort);
                List<Movie_Review_Likescnt_View> list = contentService.selectOneReviewMovieLike(movieno,pageable);
                if(list.size() != 0){
                    map.put("status", 200);
                    map.put("result", list);
                    map.put("pages", (total-1)/5+1);
                }
            }

            Double sum = 0D;
            Double avg = 0D;

                List<Movie_Review_View> list1 = contentService.selectOneReviewMovie(movieno);
                if(list1.size() != 0){
                    for(int i=0; i<list1.size();i++){
                        sum += list1.get(i).getScore();
                    }
                    avg = sum / list1.size();

                    map.put("avg",  Math.round(avg*100)/100.0);
                }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
    return map;
    }



    // 관리자 전체 리뷰 조회
    @GetMapping(value = "/admin/review.json")
    public Map<String,Object> adminreview(
        HttpServletRequest request,
        @RequestParam(name="page") int page
    ){
        Map<String,Object> map = new HashMap<>();
        try {
            int size = 10;
            Pageable pageable = PageRequest.of(page-1, size);
            
            long total = contentService.userDramaReviewTotal();
            long total1 = contentService.userMovieReviewTotal();

            List<Review_Drama> list = contentService.selectReviewDrama(pageable);
            List<Review_Movie> list1 = contentService.selectReviewMovie(pageable);
            
            if(list != null){ //드라마
                map.put("status", 200);
                map.put("result", list);
                map.put("pages", (total-1)/10+1);
            }
            if(list1 != null ){ //영화
                map.put("status", 200);
                map.put("result1", list1);
                map.put("pages1", (total1-1)/10+1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
    return map;
    }

    //본인 드라마 리뷰 조회
    @GetMapping(value = "/selectmydramareview.json")
    public Map<String,Object> userreviewtv(
        HttpServletRequest request,
        @RequestParam(name="page") int page
        ) {
            Map<String,Object> map = new HashMap<>();
            try {
                int size = 10;
                Pageable pageable = PageRequest.of(page-1, size);

                String username = (String) request.getAttribute("username");

                long total = contentService.userDramaReviewTotal(username);
                List<Review_Drama> list = contentService.selectReviewDrama(username,pageable);

                map.put("status", 200);
                map.put("result", list);
                map.put("pages", (total-1)/10+1);

            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", -1);
            }
        return map;
        }

    //본인 영화 리뷰 조회
    @GetMapping(value = "/selectmymoviereview.json")
    public Map<String,Object> userreviewmovie(
        HttpServletRequest request,
        @RequestParam(name="page") int page
        ) {
            Map<String,Object> map = new HashMap<>();
            try {
                int size = 10;
                Pageable pageable = PageRequest.of(page-1, size);

                String username = (String) request.getAttribute("username");

                long total = contentService.userMovieReviewTotal(username);

                List<Review_Movie> list = contentService.selectReviewMovie(username,pageable);     

                map.put("status", 200);
                map.put("result", list);
                map.put("pages", (total-1)/10+1);
   

               
            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", -1);
            }
        return map;
        }


    //드라마 리뷰 수정
    @PutMapping(value = "/updatetvreview.json")
    public Map<String,Object> updatetv(
        HttpServletRequest request,
        @RequestBody Review_Drama review_Drama,
        @RequestParam(name="no")long no
        ) {
            Map<String,Object> map = new HashMap<>();
            try {
                String username = (String) request.getAttribute("username");

                List<Review_Drama> list = contentService.selectReviewDrama(username);

                for(int i=0;i<list.size() ;i++){
                    if( list.get(i).getDramaContent().getId() == no){
                        list.get(i).setContent(review_Drama.getContent());
                        list.get(i).setScore(review_Drama.getScore());
                        
                        int ret = contentService.insertreviewdrama(list.get(i));
                        if(ret == 1){
                            map.put("status", 200);
                            map.put("result", "리뷰 수정완료");
                        }
                        else{
                            map.put("status", 0);
                        }
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", -1);
            }
        return map;
        }


    //영화 리뷰 수정
    @PutMapping(value = "/updatemoviereview.json")
    public Map<String,Object> updatemovie(
        @RequestBody Review_Movie review_Movie,
        @RequestParam(name="no")long no,
        HttpServletRequest request
        ) {
            Map<String,Object> map = new HashMap<>();
            try {
                String username = (String) request.getAttribute("username");
               
                List<Review_Movie> list = contentService.selectReviewMovie(username);
    
                for(int i=0;i<list.size() ;i++){
                    if( list.get(i).getMovieContent().getId() == no){
                        list.get(i).setContent(review_Movie.getContent());
                        list.get(i).setScore(review_Movie.getScore());
                        
                        int ret = contentService.insertreviewmovie(list.get(i));
                        if(ret == 1){
                            map.put("status", 200);
                            map.put("result", "리뷰 수정완료");
                        }
                        else{
                            map.put("status", 0);
                        }
                        break;
                    }
                }
              
            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", -1);
            }
        return map;
        }


    //드라마 리뷰 삭제
    @DeleteMapping(value = "/deletetvreview.json")
    public Map<String,Object> deletetv(
        @RequestParam(name="no") Long no
        ) {
            Map<String,Object> map = new HashMap<>();
            try {

                //외래키(좋아요)삭제
                Review_Drama review_Drama = new Review_Drama();
                review_Drama.setId(no);

                contentService.deletereviewlikedrama(review_Drama); //no = tmp.id(리뷰기본키,좋아요 리뷰 코드)
                
                //외래키(댓글)삭제
                contentService.deletereviewreplydrama(review_Drama);

                //---------------------------------------------------
                int ret = contentService.deletereviewdrama(no); //리뷰삭제

                if(ret ==1){
                    map.put("status", 200);
                    map.put("result", "리뷰 삭제완료");

                }
                else{
                    map.put("status", 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", -1);
            }
        return map;
        }

    //영화 리뷰 삭제
    @DeleteMapping(value = "/deletemoviereview.json")
    public Map<String,Object> deletemovie(
        @RequestParam(name="no") Long no
        ) {
            Map<String,Object> map = new HashMap<>();
            try {

                //외래키(좋아요)삭제
                Review_Movie review_Movie = new Review_Movie();
                review_Movie.setId(no);

                contentService.deletereviewlikemovie(review_Movie); //no = tmp.id(리뷰기본키,좋아요 리뷰 코드)

                //외래키(댓글)삭제
                contentService.deletereviewreplymovie(review_Movie);

                //---------------------------------------------------
                int ret = contentService.deletereviewmovie(no); //리뷰삭제
                if(ret ==1){
                    map.put("status", 200);
                    map.put("result", "리뷰 삭제완료");
                }
                else{
                    map.put("status", 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", -1);
            }
        return map;
        }


    //드라마 리뷰 작성
    @PostMapping(value = "/tvreview.json")
    public Map<String,Object> inserttv(
        @RequestBody Review_Drama review_Drama,
        HttpServletRequest request,
        @RequestParam(name="no")Long dramano
        ) {
            Map<String,Object> map = new HashMap<>();
            try {
                String username = (String) request.getAttribute("username");
                Member member = mService.selectOne(username);

                DramaContent dramaContent = new DramaContent();
                dramaContent.setId(dramano);

                review_Drama.setMember(member);
                review_Drama.setDramaContent(dramaContent);

                int ret = contentService.insertreviewdrama(review_Drama);
                if(ret ==1){
                    map.put("status", 200);
                    map.put("result", "리뷰 등록완료");
                }
                else{
                    map.put("status", 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", -1);
            }
        return map;
        }

    //영화 리뷰 작성
    @PostMapping(value = "/moviereview.json")
    public Map<String,Object> insertmovie(
        @RequestBody Review_Movie review_Movie,
        HttpServletRequest request,
        @RequestParam(name="no")Long movieno
        ) {
            Map<String,Object> map = new HashMap<>();
            try {
                String username = (String) request.getAttribute("username");
                Member member = mService.selectOne(username);

                MovieContent movieContent = new MovieContent();
                movieContent.setId(movieno);

                review_Movie.setMember(member);
                review_Movie.setMovieContent(movieContent);

                int ret = contentService.insertreviewmovie(review_Movie);
                if(ret ==1){
                    map.put("status", 200);
                    map.put("result", "리뷰 등록완료");
                }
                else{
                    map.put("status", 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", -1);
            }
        return map;
        }

    
    // ========================== 나중에 볼 작품 ========================
    
    // 나중에 볼 영화 등록여부 조회
    @GetMapping(value = "/movielaterview.json")
    public Map<String,Object> movieLaterViewGET(
        @RequestParam(name="id") Long id,
        HttpServletRequest request
    ){
        Map<String, Object> map = new HashMap<>();
        try{
            String username = (String) request.getAttribute("username");
            Member member = mService.selectOne(username);
            MovieContent movieContent = new MovieContent();
            movieContent.setId(id);
            
            List<Movie_Later> list = contentService.findMovieLater(movieContent, member);
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

    // 나중에 볼 영화 등록
    @PostMapping(value = "/movielaterinsert.json")
    public Map<String,Object> movieLaterInsertPOST(
        @RequestParam(name="id") Long id,
        @RequestParam(name="type") Long type,
        HttpServletRequest request
    ){
        Map<String, Object> map = new HashMap<>();
        try{
            Movie_Later mLater = new Movie_Later();
            String username = (String) request.getAttribute("username");
            Member member = mService.selectOne(username);
            MovieContent movieContent = new MovieContent();
            movieContent.setId(id);
            mLater.setUserid(member);
            mLater.setMoviecode(movieContent);
            if(type==0){
                Movie_Later ret = contentService.movieLaterSave(mLater);
                if(ret != null){
                    map.put("status", 200);
                    map.put("result", "등록성공");
                }
                else{
                    map.put("status", 0);
                    map.put("result", "등록실패");
                }
            }else if(type==1){
                List<Movie_Later> list = contentService.findMovieLater(movieContent, member);
                if(list.size() != 0){
                    for(int i=0;i<list.size();i++){
                        contentService.movieLaterDeleteById(list.get(i).getId());
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

    // 본인이 등록한 나중에 볼 영화 목록 조회
    @GetMapping(value = "/movielaterlist.json")
    public Map<String,Object> movieLaterListGET(HttpServletRequest request, @RequestParam(name="page") int page){
        Map<String, Object> map = new HashMap<>();
        try{
            int size = 5;
            Pageable pageable = PageRequest.of(page-1, size);
            String username = (String) request.getAttribute("username");
            Member member = mService.selectOne(username);
            long total = contentService.movieLaterTotal(member);
            List<Movie_Later> mlList= contentService.movieLaterFindByUserid(member, pageable);
            if(mlList != null){
                map.put("status", 200);
                map.put("result", mlList);
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

    // ===================================== 나중에 볼 드라마 ==========================

    // 나중에 볼 드라마 등록여부 조회
    @GetMapping(value = "/dramalaterview.json")
    public Map<String,Object> dramaLaterViewGET(
        @RequestParam(name="id") Long id,
        HttpServletRequest request
    ){
        Map<String, Object> map = new HashMap<>();
        try{
            String username = (String) request.getAttribute("username");
            Member member = mService.selectOne(username);
            DramaContent dramaContent = new DramaContent();
            dramaContent.setId(id);
            
            List<Drama_Later> list = contentService.findDramaLater(dramaContent, member);
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

    // 나중에 볼 드라마 등록
    @PostMapping(value = "/dramalaterinsert.json")
    public Map<String,Object> dramaLaterInsertPOST(
        @RequestParam(name="id") Long id,
        @RequestParam(name="type") Long type,
        HttpServletRequest request
    ){
        Map<String, Object> map = new HashMap<>();
        try{
            Drama_Later dLater = new Drama_Later();
            String username = (String) request.getAttribute("username");
            Member member = mService.selectOne(username);
            DramaContent dramaContent = new DramaContent();
            dramaContent.setId(id);
            dLater.setUserid(member);
            dLater.setDramacode(dramaContent);
            if(type==0){
                Drama_Later ret = contentService.dramaLaterSave(dLater);
                if(ret != null){
                    map.put("status", 200);
                    map.put("result", "등록성공");
                }
                else{
                    map.put("status", 0);
                    map.put("result", "등록실패");
                }
            }else if(type==1){
                List<Drama_Later> list = contentService.findDramaLater(dramaContent, member);
                if(list.size() != 0){
                    for(int i=0;i<list.size();i++){
                        contentService.dramaLaterDeleteById(list.get(i).getId());
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

    // 본인이 등록한 나중에 볼 드라마 목록 조회
    @GetMapping(value = "/dramalaterlist.json")
    public Map<String,Object> dramaLaterListGET(HttpServletRequest request, @RequestParam(name="page") int page){
        Map<String, Object> map = new HashMap<>();
        try{
            int size = 5;
            Pageable pageable = PageRequest.of(page-1, size);
            String username = (String) request.getAttribute("username");
            Member member = mService.selectOne(username);
            long total = contentService.dramaLaterTotal(member);
            List<Drama_Later> dlList= contentService.dramaLaterFindByUserid(member, pageable);
            if(dlList != null){
                map.put("status", 200);
                map.put("result", dlList);
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

    // ================================== 좋아요 ================================

    // 좋아요한 영화 등록여부 조회
    @GetMapping(value = "/movielikeview.json")
    public Map<String,Object> movieLikeViewGET(
        @RequestParam(name="id") Long id,
        HttpServletRequest request
    ){
        Map<String, Object> map = new HashMap<>();
        try{
            String username = (String) request.getAttribute("username");
            Member member = mService.selectOne(username);
            MovieContent movieContent = new MovieContent();
            movieContent.setId(id);
            
            List<Movie_Like> list = contentService.findMovieLike(movieContent, member);
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

    // 좋아요한 영화 등록
    @PostMapping(value = "/movielikeinsert.json")
    public Map<String,Object> movieLikeInsertPOST(
        @RequestParam(name="id") Long id,
        @RequestParam(name="type") Long type,
        HttpServletRequest request
    ){
        Map<String, Object> map = new HashMap<>();
        try{
            Movie_Like mLike = new Movie_Like();
            String username = (String) request.getAttribute("username");
            Member member = mService.selectOne(username);
            MovieContent movieContent = new MovieContent();
            movieContent.setId(id);
            mLike.setUserid(member);
            mLike.setMoviecode(movieContent);
            if(type==0){
                Movie_Like ret = contentService.movieLikeSave(mLike);
                if(ret != null){
                    map.put("status", 200);
                    map.put("result", "등록성공");
                }
                else{
                    map.put("status", 0);
                    map.put("result", "등록실패");
                }
            }else if(type==1){
                List<Movie_Like> list = contentService.findMovieLike(movieContent, member);
                if(list.size() != 0){
                    for(int i=0;i<list.size();i++){
                        contentService.movieLikeDeleteById(list.get(i).getId());
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

    // 본인이 좋아요한 영화 목록 조회
    @GetMapping(value = "/movielikelist.json")
    public Map<String,Object> movieLikeListGET(
            HttpServletRequest request,
            @RequestParam(name="page") int page
        ){
        Map<String, Object> map = new HashMap<>();
        try{
            int size = 5;
            Pageable pageable = PageRequest.of(page-1, size);
            String username = (String) request.getAttribute("username");
            Member member = mService.selectOne(username);
            long total = contentService.movieLikeTotal(member);
            List<Movie_Like> mlList= contentService.movieLikeFindByUserid(member, pageable);
            if(mlList != null){
                map.put("status", 200);
                map.put("result", mlList);
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


    // 좋아요한 드라마 등록여부 조회
    @GetMapping(value = "/dramalikeview.json")
    public Map<String,Object> dramaLikeGET(
        @RequestParam(name="id") Long id,
        HttpServletRequest request
    ){
        Map<String, Object> map = new HashMap<>();
        try{
            String username = (String) request.getAttribute("username");
            Member member = mService.selectOne(username);
            DramaContent dramaContent = new DramaContent();
            dramaContent.setId(id);
            
            List<Drama_Like> list = contentService.findDramaLike(dramaContent, member);
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

    // 좋아요한 드라마 등록
    @PostMapping(value = "/dramalikeinsert.json")
    public Map<String,Object> dramaLikeInsertPOST(
        @RequestParam(name="id") Long id,
        @RequestParam(name="type") Long type,
        HttpServletRequest request
    ){
        Map<String, Object> map = new HashMap<>();
        try{
            Drama_Like mLike = new Drama_Like();
            String username = (String) request.getAttribute("username");
            Member member = mService.selectOne(username);
            DramaContent dramaContent = new DramaContent();
            dramaContent.setId(id);
            mLike.setUserid(member);
            mLike.setDramacode(dramaContent);
            if(type==0){
                Drama_Like ret = contentService.dramaLikeSave(mLike);
                if(ret != null){
                    map.put("status", 200);
                    map.put("result", "등록성공");
                }
                else{
                    map.put("status", 0);
                    map.put("result", "등록실패");
                }
            }else if(type==1){
                List<Drama_Like> list = contentService.findDramaLike(dramaContent, member);
                if(list.size() != 0){
                    for(int i=0;i<list.size();i++){
                        contentService.dramaLikeDeleteById(list.get(i).getId());
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
    // 본인이 좋아요한 드라마 목록 조회
    @GetMapping(value = "/dramalikelist.json")
    public Map<String,Object> dramaLikeListGET(HttpServletRequest request, @RequestParam(name="page") int page){
        Map<String, Object> map = new HashMap<>();
        try{
            int size = 5;
            Pageable pageable = PageRequest.of(page-1, size);
            String username = (String) request.getAttribute("username");
            Member member = mService.selectOne(username);
            long total = contentService.dramaLikeTotal(member);
            List<Drama_Like> dlList= contentService.dramaLikeFindByUserid(member, pageable);
            if(dlList != null){
                map.put("status", 200);
                map.put("result", dlList);
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
    // 컨텐츠 수정요청
    @PostMapping(value="/contentupdate.json")
    public Map<String,Object> ContentUpdate(
        @RequestBody ContentUpdatePage content,
        HttpServletRequest request
        ) {
            Map<String,Object> map = new HashMap<>();
            String userid = (String) request.getAttribute("username");
            content.setUserid(userid);
            
            int ret = contentService.UpdateContent(content);
            if(ret == 1){
                map.put("status", 200);
            }
            else{
                map.put("status", -1);
            }
        return map;
    }

    //컨텐츠 수정정보 불러오기
    @GetMapping(value="/admin/updatecontentlist.json")
    public Map<String,Object> ContentUpdatelist(
        HttpServletRequest request, @RequestParam(name="page") int page
        ) {
            int size = 10;
            Pageable pageable = PageRequest.of(page-1, size);
            Map<String,Object> map = new HashMap<>();
            String role = (String) request.getAttribute("role");
            if(role.equals("ADMIN")){
                Page<ContentUpdatePage> list = contentService.UpdateContentList(pageable);
                String catename2 = "";
                for(int i=0; i<list.getContent().size(); i++){
                    if(list.getContent().get(i).getGenre() != null){
                        for(int j=0; j<list.getContent().get(i).getGenre().split(",").length; j ++){
                            Genre_Cate catename = gCateRepository.findById(Long.valueOf(list.getContent().get(i).getGenre().split(",")[j])).orElse(null);
                            catename2 = catename2.concat(catename.getName()+",");
                        }
                        list.getContent().get(i).setGenre(catename2);
                        catename2 = "";
                    }
                }
                String ottname2 = "";
                for(int i=0; i<list.getContent().size(); i++){
                    if(list.getContent().get(i).getProvider() != null){
                        for(int j=0; j<list.getContent().get(i).getProvider().split(",").length; j ++){
                            OTT_Cate ottname = oCateRepository.findById(Long.valueOf(list.getContent().get(i).getProvider().split(",")[j])).orElse(null);
                            ottname2 = ottname2.concat(ottname.getProvider_name()+",");
                        }
                        list.getContent().get(i).setProvider(ottname2);
                        ottname2 = "";
                    }
                }
                map.put("result", list);
            }
            
        return map;
    }

    //컨텐츠 수정 정보 삭제
    @DeleteMapping(value = "/deleteinfo.json")
    public Map<String,Object> deleteupdate(
        @RequestParam(name = "no") Long id
    ){
        Map<String,Object> map = new HashMap<>();
        int ret = contentService.deleteinfo(id);
        if(ret == 1){
            map.put("status", 200);
        }
        else{
            map.put("statue", -1);
        }
        return map;
    }

    //컨텐츠 수정 정보 승인
    @PutMapping(value =  "/updatedone.json")
    public Map<String,Object> updateaction(
        @RequestParam(name="no") Long id
    ){
        Map<String,Object> map = new HashMap<>();
        ContentUpdatePage updatecontentinfo = contentService.UpdateContentone(id);

        int ret = 0;
        if(updatecontentinfo.getType().equals("tv")){
            ret = contentService.confirmupdateD(updatecontentinfo);
        }
        else{
            ret = contentService.confirmupdateM(updatecontentinfo);
        }
        if(ret == 1){
            map.put("status", 200);
        }else{
            map.put("status", 0);
        }
        return map;
    }

}
