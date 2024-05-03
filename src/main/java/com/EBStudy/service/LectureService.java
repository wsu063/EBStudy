package com.EBStudy.service;

import com.EBStudy.dto.LectureFormDto;
import com.EBStudy.dto.LectureIdxDto;
import com.EBStudy.dto.LectureImgDto;
import com.EBStudy.dto.LectureSearchDto;
import com.EBStudy.entity.Lecture;
import com.EBStudy.entity.LectureIdx;
import com.EBStudy.entity.LectureImg;
import com.EBStudy.entity.User;
import com.EBStudy.repository.LectureIdxRepository;
import com.EBStudy.repository.LectureImgRepository;
import com.EBStudy.repository.LectureRepository;
import com.EBStudy.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LectureService {
    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;
    private final LectureImgService lectureImgService;
    private final LectureImgRepository lectureImgRepository;
    private final LectureIdxRepository lectureIdxRepository;

    //insert
    public Long saveLecture(LectureFormDto lectureFormDto,
                            MultipartFile lectureImgFile) throws Exception {

        //1. 상품 등록
        Lecture lecture = lectureFormDto.createLecture();

        lecture.setEndDate(LocalDateTime.now().plusDays(30));
        lectureRepository.save(lecture);
        // 2. 이미지 등록
        LectureImg lectureImg = new LectureImg();
        lectureImg.setLecture(lecture); // ★lectureImg가 lecture를 참조하므로 반드시 item 객체를 입력해준다.
        lectureImg.setRepImgYn("Y");

        //이미지 파일을 하나씩 저장
        lectureImgService.saveLectureImg(lectureImg, lectureImgFile);

        return lectureFormDto.getId();
    }


    //select(글 상세 가져오기)
    @Transactional(readOnly = true)
    public LectureFormDto getLectureDtl(Long lectureId) {
        //1. 이미지, 인덱스 가져오기
        LectureImg lectureImg = lectureImgRepository.findByLectureId(lectureId);
        List<LectureIdx> lectureIdxList = lectureIdxRepository.findByLectureIdOrderByIdAsc(lectureId);
        //-1. entitiy-> dto 변환
        List<LectureImgDto> lectureImgDtoList = new ArrayList<>();

        LectureImgDto lectureImgDto = LectureImgDto.of(lectureImg);
        lectureImgDtoList.add(lectureImgDto);

        List<LectureIdxDto> lectureIdxDtoList = new ArrayList<>();
        for(LectureIdx lectureIdx : lectureIdxList) {
            LectureIdxDto lectureIdxDto = LectureIdxDto.of(lectureIdx);
            lectureIdxDtoList.add(lectureIdxDto);
        }


        //2. lecture 가져오기
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(EntityNotFoundException::new);
        //-1. entity -> dto
        LectureFormDto lectureFormDto = LectureFormDto.of(lecture);

        //3. 강의 dto에 이미지,인덱스 리스트를 넣어준다.
        lectureFormDto.setLectureImgDto(lectureImgDto);
        lectureFormDto.setLectureIdxDtoList(lectureIdxDtoList);

        return lectureFormDto;
    }

    //select(글 목록 가져오기)
    public Page<Lecture> getLecturePage(LectureSearchDto lectureSearchDto, Pageable pageable) {
        Page<Lecture> lecturePage = lectureRepository.getLecturePage(lectureSearchDto, pageable);

        return lecturePage;
    }

    //update
    public Long updateLecture(LectureFormDto lectureFormDto, MultipartFile lectureImgFile) throws  Exception {
        Lecture lecture = lectureRepository.findById(lectureFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        lecture.updateLecture(lectureFormDto);

        Long lectureImgId = lectureFormDto.getLectureImgId();


        lectureImgService.updateLectureImg(lectureImgId, lectureImgFile);


        return lecture.getId();
    }

    //delete
    public void deleteLecture(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(EntityNotFoundException::new);

        lectureRepository.delete(lecture);
    }





    //본인 확인
    @Transactional(readOnly = true)
    public boolean validateUser(Long boardId, String email) {
        //현재 로그인한 사용자
        User curUser = userRepository.findByEmail(email);

        //게시글 찾기
        Lecture lecture = lectureRepository.findById(boardId)
                .orElseThrow(EntityNotFoundException::new);

        //게시글 작성자 찾기
        String lectureUserNm = lecture.getCreatedBy();

        if (!StringUtils.equals(curUser.getEmail(), lectureUserNm)) {
            return false;
        }
        return true;
    }
}
