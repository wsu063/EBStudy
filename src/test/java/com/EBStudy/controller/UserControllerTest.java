package com.EBStudy.controller;

import com.EBStudy.dto.UserFormDto;
import com.EBStudy.entity.User;
import com.EBStudy.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional // 트랜잭션 처리: 중간에 에러 발생시 rollback을 시켜준다.
@AutoConfigureMockMvc // MockMvc 테스트를 위해 어노테이션 선언
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User createUser(String email, String password){
        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setEmail(email);
        userFormDto.setName("홍길동");
        userFormDto.setPassword(password);
        User user = User.createUser(userFormDto, passwordEncoder);
        return userService.saveUser(user);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception {
        String email = "test10@gmail.com";
        String password = "1234";
        createUser(email, password);

        mockMvc.perform(SecurityMockMvcRequestBuilders
                        .formLogin().userParameter("email")
                        .loginProcessingUrl("/users/login") // 로그인 처리할 경로.
                        .user(email).password(password)) // 아이디와 비밀번호로 로그인처리
                .andExpect(SecurityMockMvcResultMatchers.authenticated()); // 로그인 성공하면 테스트코드 통과
    }

    @Test
    @DisplayName("로그인 실패테스트")
    public void loginFailTest() throws Exception {
        String email = "test10@gmail.com";
        String password = "1234";
        createUser(email, password);

        mockMvc.perform(SecurityMockMvcRequestBuilders
                        .formLogin().userParameter("email")
                        .loginProcessingUrl("/users/login") // 로그인 처리할 경로.
                        .user(email).password("12345")) // 아이디와 비밀번호로 로그인처리
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated()); // 로그인 실패하면 테스트코드 통과
    }
}
