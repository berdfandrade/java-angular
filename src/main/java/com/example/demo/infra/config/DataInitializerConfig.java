package com.example.demo.infra.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.demo.domain.Role;
import com.example.demo.domain.enums.RoleName;

import com.example.demo.repository.RoleRepository;

@Configuration
public class DataInitializerConfig {
    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {

            if (roleRepository.findByName(RoleName.ROLE_USER).isEmpty()) {
                roleRepository.save(new Role(RoleName.ROLE_USER));
            }

            if (roleRepository.findByName(RoleName.ROLE_ADMIN).isEmpty()) {
                roleRepository.save(new Role(RoleName.ROLE_ADMIN));
            }
        };
    }
}
