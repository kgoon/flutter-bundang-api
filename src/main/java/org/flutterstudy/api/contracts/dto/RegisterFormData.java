package org.flutterstudy.api.contracts.dto;

import lombok.Value;

@Value
public class RegisterFormData {
	String email;

	String password;

	String birth;
}
