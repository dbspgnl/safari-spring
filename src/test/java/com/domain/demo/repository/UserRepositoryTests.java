package com.domain.demo.repository;

import com.domain.demo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findUserByUsername() {
        // given
        String username = "testuser";
        User user = new User(username, "testpassword", "testuser@example.com", "Test User");
        userRepository.save(user); // 데이터베이스에 사용자 저장

        // when
        Optional<User> foundUser = userRepository.findByUsername(username);

        // then
        assertTrue(foundUser.isPresent(), "User should be found");
        assertEquals(username, foundUser.get().getUsername(), "Usernames should match");
    }
}
