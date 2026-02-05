package com.example.demo.infra.config;

import com.example.demo.domain.Role;
import com.example.demo.domain.enums.RoleName;
import com.example.demo.repository.RoleRepository;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.CommandLineRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DataInitializerConfigTest {

    @Test
    void shouldCreateRolesIfTheyDoNotExist() throws Exception {

        RoleRepository roleRepository = mock(RoleRepository.class);

        when(roleRepository.findByName(RoleName.ROLE_USER))
                .thenReturn(Optional.empty());

        when(roleRepository.findByName(RoleName.ROLE_ADMIN))
                .thenReturn(Optional.empty());

        DataInitializerConfig config = new DataInitializerConfig();

        CommandLineRunner runner = config.initRoles(roleRepository);

        runner.run();

        ArgumentCaptor<Role> captor = ArgumentCaptor.forClass(Role.class);

        verify(roleRepository, times(2)).save(captor.capture());

        List<Role> savedRoles = captor.getAllValues();

        assertTrue(savedRoles.stream().anyMatch(r -> RoleName.ROLE_USER.equals(r.getName())));
        assertTrue(savedRoles.stream().anyMatch(r -> RoleName.ROLE_ADMIN.equals(r.getName())));

    }
}
