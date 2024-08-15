package com.example.bookmanagement.service;

import com.example.bookmanagement.mapper.UserMapper;
import com.example.bookmanagement.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Service
public class UserService {
    private static final String KAPTCHA_SESSION_KEY = "ADMIN_KAPTCHA_SESSION_KEY";
    private static final String KAPTCHA_SESSION_DATE = "ADMIN_KAPTCHA_SESSION_DATE";

    private static final Long SESSION_TIMEOUT = 60 * 1000L;
    @Autowired
    private UserMapper userMapper;

    // Map<String, String> userInfos = new HashMap<>();

    public Boolean login(UserInfo userInfo, String captcha, HttpSession session) {
        // 账号密码合法性，还有账号密码合法性和正确性判断
        if (
                !StringUtils.hasLength(userInfo.getUserName()) ||
                !StringUtils.hasLength(userInfo.getPassword()) ||
                !check(captcha, session)

        ) {
            return false;
        }
        String selectPassword = userMapper.selectUser(userInfo.getUserName());
        String password = userInfo.getPassword();
        // 得判空，不然会报错
        if (selectPassword == null) {
            return false;
        }
        // 变量写前面，如果说为 null 会报空指针异常
        /*if ("zhangsan".equals(userName) && "123".equals(password)) {
            return true;
        }*/
        // 先存到内存中
        // 根据数据库得到的密码，对这个密码进行比较
        if (!selectPassword.isEmpty() && password.equals(selectPassword)) {
            return true;
        }
        return false;
    }

    // 注册账号
    public Boolean register(UserInfo userInfo, String captcha, HttpSession session) {
        // 判断验证码是否正确，然后直接把用户的信息存到文件里面
        if (
                !StringUtils.hasLength(userInfo.getUserName()) ||
                !StringUtils.hasLength(userInfo.getPassword()) ||
                !check(captcha, session)
        ) {
            return false;
        }
        String userName = userInfo.getUserName();
        String password = userInfo.getPassword();
        // 判断一下，如果数据库返回 0，就是没插入成功，说明用户存在
        if (userMapper.insertUser(userName, password) == 0) {
            return false;
        }
        return true;
    }

    public UserInfo selectUserByName(String userName) {
        if (!StringUtils.hasLength(userName)) {
            return null;
        }
        return userMapper.selectUserByName(userName);
    }

    // 验证码判断
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
}
