package org.flutterstudy.api.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.flutterstudy.api.config.security.properties.SecurityConfigure;
import org.flutterstudy.api.domain.user.User;
import org.flutterstudy.api.contracts.dto.response.AuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider implements AuthenticationTokenProvider{

    private static final long TOKEN_VALID_MS = 1000L * 60 * 60 * 24 * 30; // 30일 토큰 유효

    private final SecretKey key;

    @Autowired
    public JwtTokenProvider(SecurityConfigure securityConfigure) {
        this(securityConfigure.getJwt().getSecureKey());
    }

    public JwtTokenProvider(String secretKey){
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public UserClaims parse(String token) throws InvalidTokenException {
        try {
            return UserClaims.of(Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token));
        }catch (JwtException e){
            throw new InvalidTokenException("Fail to parse token", e);
        }
    }

    @Override
    public AuthenticationToken create(User user) {
        Date now = new Date();
        Claims claims = Jwts.claims();
        claims.setSubject(user.getPrimaryId().toString());
        claims.setIssuedAt(now); // 토큰 발행일자
        claims.setExpiration(new Date(now.getTime() + TOKEN_VALID_MS)); // set Expire Time
        claims.put(UserClaims.ROLE, user.getRoles());

        String token =  Jwts.builder().setSubject(Long.toString(user.getPrimaryId()))
                .setClaims(claims)
                .signWith(key)
                .compact();

        return new AuthenticationToken(token);
    }

}
