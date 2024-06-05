package com.core.linkup.security.jwt;

import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.common.utils.RedisUtils;
import com.core.linkup.security.MemberDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static com.core.linkup.common.utils.CookieUtils.getCookie;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRefreshTokenFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final RedisUtils redisUtils;
    private final MemberDetailsService memberDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (excludeUrls(request, response, filterChain)){
            System.out.println(request.getRequestURI()+" refresh-filter");
            return;
        }

        System.out.println(request.getRequestURI());

        // TODO: cookie 아니고 헤더에서
        String refreshToken = getRefreshTokenFromHeader(request);
//        Cookie refreshCookie = getCookie(request, "refresh-token");

        if (jwtProvider.isValidToken(refreshToken)){
                UserDetails memberDetails = getMemberFromToken(refreshToken);
                storeAuthenticationInContext(request, memberDetails);
        }
        filterChain.doFilter(request, response);
    }

    private String getRefreshTokenFromHeader(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("refresh-token"))
                .filter(token -> token.startsWith("Bearer "))
                .map(token -> token.substring(7))
                .orElse(null);
    }

    private UserDetails getMemberFromToken(String refreshToken) {        
        Long id = (long)(jwtProvider.getClaimValue(refreshToken, "member-id"));
        String token = refreshToken.substring(0, refreshToken.length()-4);
        if (redisUtils.findRefreshToken(id).equals(token)){
            return memberDetailsService.loadUserById(id);
        } else {
            throw new BaseException(BaseResponseStatus.INVALID_TOKEN);
        }
    }

    private void storeAuthenticationInContext(HttpServletRequest request,
                                              UserDetails memberDetails) {
        AbstractAuthenticationToken authentication =
                UsernamePasswordAuthenticationToken.authenticated(
                        memberDetails, null, memberDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public boolean excludeUrls(HttpServletRequest request,
                               HttpServletResponse response,
                               FilterChain filterChain) throws ServletException, IOException {
        if (!request.getRequestURI().contains("token")){
            filterChain.doFilter(request, response);
            return true;
        } else {
            return false;
        }
    }
}
