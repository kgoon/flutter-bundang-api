package org.flutterstudy.api.security;

import org.flutterstudy.api.config.security.InvalidTokenException;
import org.flutterstudy.api.config.security.JwtTokenProvider;
import org.flutterstudy.api.config.security.UserClaims;
import org.flutterstudy.api.domain.user.User;
import org.flutterstudy.api.domain.user.entity.UserBase;
import org.flutterstudy.api.domain.user.enums.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtTokenProviderTest {

    private final Long idNumber = 234L;
    private final String testUserPassword = "pass";
    private final UserRole testRole = UserRole.STAFF;

    public JwtTokenProvider jwtTokenProvider = new JwtTokenProvider("testKey");

    public User testUser = User.of(new UserBase(idNumber, testUserPassword));

    public UserClaims userClaims;

    @Test
    public void createJwt() throws InvalidTokenException {
        testUser.addRole(testRole);
        String jwt = jwtTokenProvider.create(testUser).getToken();
        userClaims = jwtTokenProvider.parse(jwt);

        assertThat(userClaims.getUserId()).isEqualTo(idNumber);
        Assertions.assertThat(userClaims.getUserRole()).contains(testRole);
        assertThat(userClaims.isValidate()).isTrue();
    }

}