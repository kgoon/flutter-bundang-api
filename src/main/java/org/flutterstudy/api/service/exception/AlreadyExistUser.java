package org.flutterstudy.api.service.exception;

public class AlreadyExistUser extends RuntimeException {
	public AlreadyExistUser(String message) {
		super(message);
	}
}
