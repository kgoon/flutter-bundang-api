package org.flutterstudy.api.contracts.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class UserNameValidatorTest {

	@ParameterizedTest
	@ValueSource(strings = {"kgoon", "k-goon", "k_goon", "k.goon"})
	void validUserName(String userName){
		boolean result = UserNameValidator.isAllowedName(userName);
		assertThat(result).isTrue();
	}


	@ParameterizedTest
	@ValueSource(strings = {"kgoon!", "길군"})
	void invalidUserName(String userName){
		boolean result = UserNameValidator.isAllowedName(userName);
		assertThat(result).isFalse();
	}

}