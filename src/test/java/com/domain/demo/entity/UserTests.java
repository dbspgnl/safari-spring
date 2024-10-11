package com.domain.demo.entity;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class UserTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void passwordEncryption() {
        String rawPassword = "password123";
        String encryptedPassword = passwordEncoder.encode(rawPassword);

        assertNotEquals(rawPassword, encryptedPassword);
        assertTrue(passwordEncoder.matches(rawPassword, encryptedPassword));
    }
}
