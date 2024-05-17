package com.core.linkup.security.jwt;

public class JwtProperties {

    // 1시간(millisecond)
    public static final int ACCESS_TOKEN_EXPIRATION_MILLISECONDS = 60*60*1000;
    // 24시간
    public static final int REFRESH_TOKEN_EXPIRATION_MILLISECONDS = 60*60*24*1000;

    public static final int ACCESS_TOKEN_EXPIRATION_SECONDS = 60*60;
    public static final int REFRESH_TOKEN_EXPIRATION_SECONDS = 60*60*24;

    public static final int COOKIE_EXPIRATION_SECONDS = 60*60*24;

    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";

}