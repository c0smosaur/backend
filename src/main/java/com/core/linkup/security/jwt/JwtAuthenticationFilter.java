package com.core.linkup.security.jwt;

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

import static com.core.linkup.common.utils.CookieUtils.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final MemberDetailsService memberDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

//         로그인, 회원가입은 필터 무시
        if (excludeUrls(request, response, filterChain)){
            System.out.println(request.getRequestURI()+" access filter");
            return;
        }

        if (!request.getRequestURI().equals("/api/v1/member/token")){
//            Cookie refreshCookie = getCookie(request, "access-token");
//            if (refreshCookie != null){

//            }
            String accessToken = getAuthorizationFromHeader(request);

            if (jwtProvider.isValidToken(accessToken)){
                UserDetails memberDetails = getMemberFromToken(accessToken);
                storeAuthenticationInContext(request, memberDetails);
            }
        }
        filterChain.doFilter(request, response);
    }

        // TODO: Authorization header의 bearer scheme에서 추출하는 메서드
    private String getAuthorizationFromHeader(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Authorization"))
                .filter(token -> token.startsWith("Bearer "))
                .map(token -> token.substring(7))
                .orElse(null);
    }

    private UserDetails getMemberFromToken(String token) {
        Long id = (long)((int)jwtProvider.getClaimValue(token, "member-id"));
        return memberDetailsService.loadUserById(id);
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
        if ((request.getRequestURI().contains("register")||
                request.getRequestURI().contains("login")||
                request.getRequestURI().contains("validate")||
                request.getRequestURI().contains("verify"))||
                request.getRequestURI().contains("category")||
                request.getRequestURI().contains("/api/v1/office") ||
                request.getRequestURI().equals("/api/v1/member/token")||
                request.getRequestURI().equals("/api/v1/reservation/company")||
                request.getRequestURI().equals("/api/v1/club/search")||
                request.getRequestURI().contains("swagger-ui")
           ){
            filterChain.doFilter(request, response);
            return true;
        } else {
            return false;
        }
    }
}
