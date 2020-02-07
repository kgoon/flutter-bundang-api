package org.flutterstudy.api.security;

import org.flutterstudy.api.config.security.JwtTokenProvider;
import org.flutterstudy.api.config.security.UserClaims;
import org.flutterstudy.api.domain.user.User;
import org.flutterstudy.api.domain.user.enums.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtTokenProviderTest {

    private final Long idNumber = 234L;
    private final String testUserEmail = "ditoo@naver.com";
    private final String testUserPassword = "pw234";
    private final UserRole testRole = UserRole.STAFF;

    public JwtTokenProvider jwtTokenProvider = new JwtTokenProvider("testKey");

    public User testUser = new User(idNumber, testUserEmail, "tester", testUserPassword);

    public UserClaims userClaims;

    @Test
    public void createJwt() throws UnsupportedEncodingException {
        testUser.addRole(testRole);
        String jwt = jwtTokenProvider.createToken(testUser);
        userClaims = jwtTokenProvider.parse(jwt);

        assertThat(userClaims.getUserId()).isEqualTo(idNumber);
        Assertions.assertThat(userClaims.getUserRole()).contains(testRole);
        assertThat(userClaims.isValidate()).isTrue();
    }

}