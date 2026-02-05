package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import com.example.demo.infra.exception.auth.EmailNotFoundException;
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

        String username = "john_doe";
        String email = "john_doe@example.com";
        String rawPassword = "123456789";

        when(userRepository
                .save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User createdUser = userService.createUser(username, email, rawPassword);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertEquals(username, savedUser.getUsername());
        assertEquals(email, savedUser.getEmail());

        assertNotEquals(rawPassword, savedUser.getPassword());
        assertTrue(new BCryptPasswordEncoder().matches(rawPassword, savedUser.getPassword()));

        assertEquals(savedUser, createdUser);
    }

    @Test
    void getUserByEmail_shouldReturnUser_whenEmailExists() {

        String email = "john@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email))
                .thenReturn(java.util.Optional.of(user));

        User result = userService.getUserByEmail(email);

        assertEquals(email, result.getEmail());
        verify(userRepository).findByEmail(email);
    }

    @Test
    void getUserByEmail_shouldThrowException_whenEmailNotFound() {

        String email = "ghost@example.com";

        when(userRepository.findByEmail(email))
                .thenReturn(java.util.Optional.empty());

        assertThrows(
                EmailNotFoundException.class,
                () -> userService.getUserByEmail(email));

        verify(userRepository).findByEmail(email);
    }

}
