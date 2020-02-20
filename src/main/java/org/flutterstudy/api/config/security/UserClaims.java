package org.flutterstudy.api.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.AccessLevel;
import org.flutterstudy.api.domain.user.enums.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserClaims {

    public static String ROLE = "role";

    public Jws<Claims> claims;

    public Authentication getAuthentication() {
        AuthenticationUser authenticationUser = new AuthenticationUser(this.getUserId(), this.getUserRole());
        return new UsernamePasswordAuthenticationToken(authenticationUser, "", authenticationUser.getAuthorities());
    }

    public Long getUserId() {
        return Long.parseLong(claims.getBody().getSubject());
    }

    public Set<UserRole> getUserRole() {
        List<String> tempList = (List<String>) claims.getBody().get(ROLE);
        Set<UserRole> roleSet = new HashSet<>();
        if (tempList == null) {
            return roleSet;
        }

        for (String role : tempList) {
            roleSet.add(UserRole.valueOf(role));
        }
        return roleSet;
    }

    public boolean isValidate() {
        return !claims.getBody().getExpiration().before(new Date());
    }

    public static UserClaims of(Jws<Claims> claims){
        return new UserClaims(claims);
    }
}
