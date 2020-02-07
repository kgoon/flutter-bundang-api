package org.flutterstudy.api.config.security;

import org.flutterstudy.api.domain.user.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


public class AuthenticationUser implements UserDetails {

    private static final String AUTHORITY_PREFIX = "ROLE_";

    private Long id;

    private String email;

    private String password;

    private Set<UserRole> roles;

    public AuthenticationUser(Long id, Set<UserRole> roles) {
        this.id = id;
        this.roles = roles;
    }

    public Long getId() {
        return this.id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(UserRole::toAuthority)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
