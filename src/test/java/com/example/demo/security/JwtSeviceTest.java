package com.example.demo.security;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private final String secret = "segredo123segredo123segredo123segredo123";
    private final long expiration = 3600_000;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService(secret, expiration);
    }

    @Test
    void testGenerateAndParseToken() {
        String username = "bernardo";
        String role = "ADMIN";

        String token = jwtService.generateToken(username, role);
        assertNotNull(token, "O token não pode ser nulo");

        Claims claims = jwtService.parseToken(token);
        assertEquals(username, claims.getSubject(), "O username deve ser igual");
        assertEquals(role, claims.get("role"), "A role deve ser igual");

        assertTrue(claims.getExpiration().getTime() > System.currentTimeMillis(),
                "A data de expiração deve ser no futuro");
    }

    @Test
    void testTokenExpiration() throws InterruptedException {
        // token de 1 segundo
        JwtService shortLivedJwt = new JwtService(secret, 1000);
        String token = shortLivedJwt.generateToken("user", "USER");

        Thread.sleep(1500);

        assertThrows(io.jsonwebtoken.ExpiredJwtException.class, () -> {
            shortLivedJwt.parseToken(token);
        }, "Deve lançar ExpiredJwtException se o token expirou");
    }
}
