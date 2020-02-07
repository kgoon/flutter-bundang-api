package org.flutterstudy.api.service.exception;

public class NotFoundMatchedUser extends RuntimeException {

    public NotFoundMatchedUser(String errorMessage) {
        super(errorMessage);
    }
}
