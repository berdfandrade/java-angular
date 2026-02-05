package com.example.demo.infra.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
        @ExceptionHandler(Exception.class)
        public ResponseEntity<Map<String, String>> handleGeneric(Exception ex) {
                return ResponseEntity.status(500)
                                .body(Map.of("error", "Erro interno do servidor"));
        }
}
