package com.core.linkup.common.config;

import com.core.linkup.security.jwt.JwtAuthenticationFilter;
import com.core.linkup.security.jwt.JwtRefreshTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtRefreshTokenFilter jwtRefreshTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(
                                // member
                                "/api/v1/member/login",
                                "/api/v1/member/register",
                                "/api/v1/member/validate/**",
                                "/api/v1/member/verify/**",
                                "/api/v1/member/my-page",
                                "/api/v1/member/token",
                                "/api/v1/member/company/register",

                                // reservation - 비로그인 범위
                                "/api/v1/reservation/company",

                                // office - 비로그인 범위
                                "/api/v1/office/**",

                                // 카테고리 조회
                                "/api/v1/category/*",

                                // office -
                                "/api/v1/office/**",

                                //소모임
                                "/api/v1/club/**").permitAll()

                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(jwtRefreshTokenFilter, JwtAuthenticationFilter.class)
        ;

        return http.build();
    }
}