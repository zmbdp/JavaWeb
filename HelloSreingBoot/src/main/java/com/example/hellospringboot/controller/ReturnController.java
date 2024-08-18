package com.example.hellospringboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/return")
// @RestController
public class ReturnController {
    @RequestMapping("/index")
    public String index() {
        return "/index.html";
    }

    @ResponseBody
    @RequestMapping("/returnHtml")
    public String returnHtml() {
        return "<h1>返回HTML代码片段</h1>";
    }

    @ResponseBody
    @RequestMapping("/returnJson")
    public Person returnJson() {
        Person person = new Person();
        person.setId(21);
        person.setAge(12);
        person.setName("zhangsan");
        return person;
    }

    @ResponseBody
    @RequestMapping("/returnMap")
    public Map<String, Integer> returnMap() {
        Map<String, Integer> retMap = new HashMap<>();
        retMap.put("zhangsan", 19);
        retMap.put("lisi", 29);
        retMap.put("wangwu", 39);
        return retMap;
    }

    @ResponseBody
    @RequestMapping("/setStatus")
    public String setStatus(HttpServletResponse resp){
        resp.setStatus(401);//通常表示没有登录
        return "设置状态码";
    }

    @ResponseBody
    @RequestMapping("/setHeader")
    public String setHeader(HttpServletResponse resp){
        // 设置 Header
        resp.setHeader("myHeader", "myHeader");
        return "{'ok':1}";
    }
}
