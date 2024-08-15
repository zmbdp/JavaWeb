package com.zmbdp.springblog.service;


import com.zmbdp.springblog.mapper.BlogInfoMapper;
import com.zmbdp.springblog.mapper.UserInfoMapper;
import com.zmbdp.springblog.model.BlogInfo;
import com.zmbdp.springblog.model.UserInfo;
import com.zmbdp.springblog.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    private static final String KAPTCHA_SESSION_KEY = "ADMIN_KAPTCHA_SESSION_KEY";
    private static final String KAPTCHA_SESSION_DATE = "ADMIN_KAPTCHA_SESSION_DATE";

    private static final Long SESSION_TIMEOUT = 60 * 1000L;
    @Autowired
    private BlogInfoMapper blogInfoMapper;

    /**
     * 根据博客 id 查询作者 id，然后再根据作者 id 查询作者信息
     * @param blogId
     * @return
     */
    public UserInfo getAuthorInfo(Integer blogId) {
        BlogInfo blogInfo = null;
        try {
            blogInfo = blogInfoMapper.selectBlogByBlogId(blogId);
        } catch (Exception e) {
            log.error("该博客不存在");
            return null;
        }
        Integer userId = blogInfo.getUserId();
        UserInfo userInfo = null;
        try {
            userInfo = userInfoMapper.selectUserByUserId(userId);
        } catch (Exception e) {
            log.error("该用户不存在");
        }
        return userInfo;
    }

    /**
     * 注册账号
     * @param userInfo 想注册用户
     * @param captcha 验证码
     * @param session
     * @return
     */
    public Boolean register(UserInfo userInfo, String captcha, HttpSession session) {
        // 判断验证码是否正确，然后直接把用户的信息存到文件里面
        if (!check(captcha, session)) {
            return false;
        }
        String userName = userInfo.getUserName();
        String githubUrl = userInfo.getGithubUrl();
        // 先把密码改成加密之后的密码
        String password = SecurityUtils.encrypt(userInfo.getPassword());
        // 判断一下，如果数据库返回 0，就是没插入成功，说明用户存在
        int flag = 0;
        try {
            flag = userInfoMapper.insertUser(userName, password, githubUrl);
        } catch (NullPointerException e) {
            log.error("用户存在");
            return false;
        }
        return flag != 0;
    }

    /**
     * 判断验证码是否正确
     * @param captcha 用户输入的验证码
     * @param session 当前会话
     * @return 验证是否通过
     */
    public Boolean check(String captcha, HttpSession session) {
        // 先判断输入过来的验证码是否为空
        if (!StringUtils.hasLength(captcha)) {
            return false;
        }
        // 再判断是否相等
        if (!captcha.equals(session.getAttribute(KAPTCHA_SESSION_KEY))) {
            return false;
        }
        // 再看时间
        Date saveDate = (Date) session.getAttribute(KAPTCHA_SESSION_DATE);
        // 如果等于空，说明没刷新，就不能进去
        if (
                null != saveDate &&
                System.currentTimeMillis() - saveDate.getTime() < SESSION_TIMEOUT
        ) {
            return true;
        }
        return false;
    }

    public UserInfo getUserByName(String userName) {
        return userInfoMapper.selectUserByName(userName);
    }

    public UserInfo getUserByUserId(Integer userId) {
        return userInfoMapper.selectUserByUserId(userId);
    }
}
