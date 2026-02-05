package com.example.demo.infra.exception.handler;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    @SuppressWarnings("null")
    @Test
    void shouldReturn500ForGenericException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Exception ex = new RuntimeException("qualquer erro");
        ResponseEntity<?> response = handler.handleGeneric(ex);
        assertEquals(500, response.getStatusCode().value());
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertEquals("Erro interno do servidor", body.get("error"));
    }
}
