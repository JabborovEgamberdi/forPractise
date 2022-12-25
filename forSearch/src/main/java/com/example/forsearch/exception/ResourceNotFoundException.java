package com.example.forsearch.exception;

public class ResourceNotFoundException extends RuntimeException{

    private final static long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
