package com.questionnaire.security.jwt;

import com.questionnaire.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";

    private Key key;

    private long tokenValidityInMilliseconds;

    private long tokenValidityInMillisecondsForRememberMe;

    public TokenProvider() {

    }

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(SecurityConstants.SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities =authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + SecurityConstants.EXPIRATION_TIME_FOR_REMEMBER_ME);
        } else {
            validity = new Date(now + SecurityConstants.EXPIRATION_TIME);
        }
        /*byte[] secretKey = SecurityConstants.SECRET.getBytes(StandardCharsets.UTF_8);*/

        String token = Jwts.builder().
                setSubject(authentication.getName()).
                claim(AUTHORITIES_KEY, authorities).
                signWith(key, SignatureAlgorithm.HS512).
                setExpiration(validity).
                compact();

        return token;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        return true;
    }
}
