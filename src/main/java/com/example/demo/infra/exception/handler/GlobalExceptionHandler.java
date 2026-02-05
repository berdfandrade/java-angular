package com.example.demo.infra.exception.handler;

import com.example.demo.infra.exception.auth.EmailAlreadyExistsException;
import com.example.demo.infra.exception.auth.EmailNotFoundException;
import com.example.demo.infra.exception.auth.UserNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler({
                        UserNotFoundException.class,
                        EmailNotFoundException.class
        })
        public ResponseEntity<Map<String, String>> handleNotFound(RuntimeException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(Map.of("error", ex.getMessage()));
        }

        @ExceptionHandler(EmailAlreadyExistsException.class)
        public ResponseEntity<Map<String, String>> handleConflict(RuntimeException ex) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(Map.of("error", ex.getMessage()));
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<Map<String, String>> handleBadRequest(RuntimeException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Map.of("error", ex.getMessage()));
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<Map<String, String>> handleGeneric(Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(Map.of("error", "Erro interno do servidor"));
        }
}
