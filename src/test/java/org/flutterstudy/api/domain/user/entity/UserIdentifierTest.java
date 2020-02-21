package org.flutterstudy.api.domain.user.entity;

import org.flutterstudy.api.domain.user.enums.UserIdentifierType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserIdentifierTest {

	@Test
	void getValue() {
		String email = "test@domain.com";
		UserIdentifier identifier = new UserIdentifier(UserIdentifierType.EMAIL, email, 101L);

		assertThat(identifier.getValue()).isEqualTo(email);
	}
}