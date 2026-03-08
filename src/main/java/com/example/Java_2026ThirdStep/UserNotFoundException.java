package com.example.Java_2026ThirdStep;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
        super(userId + " not found");
    }
}