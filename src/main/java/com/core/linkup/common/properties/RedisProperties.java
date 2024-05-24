package com.core.linkup.common.properties;

public class RedisProperties {

    public static final String REFRESH_TOKEN_PREFIX = "refresh_token:";
    public static final String MAIL_AUTH_CODE_PREFIX = "auth_code";
    public static final String COMPANY_AUTH_CODE_PREFIX = "company_auth_code:";

    public static final int COMPANY_AUTH_CODE_EXPIRATION_SECONDS = 60*60*24*7; // 1 week
}
