package com.example.restcontroller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.dto.Drama_Like;
import com.example.dto.ImageProjection;
import com.example.dto.Member;
import com.example.dto.MemberImage;
import com.example.dto.Member_MemberImage_View;
import com.example.dto.Movie_Like;
import com.example.dto.Password;
import com.example.dto.People_Like;
import com.example.dto.Review_Drama;
import com.example.dto.Review_Movie;
import com.example.jwt.JwtUtil;
import com.example.service.ContentService;
import com.example.service.MemberService;
import com.example.service.PeopleService;


@RestController
@RequestMapping(value = "/member")
public class MemberController {
    @Autowired MemberService mService;
    @Autowired PeopleService pService;
    @Autowired ContentService contentService;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired JwtUtil jUtil;
    @Autowired AuthenticationManager authenticationManager;
    @Autowired ResourceLoader resourceLoader;
    @Value("${default.image}") String defaultImage;

    // ========================== 회원가입 ==============================

    // 닉네임 중복검사
    @GetMapping(value="/nickcheck.json")
    public Map<String,Object> nickcheckGET(@RequestParam(name="nickname")String nickname) {
        Map<String,Object> map = new HashMap<>();
        try {
            int ret = mService.existsBynick(nickname);
            if(ret == 1){
                map.put("status", 0);
                map.put("result", "중복된 닉네임");
            }
            else{
                map.put("status",200);
                map.put("result","사용가능 닉네임"); 
            }
        } catch (Exception e) {
             map.put("status",-1);
             map.put("result",e.getMessage());
        }
        return map;
    }

    // 아이디 중복검사
    @GetMapping(value="/idcheck.json")
    public Map<String,Object> idcheckGET(@RequestParam(name="id")String id) {
        Map<String,Object> map = new HashMap<>();
        try {
            boolean ret = mService.existsById(id);
            map.put("status",200);
            map.put("result",ret); //있으면 참, 없으면 거짓

        } catch (Exception e) {
             map.put("status",-1);
             map.put("result",e.getMessage());
        }
        return map;
    }

    // 휴대폰번호 중복검사
    @GetMapping(value="/phonecheck.json")
    public Map<String,Object> phonecheckGET(@RequestParam(name="phone")String phone) {
        Map<String,Object> map = new HashMap<>();
        try {
            int ret = mService.existsByphone(phone.replaceAll("-", ""),"normal");
            if(ret == 0){
                map.put("status",200);
                map.put("result",ret); 
            }
            else if(ret == 1){
                map.put("status", 0);
                map.put("result", "중복된 연락처");
            }
        } catch (Exception e) {
             map.put("status",-1);
             map.put("result",e.getMessage());
        }
        return map;
    }

    // 회원가입
    @PostMapping(value = "/join.json")
    public Map<String,Object> join(
        @RequestBody Member member) {

            Map<String,Object> map = new HashMap<>();
            int check = mService.check(member.getUserid());
            if(check == 1){
                map.put("status", 0);
                map.put("result", "중복된 아이디");
            }
            else{
                String hashpw = passwordEncoder.encode(member.getUserpw());

                    member.setUserpw(hashpw);
                    int ret = mService.joinuser(member);
                    if(ret == 1){
                        map.put("status", 200);
                        map.put("result", "가입완료");
                }
            }
        return map;
    }

    // ====================================== 로그인 =========================================

    // 일반 로그인
    @PostMapping(value = "/login.json")
    public Map<String,Object> loginPost(
        @RequestBody Member member
        ){
        Map<String,Object> map = new HashMap<>();
        try{
            Member member2 = mService.find(member.getUserid());
            if(member2.getBan() == 1){
                map.put("status", 200);
                map.put("result","banned user");
            }
            else{
                UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(member.getUserid(), member.getUserpw());
                authenticationManager.authenticate(token);

                List<ImageProjection> list = mService.findByUserid_userid(member2.getUserid());
                List<Long> no = new ArrayList<>();
                for(ImageProjection image : list){
                    no.add(image.getNo());
                }

                map.put("status", 200);
                map.put("result", jUtil.generateToken(member.getUserid()));
                map.put("role", jUtil.extractRole(jUtil.generateToken(member.getUserid())));
                map.put("userid", member2.getUserid());
                map.put("ban", member.getBan());
                map.put("social", member2.getSocial());
                map.put("nickname", member2.getNickname());
                map.put("imageno", no);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // role 조회
    @GetMapping(value = "/role.json")
    public Map<String,Object> role(
        @RequestHeader(name = "TOKEN")String token
    ){
        Map<String,Object> map = new HashMap<>();
        String role = jUtil.extractRole(token);
        map.put("ROLE", role);
        map.put("status", 200);

        return map;
    }

    // 아이디 찾기
    @PostMapping(value="/findid.json")
    public Map<String,Object> findidPost(
        @RequestBody Member member
    ){

        Map<String,Object> map = new HashMap<>();
        Member retMember = mService.findid(member.getPhone(), member.getBirth(), member.getSocial());

        try{
            if(member.getBirth().equals(retMember.getBirth())){
                map.put("status", 200);
                map.put("result", retMember.getUserid());
            }
            else{
                map.put("status", 0);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 비밀번호 찾기/입력정보 검증
    @PostMapping(value="/findpw.json")
    public Map<String,Object> findPwPost(
        @RequestBody Member member
    ){
        Map<String,Object> map = new HashMap<>();
        Member retMember = mService.find(member.getUserid());

        try{
            if(member.getBirth().equals(retMember.getBirth()) 
                && member.getPhone().equals(retMember.getPhone())
                && member.getSocial().equals(retMember.getSocial())
            ){
                map.put("status", 200);
            }
            else{
                map.put("status", 0);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 비밀번호 찾기/비밀번호 변경
    @PutMapping(value="/findpw2.json")
    public Map<String,Object> findPw2PUT(
        @RequestBody Member member
    ){
        Map<String,Object> map = new HashMap<>();
        Member retMember = mService.find(member.getUserid());
        try{
            retMember.setUserpw(passwordEncoder.encode(member.getUserpw()));
            int ret = mService.updatepw(retMember);
            map.put("status", 200);
            map.put("result", ret);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }



    // =============================마이페이지===============================
   
    // 마이페이지/프로필 이미지 관리

    // 프로필 이미지 등록 (등록된 프로필 이미지 없을때)
    @PostMapping(value = "/insertimage.json")
    public Map<String,Object> insertimage(
        @ModelAttribute MemberImage memberImage,
        HttpServletRequest request,
        @RequestParam(name="file",required = false) MultipartFile file
        ) throws IOException{
            Map<String,Object> retMap = new HashMap<>();
            try {
                String userid = (String)request.getAttribute("username");
                Member member = mService.selectOne(userid);

                memberImage.setUserid(member);
                memberImage.setImagedata(file.getBytes());
                memberImage.setImagesize(file.getSize());
                memberImage.setImagename(file.getOriginalFilename());
                memberImage.setImagetype(file.getContentType());

                int ret =mService.insertimage(memberImage);

                retMap.put("status", 200);
                retMap.put("result", ret);

            } catch (Exception e) {
                e.printStackTrace();
                retMap.put("status", -1);
            } 
        return retMap;
    }

    // 프로필 이미지 삭제
    @DeleteMapping(value = "/image")
    public Map<String,Object> deleteimage(
        @RequestParam(name="no") Long no) {
            Map<String,Object> map = new HashMap<>();
            try {
                int ret = mService.deleteimageOne(no);
                map.put("status", 200); 
                map.put("result", ret); 

            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", -1);
            }
            return map;
    }

    // 프로필 이미지 변경 (등록된 프로필 이미지 있을때)
    @PostMapping(value="/updateimage.json")
    public Map<String,Object> imagePUT(
        @ModelAttribute MemberImage memberImage,
        @RequestParam(name="imageno") Long[] no,
        HttpServletRequest request,
        @RequestParam(name="file",required = false) MultipartFile file
    ){ Map<String,Object> retMap = new HashMap<>();
        try {
            String userid = (String)request.getAttribute("username");
                Member member = mService.selectOne(userid);

                MemberImage memberImage2 = mService.findimage(no[0]);
               
                memberImage2.setUserid(member);
           

                memberImage2.setImagedata(file.getBytes());
                memberImage2.setImagesize(file.getSize());
                memberImage2.setImagename(file.getOriginalFilename());
                memberImage2.setImagetype(file.getContentType());

                int ret =mService.insertimage(memberImage2);

                retMap.put("status", 200);
                retMap.put("result", ret);

        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
        }
          
        return retMap;
    }

    // 프로필 이미지 번호 조회
    @GetMapping(value="/image.json")
    public Map<String,Object> imageGET(
        HttpServletRequest request
    ){ Map<String,Object> retMap = new HashMap<>();

        String userid = (String)request.getAttribute("username");


        List<ImageProjection> list = mService.findByUserid_userid(userid);
        
        List<Long> no = new ArrayList<>();
        for(ImageProjection image : list){
            no.add(image.getNo());
        }

        retMap.put("result", no);   
        retMap.put("status", 200); 
        return retMap;
    }

    // 프로필 이미지 조회
    // 127.0.0.1:8080/BOOT1/member/image?no=이미지번호
    // th:src="@{/image(no=${obj.no})}"
    @GetMapping(value="/image")
    public ResponseEntity<byte[]> imageGET(
        @RequestParam(name="no") Long no
        ) throws IOException{
        
            if(no > 0L) {           
               MemberImage item = mService.findimage(no);

                if(item.getImagesize() > 0L) {
                    // 타입설정 png인지 jpg인지 gif인지
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(
                        MediaType.parseMediaType( item.getImagetype() ) );
                    // 실제이미지데이터, 타입이포함된 header, status 200    
                    ResponseEntity<byte[]> response 
                        = new ResponseEntity<>(
                            item.getImagedata(), headers, HttpStatus.OK);
                    return response;        
                }
                else {
                    InputStream is = resourceLoader.getResource(defaultImage)
                        .getInputStream();
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.IMAGE_JPEG);
                    // 실제이미지데이터, 타입이포함된 header, status 200    
                    ResponseEntity<byte[]> response 
                        = new ResponseEntity<>(
                            is.readAllBytes(), headers, HttpStatus.OK);
                    return response;
                }
            }
            else {
                InputStream is = resourceLoader.getResource(defaultImage)
                        .getInputStream();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);
                // 실제이미지데이터, 타입이포함된 header, status 200    
                ResponseEntity<byte[]> response 
                    = new ResponseEntity<>(
                        is.readAllBytes(), headers, HttpStatus.OK);
                return response;
            }
        }

    // 마이페이지/비밀번호 변경
    @PutMapping(value="/updatepw.json")
    public Map<String, Object> updatePUT(
        HttpServletRequest request,
        @RequestBody Password password) {
        Map<String, Object> retMap = new HashMap<>();
        try {
            String userid = (String)request.getAttribute("username");
            Member member = mService.selectOne(userid);

            if(passwordEncoder.matches(password.getPassword(),member.getUserpw())){  //앞에게 원본
                member.setUserpw(passwordEncoder.encode(password.getNewpassword()));
                int ret = mService.updatepw(member);
                retMap.put("status", 200);
                retMap.put("result", ret);
            }   

        }
        catch(Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
        }
        return retMap;  
    }

    // 마이페이지/회원탈퇴
    @DeleteMapping(value = "/delete.json")
    public Map<String,Object> delete(
        @RequestBody Member member,
        @RequestHeader(name = "TOKEN") String token) {
            Map<String,Object> map = new HashMap<>();
            try {
                String userid = jUtil.extractUsername(token);
                member.setUserid(userid);

                Member member1 = mService.selectOne(userid);
                mService.deleteImage(userid);

                if(passwordEncoder.matches(member.getUserpw(), member1.getUserpw())){
                    Member member2 = mService.selectOne(member.getUserid());
                    member2.setDelete(1L);
                    member2.setBirth(null);
                    member2.setNickname(null);
                    member2.setPhone(null);
                    member2.setRole(null);
                    member2.setUserpw(null);
                    member2.setRegdate(null);

                    mService.joinuser(member2);
                    map.put("result", 1);
                }
                map.put("status", 200);
    
            }
            catch(Exception e) {
                e.printStackTrace();
                map.put("status", -1);
            }
            return map;  
        }
    
    // 마이페이지/회원정보 수정

    // 회원정보 불러오기
    @GetMapping(value="/selectone.json")
    public Map<String, Object> selectoneGET(
                HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        try {
            // 여기서 아이디 정보 꺼냄
            String username 
                = (String) request.getAttribute("username");

            Member member 
                = mService.selectOne(username);

            map.put("status", 200);
            map.put("result", member);
        }
        catch(Exception e) {
            map.put("status", -1);
            map.put("result", e.getMessage());
        }
        return map;
    }

    // 회원정보 변경
    @PutMapping(value="/update.json")
    public Map<String, Object> updatePUT(
                HttpServletRequest request,
                @RequestBody Member member){
        Map<String, Object> map = new HashMap<>();
        try {
            String username = (String) request.getAttribute("username");

            // 기존정보 읽기
            Member member2 = mService.find(username);

            // 변경항목 객체에 추가
            member2.setPhone(member.getPhone());
            member2.setBirth(member.getBirth());
            member2.setNickname(member.getNickname());
            
            // 다시 저장하기
            mService.joinuser(member2);

            map.put("status", 200);
            map.put("result", member2);
        }
        catch(Exception e) {
            map.put("status", -1);
            map.put("result", e.getMessage());
        }
        return map;
    }

    // ============================= 관리자 페이지 ===================================
    
    // 관리자용 회원목록 조회
    @GetMapping(value = "/admin/list.json")
    public Map<String, Object> list(@RequestParam(name="page") int page){
        int size = 15;
        Pageable pageable = PageRequest.of(page-1, size);
        Map<String,Object> map = new HashMap<>();
        Page<Member> users = mService.userlist(pageable);
        map.put("result", users);
        return map;
    }

    // MEMBER -> ADMIN 승급
    @PostMapping(value = "/admin/makeadmin.json")
    public Map<String,Object> makeadmin(
        HttpServletRequest request,
        @RequestBody Member userid
    ){
        Map<String,Object> map = new HashMap<>();
        if(request.getAttribute("role").equals("ADMIN")){
            Member member = mService.find(userid.getUserid());
            member.setRole("ADMIN");
            mService.joinuser(member);

            map.put("status", 200);
        }
        else{
            map.put("status", -1);
        }
        return map;
    }

    // ADMIN -> MEMBER 강등
    @PostMapping(value = "/admin/removeadmin.json")
    public Map<String,Object> removeadmin(
        HttpServletRequest request,
        @RequestBody Member memberid
    ){
        Map<String,Object> map = new HashMap<>();
    
        if(request.getAttribute("role").equals("ADMIN")){
            Member member = mService.find(memberid.getUserid());
            member.setRole("MEMBER");
            mService.joinuser(member);

            map.put("status", 200);
        }
        else{
            map.put("status", -1);
        }
        return map;
    }

    // 회원 정지
    @PostMapping(value = "/admin/memberBan.json")
    public Map<String,Object> memberBan(
        HttpServletRequest request,
        @RequestBody Member userid
    ){
        Map<String,Object> map = new HashMap<>();
    
        if(request.getAttribute("role").equals("ADMIN")){
            Member member = mService.find(userid.getUserid());
            member.setBan(1L);;
            mService.joinuser(member);

            map.put("status", 200);
        }
        else{
            map.put("status", -1);
        }
        return map;
    }

    // 회원 정지 해제
    @PostMapping(value = "/admin/memberunBan.json")
    public Map<String,Object> memberUnBan(
        HttpServletRequest request,
        @RequestBody Member userid
    ){
        Map<String,Object> map = new HashMap<>();
    
        if(request.getAttribute("role").equals("ADMIN")){
            Member member = mService.find(userid.getUserid());
            member.setBan(0L);
            mService.joinuser(member);

            map.put("status", 200);
        }
        else{
            map.put("status", -1);
        }
        return map;
    }

    // 멤버 프로필 조회
    @GetMapping(value="/userprofile.json")
    public Map<String,Object> memberUnBan(
        @RequestParam(name="id") String id
    ){
        Map<String,Object> map = new HashMap<>();
        try{
            Member_MemberImage_View member = mService.profileFindByUserid(id);
            if(member != null){
                map.put("status", 200);
                map.put("result", member);
            }
            else{
                map.put("status", 0);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 프로필용 유저 드라마 리뷰 최신순 조회
    @GetMapping(value = "/dramareview.json")
    public Map<String,Object> userreviewtv(
        @RequestParam(name="page") int page,
        @RequestParam(name="id") String username
    ) {
        Map<String,Object> map = new HashMap<>();
        try {
            int size = 10;
            Pageable pageable = PageRequest.of(page-1, size);

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
    // 프로필용 유저 드라마 리뷰 평점높은순 조회
    @GetMapping(value = "/dramareviewscore.json")
    public Map<String,Object> userreviewscoretv(
        @RequestParam(name="page") int page,
        @RequestParam(name="id") String username
    ) {
        Map<String,Object> map = new HashMap<>();
        try {
            int size = 10;
            Pageable pageable = PageRequest.of(page-1, size);

            long total = contentService.userDramaReviewTotal(username);
            List<Review_Drama> list = contentService.selectReviewDramaOrderByScoreDESC(username,pageable);

            map.put("status", 200);
            map.put("result", list);
            map.put("pages", (total-1)/10+1);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }

    // 프로필용 유저 영화 리뷰 최신순 조회
    @GetMapping(value = "/moviereview.json")
    public Map<String,Object> userreviewmovie(
        @RequestParam(name="page") int page,
        @RequestParam(name="id") String username
    ) {
        Map<String,Object> map = new HashMap<>();
        try {
            int size = 10;
            Pageable pageable = PageRequest.of(page-1, size);

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
    
    // 프로필용 유저 영화 리뷰 평점높은순 조회
    @GetMapping(value = "/moviereviewscore.json")
    public Map<String,Object> userreviewmoviescore(
        @RequestParam(name="page") int page,
        @RequestParam(name="id") String username
    ) {
        Map<String,Object> map = new HashMap<>();
        try {
            int size = 10;
            Pageable pageable = PageRequest.of(page-1, size);

            long total = contentService.userMovieReviewTotal(username);

            List<Review_Movie> list = contentService.selectReviewMovieOrderByScoreDESC(username,pageable);     

            map.put("status", 200);
            map.put("result", list);
            map.put("pages", (total-1)/10+1);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }
    // 프로필용 유저 좋아요한 드라마 목록 조회
    @GetMapping(value = "/dramalikelist.json")
    public Map<String,Object> dramaLikeListGET(@RequestParam(name="id") String username, @RequestParam(name="page") int page){
        Map<String, Object> map = new HashMap<>();
        try{
            int size = 5;
            Pageable pageable = PageRequest.of(page-1, size);
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

    // 프로필용 유저 좋아요한 영화 목록 조회
    @GetMapping(value = "/movielikelist.json")
    public Map<String,Object> movieLikeListGET(
            @RequestParam(name="id") String username,
            @RequestParam(name="page") int page
        ){
        Map<String, Object> map = new HashMap<>();
        try{
            int size = 5;
            Pageable pageable = PageRequest.of(page-1, size);
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

    // 프로필용 유저 좋아요한 인물 목록 조회
    @GetMapping(value = "/peoplelikelist.json")
    public Map<String,Object> peopleLikeListGET(@RequestParam(name="id") String username, @RequestParam(name="page") int page){
        Map<String, Object> map = new HashMap<>();
        try{
            int size = 5;
            Pageable pageable = PageRequest.of(page-1, size);
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

    // 프로필사진/정보변경 갱신용
    @GetMapping(value = "/sessionreload.json")
    public Map<String,Object> sessionreload(
        HttpServletRequest request
        ){
        Map<String,Object> map = new HashMap<>();
        try{
            String username = (String) request.getAttribute("username");
            Member member = mService.find(username);

            List<ImageProjection> list = mService.findByUserid_userid(member.getUserid());
            List<Long> no = new ArrayList<>();
            for(ImageProjection image : list){
                no.add(image.getNo());
            }

            map.put("status", 200);
            map.put("nickname", member.getNickname());
            map.put("imageno", no);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;
    }
    
}
