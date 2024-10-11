package com.domain.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.domain.demo.config.SecurityConfig;
import com.domain.demo.entity.User;
import com.domain.demo.service.UserService;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)  // SecurityConfig 포함
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void createUser() throws Exception {
        User user = new User("username", "password", "email@example.com", null);

        when(userService.saveUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"username\", \"password\": \"password\", \"email\": \"email@example.com\"}")
                .with(csrf())) 
                .andExpect(status().isCreated())  // 상태 코드 201을 기대
                .andExpect(jsonPath("$.username").value("username"));
    }

    @Test
    void getAllUsers() throws Exception {
        // 가상의 유저 리스트 생성
        User user1 = new User("username1", "password1", "email1@example.com", "User One");
        User user2 = new User("username2", "password2", "email2@example.com", "User Two");

        when(userService.findAllUsers()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/api/users/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username", is("username1")))
                .andExpect(jsonPath("$[1].username", is("username2")));
    }
}
