package com.mohdev.dream_shop.exception;

public class AlreadyExistException extends RuntimeException {
    public AlreadyExistException(String message) {
        super(message);
    }
}