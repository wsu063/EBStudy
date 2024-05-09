package com.EBStudy.controller;

import com.EBStudy.dto.LectureFormDto;
import com.EBStudy.dto.LectureIdxDto;
import com.EBStudy.dto.LectureSearchDto;
import com.EBStudy.entity.Lecture;
import com.EBStudy.repository.LectureRepository;
import com.EBStudy.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final LectureService lectureService;
    private final LectureRepository lectureRepository;

    @GetMapping(value = "/")
    public String index(Model model) {
        List<Lecture> lectureList = lectureRepository.getLectureAll();
        List<LectureFormDto> lectures = new ArrayList<>();
        for(Lecture lecture : lectureList) {
            LectureFormDto lectureFormDto = lectureService.getLectureDtl(lecture.getId());
            lectures.add(lectureFormDto);
        }
        int maxLectures = Math.min(lectures.size(), 8); // 최대 8개의 강의만 유지
        model.addAttribute("lectures", lectures.subList(0, maxLectures));

        return "index";
    }

    //상세 페이지
    @GetMapping(value = "/lectures/detail/{lectureId}")
    private String lectureDetail(Model model, @PathVariable(value = "lectureId") Long lectureId, Principal principal) {
        LectureFormDto lectureFormDto = lectureService.getLectureDtl(lectureId);
        model.addAttribute("lecture", lectureFormDto);
        model.addAttribute("lectureIdxDto", new LectureIdxDto());
        model.addAttribute("userId", principal.getName()); // 필요한가?
        return "lecture/detail";
    }
}
