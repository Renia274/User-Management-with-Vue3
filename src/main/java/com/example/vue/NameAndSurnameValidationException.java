package com.example.vue;

public class NameAndSurnameValidationException extends RuntimeException {
    public NameAndSurnameValidationException() {
        super();
    }

    public NameAndSurnameValidationException(String message) {
        super(message);
    }
}
