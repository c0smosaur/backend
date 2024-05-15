package com.core.linkup.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
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
import java.util.UUID;

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

    public String createToken(UUID uuid, String tokenType) {
        Date now = new Date();
        Claims claims = Jwts.claims().setSubject(tokenType);
        claims.put("user-id", uuid);

        int expirationTime = (tokenType.equals(ACCESS_TOKEN) ? ACCESS_TOKEN_EXPIRATION_MILLISECONDS : REFRESH_TOKEN_EXPIRATION_MILLISECONDS);

            return Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(now)
                    .setExpiration(new Date(now.getTime()+expirationTime))
                    .setIssuer("be-final")
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

    public Boolean validateTokenAndThrow(String token){
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();

        try{
            parser.parseClaimsJws(token);
            return true;

        } catch (Exception e){
            if (e instanceof SignatureException) {
                log.info("Invalid JWT signature");
                return false;

            } else if (e instanceof ExpiredJwtException){
                log.info("Token expired");
//                throw e;
                return false;
            } else {
                log.info("Invalid JWT");
                return false;
            }
        }
    }

    public String decodeToken(String token, String claimKey) throws JsonProcessingException {
        String[] tokenParts = token.split("\\.");
        String decodedClaim = new String(
                Base64.getDecoder().decode(tokenParts[1]),
                StandardCharsets.UTF_8);

        Map<String, String> decodedClaims = objectMapper.readValue(decodedClaim, Map.class);
        return (String) decodedClaims.get(claimKey);
    }

}
