package com.zmbdp.springblog.config;

import com.zmbdp.springblog.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录校验拦截器，根据 token 来拦截
 */
@Configuration
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 登录校验
        // 1. 从 header 拿到 token
        String token = request.getHeader("user_token");
        // 2. 验证 token
        Claims claims = JwtUtils.parseToken(token);
        if (claims == null) {
            // 证明 token 不合法
            response.setStatus(401);
            return false;
        }
        return true;
    }
}
