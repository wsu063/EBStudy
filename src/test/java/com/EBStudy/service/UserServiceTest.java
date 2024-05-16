package com.EBStudy.service;

import com.EBStudy.dto.UserFormDto;
import com.EBStudy.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User createUser() {
        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setEmail("test10@gmail.com");
        userFormDto.setName("테스트");
        userFormDto.setQuiz("test");
        userFormDto.setPassword("1234");

        return User.createUser(userFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveUserTest() {
        User user = createUser();
        User savedUser = userService.saveUser(user);

        Assertions.assertEquals(user.getEmail(), savedUser.getEmail());
        Assertions.assertEquals(user.getName(), savedUser.getName());
        Assertions.assertEquals(user.getQuiz(), savedUser.getQuiz());
        Assertions.assertEquals(user.getPassword(), savedUser.getPassword());
        Assertions.assertEquals(user.getRole(), savedUser.getRole());
    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void validateDuplicateUserTest() {
        User user1 = createUser();
        User user2 = createUser();

        userService.saveUser(user1);

        Throwable e = Assertions.assertThrows(IllegalStateException.class, () -> {
            userService.saveUser(user2);
        });

        Assertions.assertEquals("이미 가입된 회원입니다.", e.getMessage());
    }
}
