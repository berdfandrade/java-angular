// package com.example.demo.controller;

// import com.example.demo.domain.User;
// import com.example.demo.dto.user.CreateUserDTO;
// import com.example.demo.service.UserService;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;

// import static
// org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static
// org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import com.fasterxml.jackson.databind.ObjectMapper;

// class UserControllerTest {

// private final UserService userService = Mockito.mock(UserService.class);
// private final UserController userController = new
// UserController(userService);
// private final MockMvc mockMvc =
// MockMvcBuilders.standaloneSetup(userController).build();
// private final ObjectMapper objectMapper = new ObjectMapper();

// @Test
// void createUser_ShouldReturnUser() throws Exception {
// // DTO que será enviado
// CreateUserDTO dto = new CreateUserDTO();
// dto.setUsername("bernardo");
// dto.setEmail("bernardo@example.com");
// dto.setPassword("123456");

// // Mockando o retorno do service
// User user = new User(1L, "bernardo", "hashedPassword", null);
// Mockito.when(userService.createUser(dto.getUsername(), dto.getEmail(),
// dto.getPassword()))
// .thenReturn(user);

// // Testando a rota
// mockMvc.perform(post("/users/users")
// .contentType(MediaType.APPLICATION_JSON)
// .content(objectMapper.writeValueAsString(dto)))
// .andExpect(status().isOk())
// .andExpect(jsonPath("$.id").value(1))
// .andExpect(jsonPath("$.username").value("bernardo"));
// }
// }
