package com.example.demo.infra.exception.handler;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.example.demo.infra.exception.auth.EmailAlreadyExistsException;
import com.example.demo.infra.exception.auth.UserNotFoundException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    @Test
    void shouldReturn500ForGenericException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Exception ex = new RuntimeException("qualquer erro");
        ResponseEntity<?> response = handler.handleGeneric(ex);
        assertEquals(500, response.getStatusCode().value());
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertEquals("Erro interno do servidor", body.get("error"));
    }

    @Test
    void shouldReturn404ForUserNotFound() {

        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        RuntimeException ex = new UserNotFoundException("john@example.com");
        ResponseEntity<?> response = handler.handleNotFound(ex);
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertEquals(
                "Usuário não encontrado com email: john@example.com",
                body.get("error"));
    }

    @Test
    void shouldReturn409ForEmailAlreadyExists() {

        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        RuntimeException ex = new EmailAlreadyExistsException("Email já existe");
        ResponseEntity<?> response = handler.handleConflict(ex);
        assertEquals(409, response.getStatusCode().value());
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertEquals("Email já existe", body.get("error"));
    }

    @Test
    void shouldReturn400ForIllegalArgument() {

        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        RuntimeException ex = new IllegalArgumentException("input inválido");
        ResponseEntity<?> response = handler.handleBadRequest(ex);
        assertEquals(400, response.getStatusCode().value());
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertEquals("input inválido", body.get("error"));
    }

}
