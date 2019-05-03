package com.questionnaire.security;

public class SecurityConstants {

    public static final String SECRET = "The specified key byte array is 144 bits which is not secure enough for any JWT HMAC-SHA algorithm";

    public static final long EXPIRATION_TIME = 86_400_000;

    public static final long EXPIRATION_TIME_FOR_REMEMBER_ME = 864_000_000;

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String SIGN_UP_URL = "/users/sign-up";
}
