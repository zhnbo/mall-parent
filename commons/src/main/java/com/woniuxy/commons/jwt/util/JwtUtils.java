package com.woniuxy.commons.jwt.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * JWT 工具类
 * @author zh_o
 * @date 2020-10-25
 */
public class JwtUtils {

    /**
     * 签名密钥
     */
    private static final String SIGN = "!@#we%34sJa";

    /**
     * 生成 Token
     * @param payloadMap 传入有效载荷
     * @return 生成后的令牌
     */
    public static String getToken(Map<String, String> payloadMap) {
        // 设置 Token 有效时间
        Calendar calendar = Calendar.getInstance();
        // 默认 7 天
        calendar.add(Calendar.DATE, 7);
        // 创建 JWT Builder
        JWTCreator.Builder builder = JWT.create();
        // 加载 Payload
        payloadMap.forEach(builder::withClaim);
        // 指定 Token 过期时间
        return builder.withExpiresAt(calendar.getTime())
                // 签名
                .sign(Algorithm.HMAC256(SIGN));
    }

    /**
     * 生成 Token 并指定失效时间
     * @param payloadMap 传入有效载荷
     * @param date 传入有效时间
     * @return 生成后的令牌
     */
    public static String getToken(Map<String, String> payloadMap, Date date) {
        // 创建 JWT Builder
        JWTCreator.Builder builder = JWT.create();
        // 加载 Payload
        payloadMap.forEach(builder::withClaim);
        // 指定 Token 过期时间
        return builder.withExpiresAt(date)
                // 签名
                .sign(Algorithm.HMAC256(SIGN));
    }

    /**
     * 验证 Token
     * @param token 传入Token
     * @return 解码后的 JWT
     */
    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
    }

}