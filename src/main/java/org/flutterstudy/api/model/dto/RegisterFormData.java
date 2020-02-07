package org.flutterstudy.api.model.dto;

import lombok.Value;

@Value
public class RegisterFormData {
	String userName;

	String email;

	String password;
}
