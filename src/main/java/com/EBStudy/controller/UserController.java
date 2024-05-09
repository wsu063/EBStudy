package com.EBStudy.controller;

import com.EBStudy.dto.LectureFormDto;
import com.EBStudy.dto.LectureIdxDto;
import com.EBStudy.dto.LectureSearchDto;
import com.EBStudy.dto.UserFormDto;
import com.EBStudy.entity.Lecture;
import com.EBStudy.entity.MyStudy;
import com.EBStudy.entity.StudyLecture;
import com.EBStudy.entity.User;
import com.EBStudy.repository.LectureRepository;
import com.EBStudy.repository.MyStudyRepository;
import com.EBStudy.repository.StudyLectureRepository;
import com.EBStudy.repository.UserRepository;
import com.EBStudy.service.LectureService;
import com.EBStudy.service.StudyLectureService;
import com.EBStudy.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final PasswordEncoder passwordEncoder;

    private final UserService userService;
    private final UserRepository userRepository;
    private final LectureService lectureService;
    private final LectureRepository lectureRepository;
    private final MyStudyRepository myStudyRepository;
    private final StudyLectureService  studyLectureService;
    private final StudyLectureRepository studyLectureRepository;

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
        model.addAttribute("userId", "");
        model.addAttribute("quizAnswer", "");
        return "user/userForm";
    }

    //회원가입 처리
    @PostMapping(value = "/users/new")
    public String userForm(@Valid UserFormDto userFormDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) return "user/userForm";

        try {
            User user = User.createUser(userFormDto, passwordEncoder);
            userService.saveUser(user);
            myStudyRepository.save(user.getMyStudy());

        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user/userform";
        }
        return "redirect:/";
    }

    @PostMapping(value = "/users/check")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> requestData) {
        String userId = requestData.get("userId");
        String quizAnswer = requestData.get("quizAnswer");

        // 아이디와 퀴즈 답변을 이용하여 비밀번호 재설정 가능 여부 확인
        boolean isValid = userService.checkQuizAnswer(userId, quizAnswer);

        if (isValid) {
            // 퀴즈 답변이 일치하는 경우, 비밀번호 재설정 페이지로 이동
            return new ResponseEntity<String>(userId, HttpStatus.OK);
        } else {
            // 퀴즈 답변이 일치하지 않는 경우, 에러 응답
            return new ResponseEntity<String>("퀴즈 답변이 틀렸습니다.", HttpStatus.BAD_REQUEST);
        }
    }



    //강의 상세페이지 for user
    @GetMapping(value = "/users/detail/{lectureId}")
    public String myStudyDetail(@PathVariable("lectureId") Long lectureId, Model model) {
        LectureFormDto lectureFormDto = lectureService.getLectureDtl(lectureId);
        model.addAttribute("lecture", lectureFormDto);
        model.addAttribute("lectureIdxDto", new LectureIdxDto());
        return "user/detail";
    }

    //강의 수강하기 for user
    @GetMapping(value = "/users/lectures/new/{lectureId}")
    public String addLecture(@PathVariable("lectureId") Long lectureId, Model model, Principal principal) {

        //수강할 사람과 수강할 강의를 찾는다.
        User user = userRepository.findByEmail(principal.getName());

        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(EntityNotFoundException::new);

        //저장한다.
        studyLectureService.saveStudyLecture(lecture, user);

        return "redirect:/";
    }

    //MyStudy for user
    @GetMapping(value = {"/users/myStudy", "/users/myStudy/{page}"})
    public String myStudyList(Principal principal, LectureSearchDto lectureSearchDto,
                              @PathVariable("page")Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);

        // 현재 수강중인 모든 강의를 찾는다.
        User user = userRepository.findByEmail(principal.getName());
        Long myStudyId = user.getMyStudy().getId();

        Page<StudyLecture> studyLectures = lectureRepository.getStudyLecturePage(pageable, myStudyId);

        model.addAttribute("studyLectures", studyLectures);

        model.addAttribute("maxPage", 5);


        return "user/myStudy";
    }

    //수강중인 강의 취소하기(delete)
    @DeleteMapping("/users/lectures/delete/{studyLectureId}")
    public @ResponseBody ResponseEntity deleteStudyLecture(@PathVariable("studyLectureId") Long studyLectureId) {

        studyLectureService.deleteStudyLecture(studyLectureId);

        return new ResponseEntity<Long>(studyLectureId, HttpStatus.OK);
    }

}
