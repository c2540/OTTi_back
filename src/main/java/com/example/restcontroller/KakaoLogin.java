package com.example.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ImageProjection;
import com.example.dto.Member;
import com.example.jwt.JwtUtil;
import com.example.service.MemberService;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

@RestController
@RequestMapping(value = "/kakao")

public class KakaoLogin {
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired MemberService mService;
    @Autowired AuthenticationManager authenticationManager;
    @Autowired JwtUtil jUtil;
    
	@GetMapping(value = "/oauth")
	public Map<String, Object> oauth(
			@RequestParam(name="code") String code,
            @RequestParam(name="re") String re){
		Map<String, Object> map = new HashMap<>();
		try {
            String id = "0fd79a0944e5a65300e5fdd8b4c193db";
	        String url ="https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id="+id+"&redirect_uri="+re+"&code="+code;
	        
            // OkHttp 클라이언트 객체 생성
	        OkHttpClient client = new OkHttpClient();

	        Request.Builder builder = new Request.Builder().url(url).get();
	        builder.addHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

	        Request request = builder.build();
	 
	        Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            String str = body.string();
            JSONObject jobj = new JSONObject(str);
            String token = jobj.getString("access_token");
            if(response.code() == 200){
                map.put("result", token);
            }

	    } catch(Exception e) {
	    	map.put("status", -1);
	        e.printStackTrace();
	    }
        return map;
	}	

	@GetMapping(value = "/v2")
	public Map<String, Object> v2(
			@RequestParam(name="access_token") String actoken,
            @RequestParam(name="app_admin_key") String adkey){
		Map<String, Object> map = new HashMap<>();
		try {
            System.out.println(actoken);
	        String url ="https://kapi.kakao.com/v2/user/me?access_token="+actoken+"&app_admin_key="+adkey;
	        
            // OkHttp 클라이언트 객체 생성
	        OkHttpClient client = new OkHttpClient();

	        Request.Builder builder = new Request.Builder().url(url).get();
	        builder.addHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

	        Request request = builder.build();
	 
	        Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            String str = body.string();
            System.out.println(str);
            JSONObject jobj = new JSONObject(str);
            Long id = jobj.getLong("id");
            JSONObject properties = jobj.getJSONObject("properties");
            String nickname = properties.getString("nickname");
            map.put("id", id);
            map.put("nickname", nickname);

	    } catch(Exception e) {
	    	map.put("status", -1);
	        e.printStackTrace();
	    }
        return map;
    }

    // 카카오 로그인
    @PostMapping(value = "/login.json")
    public Map<String,Object> kakaologin(
        @RequestBody Member member
    ){
        Map<String,Object> map = new HashMap<>();
        member.setUserpw(passwordEncoder.encode("null"));
        boolean check = mService.existsById(member.getUserid());
        if(check == false){
            int ret = mService.joinuser(member);
            if(ret == 1){
                Member member2 = mService.find(member.getUserid());
                if(member2.getBan() == 1){
                    map.put("status", 200);
                    map.put("result","banned user");
                }
                else{
                    member.setUserpw("null");
                    UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(member.getUserid(), member.getUserpw());

                    List<ImageProjection> list = mService.findByUserid_userid(member2.getUserid());
                    List<Long> no = new ArrayList<>();
                    for(ImageProjection image : list){
                        no.add(image.getNo());
                    }

                    authenticationManager.authenticate(token);
                    
                    map.put("status", 200);
                    map.put("result", jUtil.generateToken(member.getUserid()));
                    map.put("role", jUtil.extractRole(jUtil.generateToken(member.getUserid())));
                    map.put("ban", member.getBan());
                    map.put("userid", member2.getUserid());
                    map.put("nickname", member2.getNickname());
                    map.put("social", member2.getSocial());
                    map.put("imageno", no);
                }
            }
            else{
                map.put("status", 0);
            }
        }
        else{
            try{
                Member member2 = mService.find(member.getUserid());

                if(member2.getBan() == 1){
                    map.put("status", 200);
                    map.put("result","banned user");
                }
                else{
                    member.setUserpw("null");
                    UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(member.getUserid(), member.getUserpw());

                    List<ImageProjection> list = mService.findByUserid_userid(member2.getUserid());
                    List<Long> no = new ArrayList<>();
                    for(ImageProjection image : list){
                        no.add(image.getNo());
                    }

                    authenticationManager.authenticate(token);
                    
                    map.put("status", 200);
                    map.put("result", jUtil.generateToken(member.getUserid()));
                    map.put("role", jUtil.extractRole(jUtil.generateToken(member.getUserid())));
                    map.put("ban", member.getBan());
                    map.put("userid", member2.getUserid());
                    map.put("nickname", member2.getNickname());
                    map.put("social", member2.getSocial());
                    map.put("imageno", no);
                }
            }
            catch(Exception e){
                e.printStackTrace();
                map.put("status", -1);
            }
        }
        return map;
    }

}