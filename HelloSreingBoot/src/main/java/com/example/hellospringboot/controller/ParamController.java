package com.example.hellospringboot.controller;

import org.springframework.http.HttpCookie;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;


@RestController
@RequestMapping("/param")
public class ParamController {
    @RequestMapping("/m1")
    public String m1(String name) {
        return "name: " + name;
    }

    // 如果这时候传 string 类型的参数就会报错
    @RequestMapping("/m2")
    public String m2(Integer name) {
        return "name: " + name;
    }

    // 重命名，但是如果说没找到 name 的话也不报错，就直接为空
    @RequestMapping("/m3")
    public String m3(@RequestParam(value = "name", required = false) String username) {
        return username;
    }

    // 只接收 get 请求，很少用，把路走窄了
    @RequestMapping(value = "/m4", method = RequestMethod.GET)
    public String m4(Person person) {
        return person.toString();
    }

    // 获取 json 格式里 body 中的参数
    @RequestMapping("/m5")
    public String m5(@RequestBody Person person) {
        return person.toString();
    }

    // 获取 url 中的参数
    @RequestMapping("/m6/{userId}/{name}")
    public String m6(@PathVariable Integer userId, @PathVariable("name") String username) {
        return "userId: " + userId + "username: " + username;
    }

    // 上传 图片/文件
    @RequestMapping("/m7")
    public String m7(@RequestPart MultipartFile file) throws IOException {
        // 打印 文件/图片 名字
        System.out.println(file.getOriginalFilename());
        // 保存 文件/图片
        file.transferTo(new File("C:/图片/" + file.getOriginalFilename()));
        return "ok";
    }

    // 获取 cookie
    // Servlet 获取
    @RequestMapping("/getCookie")
    public String getCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null){
            /*for (Cookie cookie : cookies) {
                System.out.println("cookie: " + cookie.getName() + "; " + cookie.getValue());
            }*/
            // 用λ表达式的方式来写
            Arrays.stream(cookies).forEach(cookie -> {
                System.out.println("cookie: " + cookie.getName() + "; " + cookie.getValue());
            });
            return "cookie获取成功!!!";
        }
        return "cookie获取失败!!!";
    }
    // 但是这种方式获取 cookie 有限，只能一个一个去拿，不好用
    @RequestMapping("/getCookie2")
    public String getCookie2(@CookieValue String lisi,
                             @CookieValue(value = "123", required = false) String yes,
                             @CookieValue(required = false) String text) {
        return "lisiのcookieは: " + lisi + "; |||| ; " +
                "123のcookieは: " + yes + "; |||| ;" +
                "textのcookieは: " + text;
    }

    // 获取 Session
    @RequestMapping("/setSession")
    public String setSession(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.setAttribute("username", "ZhangSan");
        return "Session OK";
    }
    @RequestMapping("/getSession")
    public String getSession(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null){
            String username = (String) session.getAttribute("username");
            return "用户1为: " + username;
        }
        return "Session 不存在!!!";
    }
    // 也是只能拿到一个 username
    @RequestMapping("/getSession2")
    public String getSession2(@SessionAttribute(required = false) String username) {
        return "用户2为: " + username;
    }
    // 可以拿到多个
    @RequestMapping("/getSession3")
    public String getSession3(HttpSession session) {
        return "用户3为:" + (String) session.getAttribute("username");
    }

    // 拿 Header
    @RequestMapping("/getHeader")
    public String getHeader(HttpServletRequest req) {
        return "userAgent:-> " + req.getHeader("User-Agent");
    }
    @RequestMapping("/getHeader2")
    public String getHeader2(@RequestHeader("User-Agent") String userAgent) {
        return "userAgent:-> " + userAgent;
    }
}
