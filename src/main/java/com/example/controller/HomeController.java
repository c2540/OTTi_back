package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    // http://127.0.0.1:8080/TEST201/vue => resources/static/vue/index.html
    @GetMapping(value="/")
    public String vue(){
        return "forward:/vue/index.html";
    }
}
