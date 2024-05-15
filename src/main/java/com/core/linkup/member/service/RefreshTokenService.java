package com.core.linkup.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.core.linkup.security.jwt.JwtProperties.REFRESH_TOKEN_EXPIRATION_SECONDS;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String REFRESH_TOKEN_PREFIX = "refresh_token:";

    public void saveRefreshToken(String uuid, String refreshToken) {
        String key = REFRESH_TOKEN_PREFIX + uuid;
        redisTemplate.opsForValue().set(key, refreshToken);
        redisTemplate.expire(key, REFRESH_TOKEN_EXPIRATION_SECONDS, TimeUnit.SECONDS);
    }

    public String findRefreshToken(String uuid) {
        String key = REFRESH_TOKEN_PREFIX + uuid;
        return redisTemplate.opsForValue().get(key);
    }


}
