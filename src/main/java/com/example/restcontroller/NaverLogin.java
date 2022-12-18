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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ImageProjection;
import com.example.dto.Member;
import com.example.jwt.JwtUtil;
import com.example.service.MemberService;
import com.squareup.okhttp.*;

@RestController
@RequestMapping(value = "/naver")

public class NaverLogin {
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired MemberService mService;
    @Autowired AuthenticationManager authenticationManager;
    @Autowired JwtUtil jUtil;
    
	@GetMapping(value = "/oauth2.0")
	public Map<String, Object> oauth20(
			@RequestParam(name="code") String code,
			@RequestParam(name="state") String state){
		Map<String, Object> map = new HashMap<>();
		try {
            String id = "NorOMfFixWqv2LMj8T2X";
            String secret = "PY684GXakz";
	        String url ="https://nid.naver.com/oauth2.0/token?code="+code
	        		+"&state="+state+"&grant_type=authorization_code&client_id="+id
	        		+"&client_secret="+secret;
	                
	        // OkHttp 클라이언트 객체 생성
	        OkHttpClient client = new OkHttpClient();
	 
	        // GET 요청 객체 생성
	        Request.Builder builder = new Request.Builder().url(url).get();
	        builder.addHeader("X-Naver-Client-Id", id);
	        builder.addHeader("X-Naver-Client-Secret", secret);
	        
	        Request request = builder.build();
	 
	        Response response = client.newCall(request).execute();
	        if (response.isSuccessful()) {
	            // 응답 받아서 처리
	            ResponseBody body = response.body();
	            //body.close();
	            if (body != null) {
	            	String str = body.string();
                    JSONObject jobj = new JSONObject(str);
                    String tokenN = jobj.getString("access_token");

                    String url2 = "https://openapi.naver.com/v1/nid/me";
                    // GET 요청 객체 생성
	                Request.Builder builder2 = new Request.Builder().url(url2).get();
	                builder2.addHeader("Authorization", "Bearer " +tokenN);
	        
        	        Request request2 = builder2.build();
	 
	                Response response2 = client.newCall(request2).execute();
	                if (response2.isSuccessful()) {
	                    // 응답 받아서 처리
	                    ResponseBody body2 = response2.body();
	                    if (body != null) {
	            	        String str2 = body2.string();
	            	        body.close();
	            	
	                        JSONObject jobj2 = new JSONObject(str2);
                            JSONObject jobj3 = jobj2.getJSONObject("response");

                            Member member = new Member();
                            member.setUserpw(passwordEncoder.encode("null"));
                            member.setUserid(jobj3.getString("id"));
                            member.setBirth(jobj3.getString("birthyear"));
                            member.setNickname(jobj3.getString("nickname"));
                            member.setPhone(
                                jobj3.getString("mobile").split("-")[0]+
                                jobj3.getString("mobile").split("-")[1]+
                                jobj3.getString("mobile").split("-")[2]
                            );
                            member.setSocial("naver");

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
                                        map.put("userid", member2.getUserid());
                                        map.put("ban", member.getBan());
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
                                        map.put("userid", member2.getUserid());
                                        map.put("ban", member.getBan());
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
	                    }
	                }
                }
	        }
	        else {
	            System.err.println("Error Occurred");
	        }

	    } catch(Exception e) {
	    	map.put("status", -1);
	        e.printStackTrace();
	    }
        return map;
	}	
	
}