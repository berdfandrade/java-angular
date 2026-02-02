package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void createUser_shoudSaveUserWithHashedPassword() {

        // Arrage
        String username = "john_doe";
        String email = "john_doe@example.com";
        String rawPassword = "123456789";

        when(userRepository
                .save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User createdUser = userService.createUser(username, email, rawPassword);

        // Assert
        // Captura o usuário que foi passado para o save
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertEquals(username, savedUser.getUsername());
        assertEquals(email, savedUser.getEmail());

        assertNotEquals(rawPassword, savedUser.getPassword());
        assertTrue(new BCryptPasswordEncoder().matches(rawPassword, savedUser.getPassword()));

        assertEquals(savedUser, createdUser);
    }
}
