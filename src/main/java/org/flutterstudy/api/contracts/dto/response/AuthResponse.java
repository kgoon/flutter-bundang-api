package org.flutterstudy.api.contracts.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.flutterstudy.api.domain.user.enums.UserRole;

import java.util.Set;

@Getter
@Builder
public class AuthResponse {
    Long userId;
    String userName;
    String email;
    Set<UserRole> roles;
    String token;
}
