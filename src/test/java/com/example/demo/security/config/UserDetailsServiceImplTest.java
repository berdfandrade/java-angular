package com.example.demo.security.config;

import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.domain.enums.RoleName;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserDetailsServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

        @Mock
        private UserRepository userRepository;

        @InjectMocks
        private UserDetailsServiceImpl userDetailsService;

        @Test
        void shouldLoadUserByUsernameSuccessfully() {
                // arrange
                Role role = new Role(RoleName.ROLE_USER);

                User user = new User();
                user.setEmail("bernardo@email.com");
                user.setPassword("senha-hash");
                user.setRole(role);

                when(userRepository.findByEmail("bernardo@email.com"))
                                .thenReturn(Optional.of(user));

                // act
                UserDetails userDetails = userDetailsService.loadUserByUsername("bernardo@email.com");

                // assert
                assertThat(userDetails.getUsername()).isEqualTo("bernardo@email.com");
                assertThat(userDetails.getPassword()).isEqualTo("senha-hash");
                assertThat(userDetails.getAuthorities())
                                .extracting("authority")
                                .containsExactly("ROLE_USER");
        }

        @Test
        void shouldThrowExceptionWhenUserNotFound() {
                // arrange
                when(userRepository.findByEmail("inexistente@email.com"))
                                .thenReturn(Optional.empty());

                // act + assert
                assertThatThrownBy(() -> userDetailsService.loadUserByUsername("inexistente@email.com"))
                                .isInstanceOf(UsernameNotFoundException.class)
                                .hasMessage("Usuário não encontrado");
        }
}
