package org.example.exception;

public class ProcessingFileException extends RuntimeException {
    public ProcessingFileException(String message) {
        super(message);
    }
}
