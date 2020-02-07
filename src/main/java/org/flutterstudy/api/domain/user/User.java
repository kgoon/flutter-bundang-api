package org.flutterstudy.api.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.flutterstudy.api.domain.user.enums.UserRole;

import java.util.EnumSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    private Long id;

    private String email;

    private String userName;

    @JsonIgnore
    private String password;

    private Set<UserRole> roles = EnumSet.noneOf(UserRole.class);

    public User(long id, String email, String userName, String password) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public void addRole(UserRole role) {
        roles.add(role);
    }

    public boolean hasRole(UserRole role) {
        return roles.contains(role);
    }

    public void deleteRole(UserRole role) {
        roles.remove(role);
    }
}
