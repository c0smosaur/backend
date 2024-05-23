package com.core.linkup.common.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.core.linkup.common.properties.EmailProperties.AUTH_CODE_EXPIRATION_TIME_SECONDS;
import static com.core.linkup.security.jwt.JwtProperties.*;

@Service
@RequiredArgsConstructor
public class RedisUtils {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String REFRESH_TOKEN_PREFIX = "refresh_token:";
    private static final String MAIL_AUTH_CODE_PREFIX = "auth_code";

    // saved for 24 hours
    public void saveRefreshToken(Long id, String refreshToken) {
        String key = REFRESH_TOKEN_PREFIX + id;
        redisTemplate.opsForValue().set(key, refreshToken);
        redisTemplate.expire(key, REFRESH_TOKEN_EXPIRATION_SECONDS, TimeUnit.SECONDS);
    }

    // saved 2 weeks
    public void saveRememberRefreshToken(Long id, String refreshToken) {
        String key = REFRESH_TOKEN_PREFIX + id;
        redisTemplate.opsForValue().set(key, refreshToken);
        redisTemplate.expire(key, REMEMBER_REFRESH_TOKEN_EXPIRATION_SECONDS, TimeUnit.SECONDS);
    }

    public String findRefreshToken(Long id) {
        String key = REFRESH_TOKEN_PREFIX + id;
        return redisTemplate.opsForValue().get(key);
    }

    public void saveAuthCode(String email, String authCode) {
        String key = MAIL_AUTH_CODE_PREFIX + email;
        redisTemplate.opsForValue().set(key, authCode);
        redisTemplate.expire(key, AUTH_CODE_EXPIRATION_TIME_SECONDS, TimeUnit.SECONDS);
    }

    public String findAuthCode(String email) {
        String key = MAIL_AUTH_CODE_PREFIX + email;
        return redisTemplate.opsForValue().get(key);
    }


}
