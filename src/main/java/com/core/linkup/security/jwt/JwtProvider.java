package com.core.linkup.security.jwt;

import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import static com.core.linkup.security.jwt.JwtProperties.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;
    private static SecretKey key;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        String base64EncodedKey = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
        key = Keys.hmacShaKeyFor(base64EncodedKey.getBytes());
    }

    public String createToken(Long id, String tokenType) {
        Date now = new Date();
        Claims claims = Jwts.claims().setSubject(tokenType);
        claims.put("member-id", id);

        int expirationTime;

        if (tokenType.equals(ACCESS_TOKEN)) {
//            expirationTime = ACCESS_TOKEN_EXPIRATION_MILLISECONDS;
            expirationTime = TEST_ACCESS_TOKEN_EXPIRATION_MILLISECONDS;
        } else if (tokenType.equals(REFRESH_TOKEN)){
            expirationTime = REFRESH_TOKEN_EXPIRATION_MILLISECONDS;
        } else {
            expirationTime = REFRESH_TOKEN_EXPIRATION_MILLISECONDS*14;
        }

            return Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(now)
                    .setExpiration(new Date(now.getTime()+expirationTime))
                    .setIssuer("LinkUp")
                    .signWith(key)
                    .compact()
                    ;
    }

    public Object getClaimValue(String token, String claimKey) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get(claimKey);
    }

    public Boolean isValidToken(String token){
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();

        try{
            parser.parseClaimsJws(token);
            return true;

        } catch (Exception e){
            return false;
            }
        }

//    public String decodeToken(String token, String claimKey) {
//        String[] tokenParts = token.split("\\.");
//        String decodedClaim = new String(
//                Base64.getDecoder().decode(tokenParts[1]),
//                StandardCharsets.UTF_8);
//
//        try{
//            Map decodedClaims = objectMapper.readValue(decodedClaim, Map.class);
//            return decodedClaims.get(claimKey);
//        } catch (JsonMappingException e) {
//            throw new RuntimeException(e);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public Long decodeTokenForId(String token){
        String[] tokenParts = token.split("\\.");
        String decodedClaim = new String(
                Base64.getDecoder().decode(tokenParts[1]),
                StandardCharsets.UTF_8);
        try {
            Map<String, Object> decodeClaims = objectMapper.readValue(decodedClaim,Map.class);
            return ((Integer) decodeClaims.get("member-id")).longValue();
        } catch (JsonProcessingException e) {
            throw new BaseException(BaseResponseStatus.INVALID_TOKEN);
        }
    }

}
