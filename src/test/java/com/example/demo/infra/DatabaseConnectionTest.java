package com.example.demo.infra;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import org.junit.jupiter.api.Test;

@SpringBootTest()
@ActiveProfiles("test")
public class DatabaseConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void testDataSource() throws Exception {
        assertThat(dataSource).isNotNull(); // verifica se o DataSource foi carregado
        try (var connection = dataSource.getConnection()) {
            assertThat(connection.isValid(1)).isTrue(); // testa se a conexão está válida
        }
    }
}