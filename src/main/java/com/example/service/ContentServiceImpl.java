package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.dto.Genre_Cate;
import com.example.dto.Genre_Drama;
import com.example.dto.Genre_Movie;
import com.example.dto.Member;
import com.example.dto.MovieContent;
import com.example.dto.Movie_Later;
import com.example.dto.Movie_Like;
import com.example.dto.Movie_Review_Coment_Memberimage_View;
import com.example.dto.Movie_Review_Likescnt_View;
import com.example.dto.Movie_Review_View;
import com.example.dto.OTT_Cate;
import com.example.dto.OTT_Drama;
import com.example.dto.OTT_Movie;
import com.example.dto.Review_Drama;
import com.example.dto.Review_Drama_Coment;
import com.example.dto.Review_Drama_Like;
import com.example.dto.Review_Movie;
import com.example.dto.Review_Movie_Coment;
import com.example.dto.Review_Movie_Like;
import com.example.repository.ContentDRepository;
import com.example.repository.ContentMRepository;
import com.example.repository.ContentUpdateRepository;
import com.example.repository.Drama_LaterRepository;
import com.example.repository.Drama_LikeRepository;
import com.example.repository.Drama_Review_Coment_Memberimage_ViewRepository;
import com.example.repository.Drama_Review_LikescntRepository;
import com.example.repository.Drama_Review_ViewRepository;
import com.example.repository.Genre_DramaRepository;
import com.example.repository.Genre_MovieRepository;
import com.example.repository.Movie_LaterRepository;
import com.example.repository.Movie_LikeRepository;
import com.example.repository.Movie_Review_Coment_Memberimage_ViewRepository;
import com.example.repository.Movie_Review_LikescntRepository;
import com.example.repository.Movie_Review_ViewRepository;
import com.example.repository.OttDramaRepository;
import com.example.repository.OttMovieRepository;
import com.example.repository.Review_DramaRepository;
import com.example.repository.Review_Drama_LikeRepository;
import com.example.repository.Review_Drama_ReplyRepository;
import com.example.repository.Review_MovieRepository;
import com.example.repository.Review_Movie_LikeRepository;
import com.example.repository.Review_Movie_ReplyRepository;

@Service
public class ContentServiceImpl implements ContentService{
    @Autowired Review_DramaRepository review_DramaRepository;
    @Autowired Review_MovieRepository review_MovieRepository;
    @Autowired Movie_LaterRepository movie_LaterRepository;
    @Autowired Movie_LikeRepository movie_LikeRepository;
    @Autowired Drama_LaterRepository drama_LaterRepository;
    @Autowired Drama_LikeRepository drama_LikeRepository;
    @Autowired Drama_Review_ViewRepository drama_Review_ViewRepository;
    @Autowired Movie_Review_ViewRepository movie_Review_ViewRepository;
    @Autowired Review_Movie_LikeRepository review_Movie_LikeRepository;
    @Autowired Review_Drama_LikeRepository review_Drama_LikeRepository;
    @Autowired Review_Drama_ReplyRepository review_Drama_ReplyRepository;
    @Autowired Review_Movie_ReplyRepository review_Movie_ReplyRepository;
    @Autowired Drama_Review_Coment_Memberimage_ViewRepository drcmRepository;
    @Autowired Movie_Review_Coment_Memberimage_ViewRepository mrcmRepository;
    @Autowired ContentUpdateRepository cUpdateRepository;
    @Autowired OttDramaRepository oDramaRepository;
    @Autowired OttMovieRepository oMovieRepository;
    @Autowired ContentDRepository contentDRepository;
    @Autowired ContentMRepository contentMRepository;
    @Autowired Genre_DramaRepository gDramaRepository;
    @Autowired Genre_MovieRepository gMovieRepository;
    @Autowired Movie_Review_LikescntRepository movie_Review_LikescntRepository;
    @Autowired Drama_Review_LikescntRepository drama_Review_LikescntRepository;
    

    @Override
    public int insertreviewdrama(Review_Drama review_Drama) {
        try{
            review_DramaRepository.save(review_Drama);
            return 1;
        }
        catch(Exception e){
            return 0;
        }
    }

    @Override
    public int insertreviewmovie(Review_Movie review_Movie) {
        try{
            review_MovieRepository.save(review_Movie);
            return 1;
        }
        catch(Exception e){
            return 0;
        }
    }


    @Override
    public int deletereviewdrama(Long no) {
        try{
            review_DramaRepository.deleteById(no);
            return 1;
        }
        catch(Exception e){
            return 0;
        }
    }


    @Override
    public int deletereviewmovie(Long no) {
        try{
            review_MovieRepository.deleteById(no);
            return 1;
        }
        catch(Exception e){
            return 0;
        }
    }


       // ============================ 나중에 볼 영화 ====================================
       @Override
       public List<Movie_Later> findMovieLater(MovieContent movieContent, Member member) {
           try{
               List<Movie_Later> retList = movie_LaterRepository.findByMoviecodeAndUserid(movieContent, member);
   
               return retList;
           }
           catch(Exception e){
               return null;
           }
       }
       @Override
       public void movieLaterDeleteById(Long id) {
           movie_LaterRepository.deleteById(id);
       }
       @Override
       public Movie_Later movieLaterSave(Movie_Later mLater) {
           Movie_Later ret = movie_LaterRepository.save(mLater);
           return ret;
       }
       @Override
       public List<Movie_Later> movieLaterFindByUserid(Member member, Pageable pageable) {
           List<Movie_Later> ret = movie_LaterRepository.findByUseridOrderByIdDesc(member, pageable);
           return ret;
       }
   
       // ============================ 나중에 볼 드라마 ====================================
       @Override
       public List<Drama_Later> findDramaLater(DramaContent dramaContent, Member member) {
           try{
               List<Drama_Later> retList = drama_LaterRepository.findByDramacodeAndUserid(dramaContent, member);
   
               return retList;
           }
           catch(Exception e){
               return null;
           }
       }
       @Override
       public void dramaLaterDeleteById(Long id) {
           drama_LaterRepository.deleteById(id);
       }
       @Override
       public Drama_Later dramaLaterSave(Drama_Later dLater) {
           Drama_Later ret = drama_LaterRepository.save(dLater);
           return ret;
       }
       @Override
       public List<Drama_Later> dramaLaterFindByUserid(Member member, Pageable pageable) {
           List<Drama_Later> ret = drama_LaterRepository.findByUseridOrderByIdDesc(member, pageable);
           return ret;
       }
       @Override
       public long movieLaterTotal(Member member){
           try{
               long total = movie_LaterRepository.countByUserid(member);
               return total;            
           }
           catch(Exception e){
               return -1;
           }
       }
       @Override
       public long dramaLaterTotal(Member member){
           try{
               long total = drama_LaterRepository.countByUserid(member);
               return total;            
           }
           catch(Exception e){
               return -1;
           }
       }
       
       // ============================ 좋아요한 영화 ====================================
       @Override
       public List<Movie_Like> findMovieLike(MovieContent movieContent, Member member) {
           try{
               List<Movie_Like> retList = movie_LikeRepository.findByMoviecodeAndUserid(movieContent, member);
   
               return retList;
           }
           catch(Exception e){
               return null;
           }
       }
       @Override
       public Movie_Like movieLikeSave(Movie_Like mLike) {
           Movie_Like ret = movie_LikeRepository.save(mLike);
           return ret;
       }
       @Override
       public void movieLikeDeleteById(Long id) {
           movie_LikeRepository.deleteById(id);
       }
       @Override
       public List<Movie_Like> movieLikeFindByUserid(Member member, Pageable pageable) {
           List<Movie_Like> ret = movie_LikeRepository.findByUseridOrderByIdDesc(member, pageable);
           return ret;
       }
       @Override
       public long movieLikeTotal(Member member){
           try{
               long total = movie_LikeRepository.countByUserid(member);
               return total;            
           }
           catch(Exception e){
               return -1;
           }
       }
   
       // ============================ 좋아요한 드라마 ====================================
       @Override
       public List<Drama_Like> findDramaLike(DramaContent dramaContent, Member member) {
           try{
               List<Drama_Like> retList = drama_LikeRepository.findByDramacodeAndUserid(dramaContent, member);
   
               return retList;
           }
           catch(Exception e){
               return null;
           }
       }
       @Override
       public Drama_Like dramaLikeSave(Drama_Like dLike) {
           Drama_Like ret = drama_LikeRepository.save(dLike);
           return ret;
       }
       @Override
       public void dramaLikeDeleteById(Long id) {
           drama_LikeRepository.deleteById(id);
       }
       @Override
       public List<Drama_Like> dramaLikeFindByUserid(Member member, Pageable pageable) {
           List<Drama_Like> ret = drama_LikeRepository.findByUseridOrderByIdDesc(member, pageable);
           return ret;
       }
       @Override
       public long dramaLikeTotal(Member member){
           try{
               long total = drama_LikeRepository.countByUserid(member);
               return total;            
           }
           catch(Exception e){
               return -1;
           }
       }
 
    
    @Override
    public List<Review_Drama> selectReviewDrama(Pageable pageable) {
        try{
            return review_DramaRepository.findAllByOrderByIdDesc(pageable);
        }
        catch(Exception e){
            return null;

        }
    }

    @Override
    public List<Review_Movie> selectReviewMovie(Pageable pageable) {
        try{
            return review_MovieRepository.findAllByOrderByIdDesc(pageable);
        }
        catch(Exception e){
            return null;
            
        }
    }

    @Override
    public Review_Drama selectOneDrama(String userid) {
            return null;
        // return review_DramaRepository.findByMember_userid(us erid);
         }

    @Override
    public Review_Movie selectOneMovie(Long id) {
        
        return review_MovieRepository.findById(id).orElse(null);
    }

    @Override
    public List<Review_Drama> selectReviewDrama(String userid,Pageable pageable) {
        try{
            return review_DramaRepository.findByMember_useridOrderByIdDesc(userid,pageable);
        }
        catch(Exception e){
            return null;

        }
    }
    @Override
    public List<Review_Drama> selectReviewDramaOrderByScoreDESC(String userid,Pageable pageable) {
        try{
            return review_DramaRepository.findByMember_useridOrderByScoreDescIdDesc(userid,pageable);
        }
        catch(Exception e){
            return null;

        }
    }

    @Override
    public List<Review_Movie> selectReviewMovie(String userid,Pageable pageable) {
        try{
            return review_MovieRepository.findByMember_useridOrderByIdDesc(userid,pageable);
        }
        catch(Exception e){
            return null;

        }
    }
    @Override
    public List<Review_Movie> selectReviewMovieOrderByScoreDESC(String userid,Pageable pageable) {
        try{
            return review_MovieRepository.findByMember_useridOrderByScoreDescIdDesc(userid,pageable);
        }
        catch(Exception e){
            return null;

        }
    }

    @Override
    public long userDramaReviewTotal(String userid) {
        try{
            long total = review_DramaRepository.countByMember_userid(userid);
            return total;            
        }
        catch(Exception e){
            return -1;
        }
    }

    @Override
    public long userMovieReviewTotal(String userid) {
        try{
            long total = review_MovieRepository.countByMember_userid(userid);
            return total;            
        }
        catch(Exception e){
            return -1;
        }
    }

    @Override
    public List<Review_Drama> selectReviewDrama(String userid) {
        try{
            return review_DramaRepository.findByMember_userid(userid);
        }
        catch(Exception e){
            return null;

        }
    }

    @Override
    public List<Review_Movie> selectReviewMovie(String userid) {
        try{
            return review_MovieRepository.findByMember_userid(userid);
        }
        catch(Exception e){
            return null;

        }
    }

    @Override
    public List<Drama_Review_View> selectOneReviewDrama(Long dramano,Pageable pageable) {
        try{
            return drama_Review_ViewRepository.findBydramaContent_idOrderByRegdateDesc(dramano,pageable);
        }
        catch(Exception e){
            return null;

        }
    }
    @Override
    public List<Drama_Review_View> selectOneReviewDrama(Long dramano) {
        try{
            return drama_Review_ViewRepository.findBydramaContent_idOrderByRegdateDesc(dramano);
        }
        catch(Exception e){
            return null;

        }
    }

    @Override
    public List<Movie_Review_View> selectOneReviewMovie(Long movieno) {
        try{
            return movie_Review_ViewRepository.findBymovieContent_idOrderByRegdateDesc(movieno);
        }
        catch(Exception e){
            return null;

        }
    }

    @Override
    public int insertLikeDrama(Review_Drama_Like review_Drama_Like) {
        try{
            review_Drama_LikeRepository.save(review_Drama_Like);
            return 1;
        }
        catch(Exception e){
            return 0;
        }
    }

    @Override
    public int insertLikeMovie(Review_Movie_Like review_Movie_Like) {
        try{
            review_Movie_LikeRepository.save(review_Movie_Like);
            return 1;
        }
        catch(Exception e){
            return 0;
        }
    }

   

    @Override
    public List<Review_Drama_Like>  dramaReviewLikeCnt(Long no) {
        try{
            return review_Drama_LikeRepository.findByReviewdrama_id(no);
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public List<Review_Movie_Like> movieReviewLikeCnt(Long no) {
        try{
            return review_Movie_LikeRepository.findByReviewmovie_id(no);
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public List<Review_Drama_Like> dramaReviewLikechk(Review_Drama reviewdrama, Member userid) {
        try{
            return review_Drama_LikeRepository.findByReviewdramaAndUserid(reviewdrama,userid);
        }
        catch(Exception e){
            return null;
        }


    }

    @Override
    public List<Review_Movie_Like> movieReviewLikechk(Review_Movie reviewmovie, Member userid) {
        try{
            return review_Movie_LikeRepository.findByReviewmovieAndUserid(reviewmovie,userid);
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public int deletereviewlikedrama(Review_Drama reviewdrama) {
        try{
            review_Drama_LikeRepository.deleteAllByReviewdrama(reviewdrama);
            return 1;
        }
        catch(Exception e){
            return 0;
        }
    }

    @Override
    public int deletereviewlikemovie(Review_Movie reviewmovie) {
        try{
            review_Movie_LikeRepository.deleteAllByReviewmovie(reviewmovie);
            return 1;
        }
        catch(Exception e){
            return 0;
        }
    }

    @Override
    public int insertreplyDrama(Review_Drama_Coment review_Drama_Coment) {
        try{
            review_Drama_ReplyRepository.save(review_Drama_Coment);
            return 1;
        }
        catch(Exception e){
            return 0;
        }
    }
    @Override
    public int insertreplyMovie(Review_Movie_Coment review_Movie_Coment) {
        try{
            review_Movie_ReplyRepository.save(review_Movie_Coment);
            return 1;
        }
        catch(Exception e){
            return 0;
        }
    }

    @Override
    public List<Drama_Review_Coment_Memberimage_View> dramaReviewReplySelect(Long no, Pageable pageable) {
        try{
            return drcmRepository.findByReviewdrama_idOrderByRegdateDesc(no, pageable);
        }
        catch(Exception e){
            return null;
        }
    }
    @Override
    public List<Movie_Review_Coment_Memberimage_View> movieReviewReplySelect(Long no, Pageable pageable) {
        try{
            return mrcmRepository.findByReviewmovie_idOrderByRegdateDesc(no, pageable);
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public long dramaReviewTotal(Long reviewid){
        try{
            return review_Drama_ReplyRepository.countByReviewdrama_id(reviewid);
        }
        catch(Exception e){
            return -1;
        }
    }
    @Override
    public long movieReviewTotal(Long reviewid){
        try{
            return review_Movie_ReplyRepository.countByReviewmovie_id(reviewid);
        }
        catch(Exception e){
            return -1;
        }
    }

    @Override
    public int deleteDramaReviewReply(Long no,Member member) {
        try{
            int ret = review_Drama_ReplyRepository.deleteByIdAndUserid(no,member);

            if(ret == 1){
                return 1;
            } 
            else{
                return 0;
            }
        }
        catch(Exception e){
            return -1;
        }
    }

    @Override
    public int deleteMovieReviewReply(Long no,Member member) {
        try{
            int ret = review_Movie_ReplyRepository.deleteByIdAndUserid(no,member);

            if(ret == 1){
                return 1;
            } 
            else{
                return 0;
            }
        }
        catch(Exception e){
            return -1;
        }
    }

    @Override
    public long DramaReviewTotal(Long no) {
        try{
            long total = review_DramaRepository.countByDramaContent_id(no);
            return total;
        }
        catch(Exception e){
            return 0;
        }
    }

    @Override
    public long MovieReviewTotal(Long no) {
        try{
            long total = review_MovieRepository.countByMovieContent_id(no);
            return total;
        }
        catch(Exception e){
            return 0;
        }
    }

    @Override
    public List<Movie_Review_View> selectOneReviewMovie(Long movieno, Pageable pageable) {
        try{
            return movie_Review_ViewRepository.findBymovieContent_idOrderByRegdateDesc(movieno,pageable);
        }
        catch(Exception e){
            return null;

        }
    }


    @Override
    public Review_Drama selectOneDramaReview(Long id) {
        try{
            return review_DramaRepository.findById(id).orElse(null);
        }
        catch(Exception e){
            return null;

        }
    }

    @Override
    public Review_Movie selectOneMovieReview(Long id) {
        try{
            return review_MovieRepository.findById(id).orElse(null);
        }
        catch(Exception e){
            return null;

        }
    }

    @Override
    public int deletereviewreplydrama(Review_Drama reviewdrama) {
        try{
            review_Drama_ReplyRepository.deleteAllByReviewdrama(reviewdrama);
            return 1;
        }
        catch(Exception e){
            return 0;
        }
    }

    @Override
    public int deletereviewreplymovie(Review_Movie reviewmovie) {
        try{
            review_Movie_ReplyRepository.deleteAllByReviewmovie(reviewmovie);
            return 1;
        }
        catch(Exception e){
            return 0;
        }
    }

    @Override
    public List<Review_Movie_Coment> movieReviewReply(Long no) {
        try{
            return review_Movie_ReplyRepository.findByReviewmovie_id(no);
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public List<Review_Drama_Coment> dramaReviewReply(Long no) {
        try{
            return review_Drama_ReplyRepository.findByReviewdrama_id(no);
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public long userDramaReviewTotal() {
        try{
            long total = review_DramaRepository.count();
            return total;            
        }
        catch(Exception e){
            return -1;
        }
    }

    @Override
    public long userMovieReviewTotal() {
        try{
            long total = review_MovieRepository.count();
            return total;            
        }
        catch(Exception e){
            return -1;
        }
    }

    @Override
    public int UpdateContent(ContentUpdatePage content) {
        try{
            cUpdateRepository.save(content);
            return 1;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Page<ContentUpdatePage> UpdateContentList(Pageable pageable) {
        return cUpdateRepository.findAllByOrderByRegdateDesc(pageable);
    }

    @Override
    public int deleteinfo(Long no) {
        try{
            cUpdateRepository.deleteById(no);
            return 1;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int confirmupdateD(ContentUpdatePage content) {
        try{
            DramaContent contentinfo = contentDRepository.findById(content.getContentcode()).orElse(null);
            contentinfo.setName(content.getName());
            contentinfo.setAdult(content.getAdult());
            contentinfo.setEpisode_run_time(content.getEpisode_run_time());
            contentinfo.setFirst_air_date(content.getFirst_air_date());
            contentinfo.setImdb_id(content.getImdb_id());
            contentinfo.setNumber_of_episodes(content.getNumber_of_episodes());
            contentinfo.setNumber_of_seasons(content.getNumber_of_seasons());
            contentinfo.setOverview(content.getOverview());
            contentinfo.setTrailer(content.getTrailer());
            contentinfo.setYoutubereview(content.getYoutubereview());

            if(content.getProvider() != null){
                for(int i=0; i<content.getProvider().split(",").length; i++){
                    Long ottcode = Long.valueOf(content.getProvider().split(",")[i]);
                    if(!oDramaRepository.existsByDramacontent_idAndProvcate_id(content.getContentcode(),ottcode)){
                        OTT_Drama OD = new OTT_Drama();
                        OTT_Cate OC = new OTT_Cate();
                        OC.setId(ottcode);
                        OD.setDramacontent(contentinfo);
                        OD.setProvcate(OC);
                        oDramaRepository.save(OD);
                    }
                }
            }

            if(content.getGenre() != null){
                for(int i=0; i<content.getGenre().split(",").length; i++){
                    Long genrecode = Long.valueOf(content.getGenre().split(",")[i]);
                    if(!gDramaRepository.existsByDramacontent_idAndGenrecate_id(content.getContentcode(),genrecode)){
                        Genre_Drama GD = new Genre_Drama();
                        Genre_Cate GC = new Genre_Cate();
                        GC.setId(genrecode);
                        GD.setDramacontent(contentinfo);
                        GD.setGenrecate(GC);
                        gDramaRepository.save(GD);
                    }
                }
            }
            
            contentDRepository.save(contentinfo);
            return 1;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int confirmupdateM(ContentUpdatePage content) {
        try{
            MovieContent contentinfo = contentMRepository.findById(content.getContentcode()).orElse(null);
            contentinfo.setAdult(content.getAdult());
            contentinfo.setTitle(content.getTitle());
            contentinfo.setRelease_date(content.getRelease_date());
            contentinfo.setRuntime(content.getRun_time());
            contentinfo.setImdb_id(content.getImdb_id());
            contentinfo.setOverview(content.getOverview());
            contentinfo.setTrailer(content.getTrailer());
            contentinfo.setYoutubereview(content.getYoutubereview());

            if(content.getProvider() != null){
                for(int i=0; i<content.getProvider().split(",").length; i++){
                    Long ottcode = Long.valueOf(content.getProvider().split(",")[i]);
                    if(!oMovieRepository.existsByMoviecontent_idAndProvcate_id(content.getContentcode(),ottcode)){
                        OTT_Movie OD = new OTT_Movie();
                        OTT_Cate OC = new OTT_Cate();
                        OC.setId(ottcode);
                        OD.setMoviecontent(contentinfo);
                        OD.setProvcate(OC);
                        oMovieRepository.save(OD);
                    }
                }
            }

            if(content.getGenre() != null){
                for(int i=0; i<content.getGenre().split(",").length; i++){
                    Long genrecode = Long.valueOf(content.getGenre().split(",")[i]);
                    if(!gMovieRepository.existsByMoviecontent_idAndGenrecate_id(content.getContentcode(),genrecode)){
                        Genre_Movie GM = new Genre_Movie();
                        Genre_Cate GC = new Genre_Cate();
                        GC.setId(genrecode);
                        GM.setMoviecontent(contentinfo);
                        GM.setGenrecate(GC);
                        gMovieRepository.save(GM);
                    }
                }
            }
            
            contentMRepository.save(contentinfo);
            return 1;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public ContentUpdatePage UpdateContentone(Long no) {
        return cUpdateRepository.findById(no).orElse(null);
    }

    @Override
    public List<Movie_Review_Likescnt_View> selectOneReviewMovieLike(Long movieno, PageRequest pageable) {
         try{
            return movie_Review_LikescntRepository.findByMoviecontent_id(movieno,pageable);
        }
        catch(Exception e){
            return null;

        }
    }

    @Override
    public List<Drama_Review_Likescnt_View> selectOneReviewDramaLike(Long dramano, PageRequest pageable) {
        try{
            return drama_Review_LikescntRepository.findByDramacontent_id(dramano,pageable);
        }
        catch(Exception e){
            return null;

        }
    }


}
