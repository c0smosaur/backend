package com.core.linkup.security.jwt;

public class JwtProperties {

    public static final int TEST_ACCESS_TOKEN_EXPIRATION_MILLISECONDS = 60*5*1000; // 5min

    public static final int ACCESS_TOKEN_EXPIRATION_MILLISECONDS = 60*60*1000; // 1 hour
    public static final int REFRESH_TOKEN_EXPIRATION_MILLISECONDS = 60*60*24*1000; // 24 hours

    public static final int ACCESS_TOKEN_EXPIRATION_SECONDS = 60*60; // 1 hour
    public static final int REFRESH_TOKEN_EXPIRATION_SECONDS = 60*60*24; // 24 hours
    public static final int REMEMBER_REFRESH_TOKEN_EXPIRATION_SECONDS = 60*60*24*14;

    public static final int COOKIE_EXPIRATION_SECONDS = 60*60*24; // 24 hours

    public static final int REMEMBER_COOKIE_EXPIRATION_SECONDS = 60*60*24*14; // 2 weeks

    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String REMEMBER_REFRESH_TOKEN = "remember_refresh_token";

}