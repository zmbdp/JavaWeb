package com.zmbdp.springblog.controller;

import com.zmbdp.springblog.model.Result;
import com.zmbdp.springblog.model.UserInfo;
import com.zmbdp.springblog.service.UserService;
import com.zmbdp.springblog.utils.JwtUtils;
import com.zmbdp.springblog.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 登录
     * @param userName
     * @param password
     * @param captcha
     * @return
     */
    @RequestMapping("/login")
    public Result login(String userName, String password, String captcha, HttpSession session) {
        /**
         * 1. 参数校验
         * 2. 密码、验证码校验
         * 3. 生成 token 返回给前端
         */
        if (!StringUtils.hasLength(userName) || !StringUtils.hasLength(password)) {
            return Result.fail("用户名或密码不可为空");
        }
        // 从数据库里面拿到密码
        UserInfo userInfo = userService.getUserByName(userName);
        if (userInfo == null) {
            return Result.fail("用户名或密码错误");
        }
        // 先得到加密后的密码
        // 拿到数据库里面的密码
        String sqlPassword = userInfo.getPassword();
        if (
                userInfo.getId() < 1 ||
                !SecurityUtils.verify(password, sqlPassword)// 校验密码
        ) {
            return Result.fail("用户名或密码错误");
        }
        if (!userService.check(captcha, session)) {
            return Result.fail("验证码错误，请重试");
        }
        // 生成 token
        Map<String, Object> map = new HashMap<>();
        map.put("id", userInfo.getId());
        map.put("name", userInfo.getUserName());
        String token = JwtUtils.getToken(map);
        return Result.success(token);
    }

    @RequestMapping("/getUserInfo")
    public UserInfo getUserInfo(HttpServletRequest request) {
        String token = request.getHeader("user_token");
        Integer userId = JwtUtils.getUserIdToken(token);
        if (userId == null || userId < 1) {
            return null;
        }
        return userService.getUserByUserId(userId);
    }

    /**
     * 通过用户 id 获取用户信息
     * @param blogId
     * @return
     */
    @RequestMapping("/getAuthorInfo")
    public UserInfo getAuthorInfo(Integer blogId) {
        if (blogId < 1) {
            return null;
        }
        return userService.getAuthorInfo(blogId);
    }

    /**
     * 注册
     * @param userInfo
     * @param captcha
     * @param session
     * @return
     */
    @RequestMapping("/register")
    public Boolean register(UserInfo userInfo, String captcha, HttpSession session) {
        // 判断验证码是否正确，然后直接把用户的信息存到文件里面
        if (
                !StringUtils.hasLength(userInfo.getUserName()) ||
                !StringUtils.hasLength(userInfo.getPassword())
        ) {
            return false;
        }
        return userService.register(userInfo, captcha, session);
    }
}
