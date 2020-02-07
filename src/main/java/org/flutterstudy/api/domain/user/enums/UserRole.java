package org.flutterstudy.api.domain.user.enums;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {
    STAFF,
    USER,
    OWNER;

	private static final String AUTHORITY_PREFIX = "ROLE_";

	public GrantedAuthority toAuthority() {
		return new SimpleGrantedAuthority(AUTHORITY_PREFIX + this.toString());
	}
}
