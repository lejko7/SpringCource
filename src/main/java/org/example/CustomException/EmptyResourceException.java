package org.example.CustomException;

public class EmptyResourceException extends RuntimeException {

    public EmptyResourceException(String message) {
        super(message);
    }
}
