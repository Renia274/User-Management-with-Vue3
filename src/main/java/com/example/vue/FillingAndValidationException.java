package com.example.vue;

public class FillingAndValidationException extends RuntimeException {
    public FillingAndValidationException() {
        super();
    }

    public FillingAndValidationException(String message) {
        super(message);
    }
}
