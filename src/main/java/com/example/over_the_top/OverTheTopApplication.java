package com.example.over_the_top;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@PropertySource("classpath:/global.properties")

@ComponentScan(basePackages = {
	"com.example.service",
	"com.example.config",
	"com.example.jwt",
	"com.example.restcontroller",
	"com.example.controller"
})

@EntityScan(basePackages = {"com.example.dto"})

@MapperScan(basePackages = "com.example.mapper")

@EnableJpaRepositories(basePackages = "com.example.repository")

@SpringBootApplication
public class OverTheTopApplication 
					extends SpringBootServletInitializer {

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(OverTheTopApplication.class);
    }

    public static void main(String[] args) {
		SpringApplication.run(OverTheTopApplication.class, args);
	}

}
