package org.flutterstudy.api.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.flutterstudy.api.config.security.InvalidTokenException;
import org.flutterstudy.api.config.security.JwtTokenProvider;
import org.flutterstudy.api.config.security.UserClaims;
import org.flutterstudy.api.domain.user.User;
import org.flutterstudy.api.domain.user.entity.UserBase;
import org.flutterstudy.api.domain.user.enums.UserRole;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtTokenProviderTest {

    private static String SECRET_KEY_STR;
    private final Long idNumber = 234L;
    private final String testUserPassword = "pass";
    JwtTokenProvider jwtTokenProvider;

    @BeforeAll
    public static void prepare(){
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        SECRET_KEY_STR = Encoders.BASE64.encode(key.getEncoded());
    }

    @BeforeEach
    public void prepareTest(){
        jwtTokenProvider = new JwtTokenProvider(SECRET_KEY_STR);
    }

    @Test
    public void createJwt() throws InvalidTokenException {
        User testUser = User.of(new UserBase(idNumber, testUserPassword));
        testUser.addRole(UserRole.STAFF);
        String jwt = jwtTokenProvider.create(testUser).getToken();

        // action
        UserClaims userClaims = jwtTokenProvider.parse(jwt);

        // assertion
        assertThat(userClaims.getUserId()).isEqualTo(idNumber);
        assertThat(userClaims.getUserRole()).contains(UserRole.STAFF);
        assertThat(userClaims.isValidate()).isTrue();
    }

}