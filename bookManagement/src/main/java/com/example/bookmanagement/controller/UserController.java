package com.example.bookmanagement.controller;

import com.example.bookmanagement.constant.Constants;
import com.example.bookmanagement.model.UserInfo;
import com.example.bookmanagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    // 登录账号
    @RequestMapping("/login")
    public Boolean login(UserInfo userInfo, String captcha, HttpSession session) {
        // 变量写前面，如果说为 null 会报空指针异常
        Boolean ret = userService.login(userInfo, captcha, session);
        if (ret) {
            UserInfo sessionUser = userService.selectUserByName(userInfo.getUserName());
            session.setAttribute(Constants.SESSION_USER_KEY, sessionUser);
        }
        return ret;
    }

    // 注册账号
    @RequestMapping("/register")
    public Boolean register(UserInfo userInfo, String captcha, HttpSession session) {
        log.info("注册请求，用户信息: " + userInfo);
        Boolean ret = userService.register(userInfo, captcha, session);
        log.info("注册结果: " + ret);
        return ret;
    }
}
