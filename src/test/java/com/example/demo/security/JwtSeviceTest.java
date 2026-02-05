package com.example.demo.security;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.demo.domain.enums.RoleName;
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
        RoleName role = RoleName.ROLE_ADMIN; // agora é enum

        // converter enum para String na hora de gerar token
        String token = jwtService.generateToken(username, role);
        assertNotNull(token, "O token não pode ser nulo");

        Claims claims = jwtService.parseToken(token);
        assertEquals(username, claims.getSubject(), "O username deve ser igual");
        assertEquals(role.name(), claims.get("role"), "A role deve ser igual"); // compara String

        assertTrue(claims.getExpiration().getTime() > System.currentTimeMillis(),
                "A data de expiração deve ser no futuro");
    }

    @Test
    void testTokenExpiration() throws InterruptedException {
        JwtService shortLivedJwt = new JwtService(secret, 1000);
        String token = shortLivedJwt.generateToken("user", RoleName.ROLE_USER);

        Thread.sleep(1500);

        assertThrows(io.jsonwebtoken.ExpiredJwtException.class, () -> {
            shortLivedJwt.parseToken(token);
        }, "Deve lançar ExpiredJwtException se o token expirou");
    }
}
