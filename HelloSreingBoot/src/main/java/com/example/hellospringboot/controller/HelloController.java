package com.example.hellospringboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping("/sayhi")
    public String sayHi() {
        return "Hello Springboot";
    }
}
