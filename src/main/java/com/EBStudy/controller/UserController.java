package com.EBStudy.controller;

import com.EBStudy.dto.UserFormDto;
import com.EBStudy.entity.User;
import com.EBStudy.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    //로그인 화면
    @GetMapping(value="/users/login")
    public String loginUser() {
        return "user/userLoginForm";
    }

    //로그인 에러 화면
    @GetMapping(value = "/users/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg",
                "아이디 또는 비밀번호를 확인해주세요.");
        return "user/userLoginForm";
    }


    //회원가입 화면
    @GetMapping(value = "/users/new")
    public String userForm(Model model) {
        model.addAttribute("userFormDto", new UserFormDto());
        return "user/userForm";
    }

    //회원가입 처리
    @PostMapping(value = "/users/new")
    public String userForm(@Valid UserFormDto userFormDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) return "user/userForm";

        try {
            User user = User.createUser(userFormDto, passwordEncoder);
            userService.saveUser(user);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user/userform";
        }
        return "redirect:/";

    }

}
