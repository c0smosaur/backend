package com.core.linkup.security.jwt;

import com.core.linkup.common.service.RedisService;
import com.core.linkup.security.MemberDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static com.core.linkup.security.jwt.JwtProperties.ACCESS_TOKEN;
import static com.core.linkup.security.jwt.JwtProperties.ACCESS_TOKEN_EXPIRATION_SECONDS;
import static com.core.linkup.common.utils.CookieUtils.*;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final MemberDetailsService memberDetailsService;
    private final RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Optional<Cookie> accessCookie = getCookie(request, "access-token");
        if (accessCookie.isPresent()) {
            String accessToken = accessCookie.get().getValue();

            if (jwtProvider.validateTokenAndThrow(accessToken)) {
                // VALID access token
                UserDetails memberDetails = getMemberFromToken(accessToken);
                storeAuthenticationInContext(request, accessToken, memberDetails);
            } else {
                // INVALID access token or EXPIRED access token
                Optional<Cookie> refreshCookie = getCookie(request, "refresh-token");
                if (refreshCookie.isPresent()) {
                    // refresh token 존재
                    String refreshToken = refreshCookie.get().getValue();
                    if (jwtProvider.validateTokenAndThrow(refreshToken)) {
                        // VALID refresh token
                        String uuidString = jwtProvider.decodeToken(refreshToken, "user-id");
                        // redis에서 uuid로 저장된 refresh-token 불러와 일치하는지 확인
                        String redisRefreshToken = redisService.findRefreshToken(uuidString);
                        // match
                        if (refreshToken.equals(redisRefreshToken)) {
                            // 액세스 토큰 재발행
                            String newAccessToken =
                                    jwtProvider.createToken(UUID.fromString(uuidString), ACCESS_TOKEN);
                            addCookie(response, "access-token", newAccessToken, ACCESS_TOKEN_EXPIRATION_SECONDS);
                            // SecurityContext에 저장
                            UserDetails memberDetails = getMemberFromToken(newAccessToken);
                            storeAuthenticationInContext(request, newAccessToken, memberDetails);

                        }
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private UserDetails getMemberFromToken(String token) {
        String uuidString = (String) jwtProvider.getClaimValue(token, "user-id");
        return memberDetailsService.loadUserByUsername(uuidString);
    }

    private void storeAuthenticationInContext(HttpServletRequest request,
                                              String token, UserDetails memberDetails) {
        AbstractAuthenticationToken authentication =
                UsernamePasswordAuthenticationToken.authenticated(
                        memberDetails, token, memberDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
