package com.example.demo.infra.exception.handler;

import com.example.demo.infra.exception.auth.EmailAlreadyExistsException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class AuthExceptionHandlerTest {

    @Test
    void shouldReturn409WhenEmailAlreadyExists() {
        AuthExceptionHandler handler = new AuthExceptionHandler();

        EmailAlreadyExistsException ex = new EmailAlreadyExistsException("Email já registrado");

        var response = handler.handleEmailExists(ex);

        assertEquals(409, response.getStatusCode().value());
    }
}