package com.example.restcontroller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.dto.ContentDList;
import com.example.dto.ContentMList;
import com.example.dto.DramaContent;
import com.example.dto.Genre_Cate;
import com.example.dto.Genre_Drama;
import com.example.dto.Genre_Movie;
import com.example.dto.MovieContent;
import com.example.dto.OTT_Cate;
import com.example.dto.OTT_Drama;
import com.example.dto.OTT_Movie;
import com.example.repository.ContentDRepository;
import com.example.repository.ContentMRepository;
import com.example.repository.Genre_CateRepository;
import com.example.repository.Genre_DramaRepository;
import com.example.repository.Genre_MovieRepository;
import com.example.repository.OttDramaRepository;
import com.example.repository.OttMovieRepository;



@RestController
@RequestMapping(value = "/api")
public class ApiController {

// INSERT INTO OTT_CATE(ID, LOGO_PATH,PROVIDER_NAME)
// VALUES(8,'netflix.jpg','NETFLIX');
// INSERT INTO OTT_CATE(ID, LOGO_PATH,PROVIDER_NAME)
// VALUES(337,'disney+.jpg','DISNEY+');
// INSERT INTO OTT_CATE(ID, LOGO_PATH,PROVIDER_NAME)
// VALUES(350,'appleTV.jpg','APPLETV');
// INSERT INTO OTT_CATE(ID, LOGO_PATH,PROVIDER_NAME)
// VALUES(119,'primevideo.jpg','AMAZON');
// INSERT INTO OTT_CATE(ID, LOGO_PATH,PROVIDER_NAME)
// VALUES(356,'wavve.jpg','WAVVE');
// INSERT INTO OTT_CATE(ID, LOGO_PATH,PROVIDER_NAME)
// VALUES(97,'watcha.jpg','WATCHA');
// INSERT INTO OTT_CATE(ID, LOGO_PATH,PROVIDER_NAME)
// VALUES(3897,'Tving.jpg','TVING');

    @Autowired ContentDRepository cdRepository;
    @Autowired ContentMRepository cmRepository;
    @Autowired OttDramaRepository odRepository;
    @Autowired OttMovieRepository omRepository;
    @Autowired Genre_DramaRepository gdRepository;
    @Autowired Genre_MovieRepository gmRepository;
    @Autowired Genre_CateRepository genreRepository;
    
    // 사용안함
    // @GetMapping(
    //     value = "/test1.json"
    //     )
    // public String test1(
    //     @RequestParam(name="provider", defaultValue = "") String provider,
    //     @RequestParam(name="page", defaultValue = "1") int page,
    //     @RequestParam(name="sort", defaultValue = "vote_average.desc") String sort,
    //     @RequestParam(name="type", defaultValue = "movie") String type,
    //     @RequestParam(name="vote", defaultValue = "1000") Long vote
    // ){
    //     try{
    //         String prov = provider;
    //         int pageno = page;
    //         String sortby = sort;
    //         String typeTM = type;
    //         Long votec = vote;
    //         String api = "a97e8456c333e46c3010084e17ebe837";
    //         String apiURL = "https://api.themoviedb.org/3/discover/"+typeTM+"?api_key="+api+"&sort_by="+sortby+"&with_watch_providers="+prov+"&watch_region=KR&language=ko&page="+pageno+"&vote_count.gte="+votec;

    //         RestTemplate restTemplate = new RestTemplate();
    //         String result = restTemplate.getForObject(apiURL, String.class);

    //         return result;
    //     }
    //     catch(Exception e){
    //         return null;
    //     }
    // }

    //컨텐츠페이지 추천작품(api)
    @GetMapping(value = "/recommend.json")
    public String recommend(
        @RequestParam(name="type") String type,
        @RequestParam(name="no") Long no
    ){
        try{
            String typetm = type;
            Long no1 = no;
            String api = "a97e8456c333e46c3010084e17ebe837";
            String apiURL = "https://api.themoviedb.org/3/"+typetm+"/"+no1+"/recommendations?api_key="+api+"&language=ko-KR&page=1";

            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(apiURL, String.class);

            return result;
        }
        catch(Exception e){
            return null;
        }
    }

    // 메인화면 트랜딩(api)
    @GetMapping(value = "/trandingapi.json")
    public String tranding(){
        try{
            String api = "a97e8456c333e46c3010084e17ebe837";
            String apiURL = "https://api.themoviedb.org/3/trending/all/week?api_key="+api;

            RestTemplate restTemplate = new RestTemplate();
            String list = restTemplate.getForObject(apiURL, String.class);
            
            return list;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // 컨텐츠 1개 정보 상세(api)
    @GetMapping(
        value = "/contentinfo.json"
        )
    public String contentinfo(
        @RequestParam(name="type", defaultValue = "movie") String type,
        @RequestParam(name="id") Long no
    ){
        try{
            String api = "a97e8456c333e46c3010084e17ebe837";
            Long id = no;
            String typeTM = type;
            String apiURL = "https://api.themoviedb.org/3/"+typeTM+"/"+id+"?api_key="+api+"&language=ko";

            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(apiURL, String.class);


            return result;
        }
        catch(Exception e){
            return null;
        }
    }

    // 유튜브 트레일러 (api)
    @GetMapping(value = "/trailer.json")
    public String Trailer(
        @RequestParam(name="type", defaultValue = "movie") String type,
        @RequestParam(name="id") Long no
    ){
        try{
            String api = "a97e8456c333e46c3010084e17ebe837";
            Long id = no;
            String typeTM = type;
            String apiURL = "https://api.themoviedb.org/3/"+typeTM+"/"+id+"/videos?api_key="+api+"&language=KO";

            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(apiURL, String.class);


            return result;
        }
        catch(Exception e){
            return null;
        }
    }

    // ott정보(api)
    @GetMapping(value = "/ottinfo.json")
    public String ottinfo(
        @RequestParam(name="type") String type,
        @RequestParam(name="key") Long no
    ){
        try{
            String api = "a97e8456c333e46c3010084e17ebe837";
            Long id = no;
            String typeTM = type;
            String apiURL = "https://api.themoviedb.org/3/"+typeTM+"/"+id+"/watch/providers?api_key="+api;

            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(apiURL, String.class);


            return result;
        }
        catch(Exception e){
            return null;
        }
    }

    // api 검색(db사용으로 사용안함)
    @GetMapping(value = "/test7.json")
    public String test7(
        @RequestParam(name="type",defaultValue = "tv" ) String typev,
        @RequestParam(name="text") String query
    ){
        try{
            String api = "a97e8456c333e46c3010084e17ebe837";
            String text = "&query="+query;
            String type = typev;
            String apiURL = "https://api.themoviedb.org/3/search/"+type+"?api_key="+api+text;

            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(apiURL, String.class);

            return result;
        }
        catch(Exception e){
            return null;
        }
    }

    // =========================================DB저장용=================================================================
    @GetMapping(value = "/posttvdata.json")
    public Map<String,Object> posttvdata(){
            Map<String,Object> map = new HashMap<>();
        try{
            RestTemplate restTemplate = new RestTemplate();
            String apiURL = "https://api.themoviedb.org/3/discover/tv?api_key=a97e8456c333e46c3010084e17ebe837&with_watch_providers=&watch_region=KR&language=ko&vote_count.gte=1000";
            ContentDList list = restTemplate.getForObject(apiURL, ContentDList.class );

            if(list != null){
                for(int page=1; page<list.getTotal_pages();page++){
                    String apiURL1="https://api.themoviedb.org/3/discover/tv?api_key=a97e8456c333e46c3010084e17ebe837&with_watch_providers=&page="+page+"&watch_region=KR&language=ko&vote_count.gte=1000";
                    ContentDList list1 = restTemplate.getForObject(apiURL1, ContentDList.class );
                    if(list1 != null){
                        cdRepository.saveAll(list1.getResults());
                    }
                }
                cdRepository.saveAll(list.getResults());
            }
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }
    
    @GetMapping(value = "/posttvdata1.json")
    public Map<String,Object> posttvdata1(){
        Map<String,Object> map = new HashMap<>();
        try{
            RestTemplate restTemplate = new RestTemplate();
            String apiURL = "https://api.themoviedb.org/3/discover/tv?api_key=a97e8456c333e46c3010084e17ebe837&with_watch_providers=&watch_region=KR&language=ko&vote_count.gte=1000";
            ContentDList list = restTemplate.getForObject(apiURL, ContentDList.class );
            if(list != null){
                for(int page=1; page<list.getTotal_pages();page++){
                    String apiURL1="https://api.themoviedb.org/3/discover/tv?api_key=a97e8456c333e46c3010084e17ebe837&with_watch_providers=&page="+page+"&watch_region=KR&language=ko&vote_count.gte=1000";
                    ContentDList list1 = restTemplate.getForObject(apiURL1, ContentDList.class );
                    if(list1 != null){
                        cdRepository.saveAll(list1.getResults());
                    }
                }
                cdRepository.saveAll(list.getResults());
            }
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", 0);
        }

        return map;
    }

    @GetMapping(value = "/postmoviedata.json")
    public Map<String,Object> postmoviedata(){
        Map<String,Object> map = new HashMap<>();
        try{
            RestTemplate restTemplate = new RestTemplate();
            String apiURL = "https://api.themoviedb.org/3/discover/movie?api_key=a97e8456c333e46c3010084e17ebe837&with_watch_providers=&watch_region=KR&language=ko&vote_count.gte=8000";
            ContentMList list = restTemplate.getForObject(apiURL, ContentMList.class);
            if(list != null){
                for(int page=1; page<list.getTotal_pages();page++){
                    String apiURL1="https://api.themoviedb.org/3/discover/movie?api_key=a97e8456c333e46c3010084e17ebe837&with_watch_providers=&page="+page+"&watch_region=KR&language=ko&vote_count.gte=8000";
                    ContentMList list1 = restTemplate.getForObject(apiURL1, ContentMList.class);
                    if(list1 != null){
                        cmRepository.saveAll(list1.getResults());
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", 0);
        }

        return map;
    }

    // 드라마 OTT 카테고리 저장
    @PostMapping(value = "/dramaottpost.json")
    public Map<String,Object> post(
        @RequestParam(name = "no")Long no,
        @RequestBody OTT_Cate ott
    ){
        Map<String,Object> map = new HashMap<>();
        try{
            OTT_Drama obj = new OTT_Drama();
            DramaContent drama = new DramaContent();
            drama.setId(no);
            obj.setDramacontent(drama);
            obj.setProvcate(ott);

            List<OTT_Drama> list = odRepository.findByDramacontent_id(no);
            for(OTT_Drama OD : list){
                if(OD.getProvcate().getId().equals(ott.getId())){
                    map.put("result", "중복된 값");
                    return map;
                }
            }   
            odRepository.save(obj);
        }
        catch(Exception e){
        }
        return map;
    }

    // DB 드라마 전체 ID값추출
    @GetMapping(value = "/tet2.json")
    public Map<String,Object> post1(){
        Map<String,Object> map = new HashMap<>();
        try{
            List<DramaContent> content = cdRepository.findAll();
            List<Long> no = new ArrayList<>();
            for(DramaContent DC : content){
                no.add(DC.getId());
            }
            map.put("ID", no);
        }
        catch(Exception e){

        }

        return map;
    }

    // 영화 OTT 정보 저장
    @PostMapping(value = "/movieottpost.json")
    public Map<String,Object> movieottpost(
        @RequestParam(name = "no")Long no,
        @RequestBody OTT_Cate ott
    ){
        Map<String,Object> map = new HashMap<>();
        try{
            OTT_Movie obj = new OTT_Movie();
            MovieContent movie = new MovieContent();  
            movie.setId(no);
            obj.setMoviecontent(movie);
            obj.setProvcate(ott);
            
            List<OTT_Movie> list = omRepository.findByMoviecontent_id(no);
            for(OTT_Movie OM : list){
                if(OM.getProvcate().getId().equals(ott.getId())){
                    map.put("result", "중복된 값");
                    return map;
                }
            }
            omRepository.save(obj);
        }
        catch(Exception e){
        }
        return map;
    }

    // DB 전체 영화 ID값 추출
    @GetMapping(value = "/tetM2.json")
    public Map<String,Object> post13(){
        Map<String,Object> map = new HashMap<>();
        try{
            List<MovieContent> content = cmRepository.findAll();
            List<Long> no = new ArrayList<>();
            for(MovieContent DC : content){
                no.add(DC.getId());
            }
            map.put("ID", no);
        }
        catch(Exception e){
        }
        return map;
    }

    // genre_cate 저장
    @PostMapping(value="/genrespost.json")
    public Map<String,Object> postmoviedata(
        @RequestBody Genre_Cate genres
    ){

        Map<String,Object> map = new HashMap<>();
        genreRepository.save(genres);
        map.put("status", 200);
        return map;
    }

    // DB에 저장된 드라마코드 리스트 조회
    @GetMapping(value = "/dramaidget.json")
    public Map<String,Object> dramaIdGET(){
        Map<String,Object> map = new HashMap<>();
        List<Long> list1 = new ArrayList<>();
        List<DramaContent> list = cdRepository.findAll();
        for(DramaContent obj : list){
            list1.add(obj.getId());
        }
        map.put("id", list1);
        return map;
    }

    // DB에 저장된 드라마 장르 저장
    @PostMapping(value = "/dramagenrepost.json")
    public Map<String,Object> dramaGenrePost(
        @RequestParam(name="id") Long id,
        @RequestBody Genre_Cate genre_Cate
        ){
        Map<String,Object> map = new HashMap<>();
        Genre_Drama drama = new Genre_Drama();
        drama.setGenrecate(genre_Cate);
        DramaContent content = new DramaContent();
        content.setId(id);
        drama.setDramacontent(content);

        List<Genre_Drama> list = gdRepository.findByDramacontent_id(id);
        for(Genre_Drama GD : list){
            if(GD.getGenrecate().getId().equals(genre_Cate.getId())){
                map.put("result", "중복된 값");
                return map;
            }
        }
        gdRepository.save(drama);
        return map;
    }

    // DB에 저장된 드라마 상세정보 저장
    @PostMapping(value = "/dramainfopost.json")
    public Map<String,Object> dramaInfoPost(
        @RequestParam(name="id") Long id,
        @RequestBody DramaContent drama
    ){  
        Map<String,Object> map = new HashMap<>();
        DramaContent dc = cdRepository.findById(id).orElse(null);
            dc.setAdult(drama.getAdult());
            dc.setImdb_id(drama.getImdb_id());
            dc.setEpisode_run_time(drama.getEpisode_run_time());
            dc.setNumber_of_episodes(drama.getNumber_of_episodes());
            dc.setNumber_of_seasons(drama.getNumber_of_seasons());
            dc.setTrailer(drama.getTrailer());
        
        cdRepository.save(dc);
        return map;
    }

    // DB 영화 상세정보 저장
    @PostMapping(value = "/movieinfopost.json")
    public Map<String,Object> movieInfoPost(
        @RequestParam(name="id") Long id,
        @RequestBody MovieContent movie
    ){  
        Map<String,Object> map = new HashMap<>();
        MovieContent mc = cmRepository.findById(id).orElse(null);
            mc.setImdb_id(movie.getImdb_id());
            mc.setRuntime(movie.getRuntime());
            mc.setTrailer(movie.getTrailer());
        
        cmRepository.save(mc);
        return map;
    }

    // DB에 저장된 영화 id 조회
    @GetMapping(value = "/movieidget.json")
    public Map<String,Object> movieIdGET(){
        Map<String,Object> map = new HashMap<>();
        List<Long> list1 = new ArrayList<>();
        List<MovieContent> list = cmRepository.findAll();
        for(MovieContent obj : list){
            list1.add(obj.getId());
        }
        map.put("id", list1);
        return map;
    }

    // DB에 저장된 영화 장르 저장
    @PostMapping(value = "/moviegenrepost.json")
    public Map<String,Object> movieGenrePost(
        @RequestParam(name="id") Long id,
        @RequestBody Genre_Cate genre_Cate
        ){
        Map<String,Object> map = new HashMap<>();
        Genre_Movie movie = new Genre_Movie();
        movie.setGenrecate(genre_Cate);
        MovieContent content = new MovieContent();
        content.setId(id);
        movie.setMoviecontent(content);
        List<Genre_Movie> list = gmRepository.findByMoviecontent_id(id);
        for(Genre_Movie GM : list){
            if(GM.getGenrecate().getId().equals(genre_Cate.getId())){
                map.put("result", "중복된 값");
                return map;
            }
        }
        gmRepository.save(movie);
        
        return map;
    }

    // 드라마 1개 DB 추가
    @PostMapping(value = "/postoneinfoD.json")
    public Map<String,Object> postoneinfo(
        @RequestBody DramaContent content
    ){
        Map<String,Object> map = new HashMap<>();

        DramaContent ret = cdRepository.save(content);
        
        if(ret != null){
            map.put("status", 200);
        }
        else{
            map.put("status", 0);
        }

        return map;
    }

    // 영화 1개 DB 추가
    @PostMapping(value = "/postoneinfoM.json")
    public Map<String,Object> postoneinfoM(
        @RequestBody MovieContent content
    ){
        Map<String,Object> map = new HashMap<>();

        MovieContent ret = cmRepository.save(content);

        if(ret != null){
            map.put("status", 200);
        }
        else{
            map.put("status", 0);
        }
        return map;
    }

    // 컨텐츠 참여인물 조회
    @GetMapping(value = "/credit.json")
    public String CreditGET(
        @RequestParam(name="type", defaultValue = "movie") String type,
        @RequestParam(name="key") Long no
    ){
        try{
            String api = "a97e8456c333e46c3010084e17ebe837";
            Long id = no;
            String typeTM = type;
            String apiURL = "https://api.themoviedb.org/3/"+typeTM+"/"+id+"/credits?api_key="+api+"&language=KO";

            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(apiURL, String.class);


            return result;
        }
        catch(Exception e){
            return null;
        }
    }

    // 인물정보 조회
    @GetMapping(value = "/peopleinfo.json")
    public String peopleinfoGET(
        @RequestParam(name="key") Long no
    ){
        try{
            Long id = no;
            String apiURL = "https://api.themoviedb.org/3/person/"+id+"?api_key=a97e8456c333e46c3010084e17ebe837&language=ko-KR";
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(apiURL, String.class);
            return result;
        }
        catch(Exception e){
            return null;
        }
    }
    
    // 인물 SNS 조회
    @GetMapping(value = "/peoplesns.json")
    public String peoplesnsGET(
        @RequestParam(name="key") Long no
    ){
        try{
            Long id = no;
            String apiURL = "https://api.themoviedb.org/3/person/"+id+"/external_ids?api_key=a97e8456c333e46c3010084e17ebe837&language=ko-KR";
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(apiURL, String.class);
            return result;
        }
        catch(Exception e){
            return null;
        }
    }
    
    // 인물 참여작품 조회
    @GetMapping(value ="/peoplecontent.json")
    public String peoplesnsGET(
        @RequestParam(name="key") Long no,
        @RequestParam(name="type") String type
    ){
        try{
            String apiURL = "https://api.themoviedb.org/3/person/"+no+"/"+type+"_credits?api_key=a97e8456c333e46c3010084e17ebe837&language=ko-KR";
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(apiURL, String.class);
            return result;
        }
        catch(Exception e){
            return null;
        }
    }

    // 인물 리스트 조회(인기순)
    @GetMapping(value ="/peoplelist.json")
    public String peoplelistGET(
        @RequestParam(name="page") Long page
    ){
        try{
            String apiURL = "https://api.themoviedb.org/3/person/popular?api_key=a97e8456c333e46c3010084e17ebe837&page="+page+"&language=ko-KR";
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(apiURL, String.class);
            return result;
        }
        catch(Exception e){
            return null;
        }
    }

    // 인물 검색
    @GetMapping(value="/peoplesearch.json")
    public String peopleSearchGET(
        @RequestParam(name="page") Long page,
        @RequestParam(name="text") String text
    ){
        try{
            String apiURL = "https://api.themoviedb.org/3/search/person?api_key=a97e8456c333e46c3010084e17ebe837&language=ko-KR&query="+text+"&page="+page+"&include_adult=false";
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(apiURL, String.class);
            return result;
        }
        catch(Exception e){
            return null;
        }
    }
    
}
