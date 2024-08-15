
package com.example.bookmanagement.interceptor;

import com.example.bookmanagement.constant.Constants;
import com.example.bookmanagement.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    // 目标方法执行钱执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("拦截器触发，路径: " + request.getRequestURI());
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(Constants.SESSION_USER_KEY) != null) {
            UserInfo userInfo = (UserInfo) session.getAttribute(Constants.SESSION_USER_KEY);
            return userInfo != null && userInfo.getUserId() > 0;
        }
        response.setStatus(401);
        return false;
    }

    // 目标方法执行后执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("拦截器结束");
    }
}

