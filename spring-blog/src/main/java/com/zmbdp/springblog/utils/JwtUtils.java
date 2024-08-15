package com.zmbdp.springblog.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Slf4j
public class JwtUtils {
    private static final long EXPIRATION = 1000 * 60 * 30;
    private static final String SECRET_STRING = "9dl2i3/fBSn9O64/LsHZQYzFNQ+Zbu4lg6igM3rvB5Q=";
    private static final Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_STRING));

    /**
     * 生成 token
     * @param claim
     * @return
     */
    public static String getToken(Map<String, Object> claim) {
        String result = Jwts.builder()
                .setClaims(claim)// 设置载荷信息
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))// 设置过期时间
                .signWith(key)// 设置签名
                .compact();// 然后生成 token
        return result;
    }

    /**
     * 校验 token
     * @param token
     * @return
     */
    public static Claims parseToken(String token){
        if (token == null) {
            return null;
        }
        JwtParser build = Jwts.parserBuilder().setSigningKey(key).build();
        Claims claims = null;
        try {
            claims = build.parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.error("token错误");
            return null;
        }
        return claims;
    }

    public static Integer getUserIdToken(String token) {
        Claims claims = parseToken(token);
        if (claims == null) {
            return null;
        }
        return (Integer) claims.get("id");
    }
}
