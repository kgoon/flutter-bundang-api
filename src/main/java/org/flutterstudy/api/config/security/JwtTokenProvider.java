package org.flutterstudy.api.config.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.flutterstudy.api.domain.user.User;
import org.flutterstudy.api.domain.user.entity.UserBase;
import org.flutterstudy.api.model.dto.AuthenticationToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Component
public class JwtTokenProvider implements AuthenticationTokenProvider{

    private static final long TOKEN_VALID_MS = 1000L * 60 * 60 * 24 * 30; // 30일 토큰 유효

    private final String secretKey;

    public JwtTokenProvider(
            @Value("${app.security.jwt.private-key}") String secretKey
    ) {
        this.secretKey = secretKey;
    }

    public UserClaims parse(String token) throws InvalidTokenException {
        try {
            return new UserClaims(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token));
        }catch (JwtException e){
            throw new InvalidTokenException("Fail to parse token", e);
        }
    }

    @Override
    public AuthenticationToken create(User user) {
        Date now = new Date();
        String token =  Jwts.builder().setSubject(Long.toString(user.getPrimaryId()))
                .claim(UserClaims.ROLE, user.getRoles())// 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() + TOKEN_VALID_MS)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return new AuthenticationToken(token);
    }
}
