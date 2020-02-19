package org.flutterstudy.api.config.security;

import org.flutterstudy.api.domain.user.User;
import org.flutterstudy.api.contracts.dto.AuthenticationToken;

public interface AuthenticationTokenProvider {

    AuthenticationToken create(User user);

    UserClaims parse(String token) throws InvalidTokenException;
}
