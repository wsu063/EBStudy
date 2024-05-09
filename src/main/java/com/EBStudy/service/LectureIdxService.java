package com.EBStudy.service;

import com.EBStudy.dto.LectureFormDto;
import com.EBStudy.dto.LectureIdxDto;
import com.EBStudy.entity.Lecture;
import com.EBStudy.entity.LectureIdx;
import com.EBStudy.entity.LectureImg;
import com.EBStudy.repository.LectureIdxRepository;
import com.EBStudy.repository.LectureRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LectureIdxService {
    private final LectureIdxRepository lectureIdxRepository;
    private final LectureRepository lectureRepository;

    //deleteIndex
    public void deleteIndex(Long indexId) {
        LectureIdx index = lectureIdxRepository.findById(indexId)
                .orElseThrow(EntityNotFoundException::new);

        lectureIdxRepository.delete(index);
    }
    //insert
    public Long saveIndex(LectureIdxDto lectureIdxDto, Long lectureId) throws Exception {

        //1. 목차 등록
        LectureIdx lectureIdx = lectureIdxDto.createIndex();
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(EntityNotFoundException::new);
        lectureIdx.setLecture(lecture);
        lectureIdx.setIndexYn("N");
        lectureIdxRepository.save(lectureIdx);

        return lectureIdxDto.getId();
    }
}
