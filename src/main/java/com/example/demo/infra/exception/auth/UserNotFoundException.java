package com.example.demo.infra.exception.auth;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super("Usuário não encontrado com email: " + email);
    }
}