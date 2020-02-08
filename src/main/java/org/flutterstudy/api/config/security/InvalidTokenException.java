package org.flutterstudy.api.config.security;

public class InvalidTokenException extends Exception{
    public InvalidTokenException(String message, Exception cause) {
        super(message, cause);
    }
}
