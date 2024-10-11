package com.domain.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.domain.demo.entity.User;
import com.domain.demo.repository.UserRepository;

@SpringBootTest
@Transactional
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void clearDatabase() {
        userRepository.deleteAll(); // 테스트 실행 전 데이터베이스 초기화 : 중복 문제 해결
    }

    @Test
    public void saveUserInDatabase() {
        // given
        User user = new User("uniqueUser", "testPassword", "unique@example.com", "Test User");

        // when
        User savedUser = userRepository.save(user);

        // then
        assertNotNull(savedUser, "User should not be null after saving");
        assertNotNull(savedUser.getId(), "User ID should not be null after saving");
    }

    @Test
    void authenticateUser_Success() {
        String username = "testuser";
        String rawPassword = "password";
        String encodedPassword = "encodedPassword"; // 암호화된 비밀번호

        User user = new User(username, encodedPassword, "testuser@example.com", "Test User");
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        assertTrue(userService.authenticateUser(username, rawPassword), "User should be authenticated");
    }

    @Test
    void authenticateUser_Failure() {
        String username = "testuser";
        String rawPassword = "wrongpassword";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertFalse(userService.authenticateUser(username, rawPassword), "User should not be authenticated");
    }
    
}
