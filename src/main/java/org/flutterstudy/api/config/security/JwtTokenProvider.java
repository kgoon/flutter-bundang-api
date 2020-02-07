package org.flutterstudy.api.config.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.flutterstudy.api.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private long TOKEN_VALID_MS = 1000L * 60 * 60 * 24 * 30; // 30일 토큰 유효

    private String secretKey;

    public JwtTokenProvider(
            @Value("${app.security.jwt.private-key}") String secretKey
    ) {
        this.secretKey = secretKey;
    }

    public String createToken(User user) throws UnsupportedEncodingException {
        Date now = new Date();
        return Jwts.builder().setSubject(Long.toString(user.getId()))
                .claim(UserClaims.ROLE, user.getRoles())// 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() + TOKEN_VALID_MS)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public UserClaims parse(String jwtToken) {

        return new UserClaims(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken));
    }

}
