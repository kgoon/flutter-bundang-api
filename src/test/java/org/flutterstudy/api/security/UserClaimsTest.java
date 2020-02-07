package org.flutterstudy.api.security;

import org.flutterstudy.api.config.security.JwtTokenProvider;
import org.flutterstudy.api.config.security.UserClaims;
import org.flutterstudy.api.domain.user.User;
import org.flutterstudy.api.config.security.AuthenticationUser;
import org.flutterstudy.api.domain.user.enums.UserRole;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserClaimsTest {

    private Long testIdNumber = 33L;
    private UserRole testRole = UserRole.STAFF;
    private String privateKey = "secret";
    public UserClaims userClaims;

    @Before
    public void makeClaimsByJwt() throws UnsupportedEncodingException {
        User user = new User(testIdNumber,"test@kkongtents.com","tester", "pass");
        user.addRole(testRole);

        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(privateKey);
        userClaims = jwtTokenProvider.parse(jwtTokenProvider.createToken(user));
    }

    @Test
    public void validateClaims() {
        assertThat(userClaims.getUserId()).isEqualTo(testIdNumber);
        assertThat(userClaims.getUserRole().contains(testRole)).isTrue();
        assertThat(userClaims.isValidate()).isTrue();
    }

    @Test
    public void makeAuthenticationWithClaims() {
        assertThat(userClaims.getAuthentication().getPrincipal()).isNotNull();
        Long authenticationUserId = ((AuthenticationUser) userClaims.getAuthentication().getPrincipal()).getId();
        assertThat(authenticationUserId).isEqualTo(testIdNumber);
    }
}