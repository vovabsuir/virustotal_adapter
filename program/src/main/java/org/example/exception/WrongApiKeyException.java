package org.example.exception;

public class WrongApiKeyException extends RuntimeException {
    public WrongApiKeyException(String message) {
        super(message);
    }
}
