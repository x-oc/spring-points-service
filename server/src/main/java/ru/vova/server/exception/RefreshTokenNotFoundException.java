package ru.vova.server.exception;

public class RefreshTokenNotFoundException extends Exception {
    public RefreshTokenNotFoundException(String message) {
        super(message);
    }
}
