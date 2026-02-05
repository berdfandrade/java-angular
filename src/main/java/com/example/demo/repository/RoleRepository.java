package com.example.demo.repository;

import com.example.demo.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.example.demo.domain.enums.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}