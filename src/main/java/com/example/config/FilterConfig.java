package com.example.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.jwt.JwtFilter;
import com.example.jwt.JwtFilterAdmin;

@Configuration
public class FilterConfig {
    
    @Bean
    public FilterRegistrationBean<JwtFilterAdmin>
    ADMINfilterRegistrationBean(JwtFilterAdmin jwtFilteradmin){

        FilterRegistrationBean<JwtFilterAdmin> bean = new FilterRegistrationBean<>();
        bean.setFilter(jwtFilteradmin);
        bean.addUrlPatterns("/member/admin/*","/content/admin/*");

        return bean;
    }

    @Bean
    public FilterRegistrationBean<JwtFilter>
    filterRegistrationBean(JwtFilter jwtFilter){

        FilterRegistrationBean<JwtFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(jwtFilter);
        bean.addUrlPatterns("/member/selectone.json",
                            "/member/update.json",
                            "/member/delete.json",
                            "/member/updatepw.json", 
                            "/member/insertimage.json", 
                            "/member/image.json",
                            "/member/deleteimage.json",
                            "/member/updateimage.json",
                            "/member/sessionreload.json",
                            "/center/QNAList.json",
                            "/content/movielaterview.json",
                            "/content/movielaterinsert.json",
                            "/content/movielaterlist.json",
                            "/content/dramalaterview.json",
                            "/content/dramalaterinsert.json",
                            "/content/dramalaterlist.json",
                            "/content/movielikeview.json",
                            "/content/movielikeinsert.json",
                            "/content/movielikelist.json",
                            "/content/dramalikeview.json",
                            "/content/dramalikeinsert.json",
                            "/content/dramalikelist.json",
                            "/content/updatetvreview.json",
                            "/content/updatemoviereview.json",
                            "/content/tvreview.json",
                            "/content/moviereview.json",
                            "/content/selectmydramareview.json",
                            "/content/selectmymoviereview.json",
                            "/content/mydramareviewchk.json",
                            "/content/mymoviereviewchk.json",
                            "/content/moviereviewlike.json",
                            "/content/dramareviewlike.json",
                            
                            "/content/dramareviewreply.json",
                            "/content/moviereviewreply.json",
                            "/content/deletedramareviewreply.json",
                            "/content/deletemoviereviewreply.json",
                            "/content/contentupdate.json",

                            "/contentlist/recommend.json",
                            "/contentlist/recommenddrama.json",
                            "/contentlist/recommendmovie.json",

                            "/people/likeview.json",
                            "/people/likeinsert.json",
                            "/people/likelist.json"
                            );

        return bean;
    }
}
