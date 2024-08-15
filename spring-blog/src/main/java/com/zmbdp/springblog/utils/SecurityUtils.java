package com.zmbdp.springblog.utils;

import org.springframework.util.DigestUtils;

import java.util.UUID;

// 使用加密的方式
public class SecurityUtils {
    /**
     * 加密
     *
     * @param password 明文密码
     * @return 盐值+密文
     */
    public static String encrypt(String password) {
        //生成随机盐值
        String salt = UUID.randomUUID().toString().replace("-", "");
        //加密  盐值+明文
        String securityPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        //数据库中存储   盐值+密文
        return salt + securityPassword;
    }

    /**
     * 校验
     *
     * @param inputPassword
     * @param sqlPassword
     * @return
     */
    public static boolean verify(String inputPassword, String sqlPassword) {
        //取出盐值
        if (sqlPassword == null || sqlPassword.length() != 64) {
            return false;
        }
        // 拿到前面的
        String salt = sqlPassword.substring(0, 32);
        // 进行加密
        String securityPassword = DigestUtils.md5DigestAsHex(inputPassword.getBytes());
        return (salt + securityPassword).equals(sqlPassword);
    }
}
