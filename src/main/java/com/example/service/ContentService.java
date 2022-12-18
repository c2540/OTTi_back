package com.example.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.dto.ContentUpdatePage;
import com.example.dto.DramaContent;
import com.example.dto.Drama_Later;
import com.example.dto.Drama_Like;
import com.example.dto.Drama_Review_Coment_Memberimage_View;
import com.example.dto.Drama_Review_Likescnt_View;
import com.example.dto.Drama_Review_View;
import com.example.dto.Member;
import com.example.dto.MovieContent;
import com.example.dto.Movie_Later;
import com.example.dto.Movie_Like;
import com.example.dto.Movie_Review_Coment_Memberimage_View;
import com.example.dto.Movie_Review_Likescnt_View;
import com.example.dto.Movie_Review_View;
import com.example.dto.Review_Drama;
import com.example.dto.Review_Drama_Coment;
import com.example.dto.Review_Drama_Like;
import com.example.dto.Review_Movie;
import com.example.dto.Review_Movie_Coment;
import com.example.dto.Review_Movie_Like;

@Service
public interface ContentService {
    
    public int insertreviewdrama(Review_Drama review_Drama);
    public int insertreviewmovie(Review_Movie review_Movie);

    public int deletereviewdrama(Long no);
    public int deletereviewmovie(Long no);

    public int insertLikeDrama(Review_Drama_Like review_Drama_Like);
    public int insertLikeMovie(Review_Movie_Like review_Movie_Like);

    public List<Review_Drama_Like> dramaReviewLikeCnt(Long no);
    public List<Review_Movie_Like> movieReviewLikeCnt(Long no);

    public List<Review_Drama_Coment> dramaReviewReply(Long no);
    public List<Review_Movie_Coment> movieReviewReply(Long no);
    

    public List<Review_Drama_Like> dramaReviewLikechk(Review_Drama reviewdrama, Member userid); 
    public List<Review_Movie_Like> movieReviewLikechk(Review_Movie reviewdrama, Member userid);

    public int deletereviewlikedrama(Review_Drama reviewdrama);
    public int deletereviewlikemovie(Review_Movie reviewmovie);

    public int deletereviewreplydrama(Review_Drama reviewdrama);
    public int deletereviewreplymovie(Review_Movie reviewmovie);



    public int insertreplyDrama(Review_Drama_Coment review_Drama_Coment);
    public int insertreplyMovie(Review_Movie_Coment review_Movie_Coment);
    public List<Drama_Review_Coment_Memberimage_View> dramaReviewReplySelect(Long no, Pageable pageable);
    public List<Movie_Review_Coment_Memberimage_View> movieReviewReplySelect(Long no, Pageable pageable);
    public long dramaReviewTotal(Long reviewid);
    public long movieReviewTotal(Long reviewid);
    public int deleteDramaReviewReply(Long no,Member member);
    public int deleteMovieReviewReply(Long no,Member member);



    //관리자
    public List<Review_Drama> selectReviewDrama(Pageable pageable);
    public List<Review_Movie> selectReviewMovie(Pageable pageable);

    public long userDramaReviewTotal();
    public long userMovieReviewTotal();

    //유저
    public List<Review_Drama> selectReviewDrama(String userid,Pageable pageable);
    public List<Review_Drama> selectReviewDramaOrderByScoreDESC(String userid,Pageable pageable);
    public List<Review_Drama> selectReviewDrama(String userid);
    public Review_Drama selectOneDramaReview(Long id);

    public List<Review_Movie> selectReviewMovie(String userid,Pageable pageable);
    public List<Review_Movie> selectReviewMovieOrderByScoreDESC(String userid,Pageable pageable);
    public List<Review_Movie> selectReviewMovie(String userid);
    public Review_Movie selectOneMovieReview(Long id);

    public long userDramaReviewTotal(String userid);
    public long userMovieReviewTotal(String userid);

    public long DramaReviewTotal(Long no);
    public long MovieReviewTotal(Long no);

    public List<Drama_Review_View> selectOneReviewDrama(Long dramano,Pageable pageable);
    public List<Drama_Review_View> selectOneReviewDrama(Long dramano);
    public List<Movie_Review_View> selectOneReviewMovie(Long movieno,Pageable pageable);
    public List<Movie_Review_View> selectOneReviewMovie(Long movieno);

    public List<Drama_Review_Likescnt_View> selectOneReviewDramaLike(Long dramano,PageRequest pageable);
    public List<Movie_Review_Likescnt_View> selectOneReviewMovieLike(Long movieno,PageRequest pageable);


    public Review_Drama selectOneDrama(String userid);
    public Review_Movie selectOneMovie(Long id);

    public List<Movie_Later> findMovieLater(MovieContent movieContent, Member member);
    public void movieLaterDeleteById(Long id);
    public Movie_Later movieLaterSave(Movie_Later mLater);
    public List<Movie_Later> movieLaterFindByUserid(Member member, Pageable pageable);
    public long movieLaterTotal(Member member);
    
    public List<Drama_Later> findDramaLater(DramaContent dramaContent, Member member);
    public Drama_Later dramaLaterSave(Drama_Later dLater);
    public void dramaLaterDeleteById(Long id);
    public List<Drama_Later> dramaLaterFindByUserid(Member member, Pageable pageable);
    public long dramaLaterTotal(Member member);

    public List<Movie_Like> findMovieLike(MovieContent movieContent, Member member);
    public void movieLikeDeleteById(Long id);
    public Movie_Like movieLikeSave(Movie_Like mLike);
    public List<Movie_Like> movieLikeFindByUserid(Member member, Pageable pageable);
    public long movieLikeTotal(Member member);

    public List<Drama_Like> findDramaLike(DramaContent dramaContent, Member member);
    public void dramaLikeDeleteById(Long id);
    public Drama_Like dramaLikeSave(Drama_Like dLike);
    public List<Drama_Like> dramaLikeFindByUserid(Member member, Pageable pageable);
    public long dramaLikeTotal(Member member);

    // 수정
    public int UpdateContent(ContentUpdatePage content);
    public Page<ContentUpdatePage> UpdateContentList(Pageable pageable);
    public ContentUpdatePage UpdateContentone(Long no);
    public int deleteinfo(Long no);
    public int confirmupdateD(ContentUpdatePage content);
    public int confirmupdateM(ContentUpdatePage content);
}
