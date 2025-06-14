package org.dongguk.dambo.constant;

public class AuthConstant {
    public static final String USER_ID_CLAIM_NAME = "uid";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String ANONYMOUS_USER = "anonymousUser";
    public static final String[] AUTH_WHITELIST = {
            "/api/v1/auth/sign-up",
            "/api/v1/auth/login",
            "/api/v1/auth/check-id",
            "/api/v1/contracts",
    };
    private AuthConstant() {
    }
}