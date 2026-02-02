package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Busca usuário por username
    Optional<User> findByUsername(String username);

    // Busca por email
    Optional<User> findByEmail(String email);

}
