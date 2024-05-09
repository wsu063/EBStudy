package com.EBStudy.controller;

import com.EBStudy.dto.LectureFormDto;
import com.EBStudy.dto.LectureIdxDto;
import com.EBStudy.dto.LectureSearchDto;
import com.EBStudy.entity.Lecture;
import com.EBStudy.service.LectureIdxService;
import com.EBStudy.service.LectureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LectureController {
    private final LectureService lectureService;
    private final LectureIdxService lectureIdxService;

    //강의 등록
    @GetMapping(value = "/admin/lectures/new")
    public String write(Model model) {
        model.addAttribute("lectureFormDto", new LectureFormDto());

        return "lecture/write";
    }

    @PostMapping(value = "/admin/lectures/new")
    public String insertLecture(@Valid LectureFormDto lectureFormDto, BindingResult bindingResult,
                                Model model, @RequestParam("lectureImgFile")MultipartFile lectureImgFile) {
        if (bindingResult.hasErrors()) return "lecture/write";

        try {
            lectureService.saveLecture(lectureFormDto, lectureImgFile);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "강의 등록 중 에러가 발생했습니다.");
            return "lecture/write";
        }
        return "redirect:/";
    }

    //수정 페이지
    @GetMapping(value = "/admin/lectures/rewrite/{lectureId}")
    private String lectureDtl(@PathVariable("lectureId") Long lectureId, Model model) {
        try {
            LectureFormDto lectureFormDto = lectureService.getLectureDtl(lectureId);
            model.addAttribute("lectureFormDto", lectureFormDto);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "강의 정보를 가져오는 도중 에러가 발생했습니다.");
            model.addAttribute("lectureFormDto", new LectureFormDto());
            return "lecture/rewrite";

        }
        return "lecture/rewrite";
    }

    @PostMapping(value = "/admin/lectures/rewrite/{lectureId}")
    public String lectureUpdate(@Valid LectureFormDto lectureFormDto, Model model,
                                BindingResult bindingResult,
                                @RequestParam("lectureImgFile") MultipartFile lectureImgFile,
                                @PathVariable("lectureId") Long lectureId) {
        if(bindingResult.hasErrors()) return "lecture/write";

        LectureFormDto getLectureFormDto = lectureService.getLectureDtl(lectureId);

        try {
            lectureService.updateLecture(lectureFormDto, lectureImgFile);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "강의 정보를 가져오는 도중 에러가 발생했습니다.");

            model.addAttribute("lectureFormDto", getLectureFormDto);
            return "lecture/rewrite";
        }

        return "redirect:/admin/lectures/detail/" + lectureId;
    }

    //목록 가져오기
    @GetMapping(value = {"/admin/lectures/list", "admin/lectures/list/{page}"})
    public String lectureList(LectureSearchDto lectureSearchDto, @PathVariable("page")Optional<Integer> page,
                              Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);

        Page<Lecture> lectures = lectureService.getLecturePage(lectureSearchDto, pageable);

        model.addAttribute("lectures", lectures);
        model.addAttribute("lectureSearchDto", lectureSearchDto);

        model.addAttribute("maxPage", 5);

        return "lecture/list";
    }
    
    //상세 페이지
    @GetMapping(value = "/admin/lectures/detail/{lectureId}")
    private String lectureDetail(Model model, @PathVariable(value = "lectureId") Long lectureId, Principal principal) {
        LectureFormDto lectureFormDto = lectureService.getLectureDtl(lectureId);
        model.addAttribute("lecture", lectureFormDto);
        model.addAttribute("lectureIdxDto", new LectureIdxDto());
        model.addAttribute("userId", principal.getName());
        return "lecture/detail";
    }

    //강좌 삭제
    @DeleteMapping("/admin/lectures/delete/{lectureId}")
    public @ResponseBody ResponseEntity deleteLecture(@PathVariable("lectureId") Long lectureId, Principal principal) {
        if(!lectureService.validateUser(lectureId, principal.getName())) {
            return new ResponseEntity<String>("삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        lectureService.deleteLecture(lectureId);

        return new ResponseEntity<Long>(lectureId, HttpStatus.OK);
    }

    //목차 등록
    @PostMapping(value = "/admin/lectures/index/new")
    public String insertInput(@Valid LectureIdxDto lectureIdxDto, BindingResult bindingResult,
                              Model model, @RequestParam("lectureId") Long lectureId) {
        if (bindingResult.hasErrors()) return "lecture/write";

        try {
            lectureIdxService.saveIndex(lectureIdxDto, lectureId);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "강의 등록 중 에러가 발생했습니다.");
            return "lecture/write";
        }
        return "redirect:/admin/lectures/detail/" + lectureId;
    }

    //목차 삭제
    @DeleteMapping("/admin/lectures/index/delete/{indexId}")
    public @ResponseBody ResponseEntity deleteIndex(@PathVariable("indexId") Long indexId,
                                                    Principal principal) {
        lectureIdxService.deleteIndex(indexId);

        return new ResponseEntity<Long>(indexId, HttpStatus.OK);
    }


}
