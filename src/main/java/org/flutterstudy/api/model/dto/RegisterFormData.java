package org.flutterstudy.api.model.dto;

import lombok.Value;

@Value
public class RegisterFormData {
	String email;

	String password;

	String birth;
}
