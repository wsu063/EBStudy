package com.EBStudy.repository;

import com.EBStudy.dto.LectureFormDto;
import com.EBStudy.dto.LectureSearchDto;
import com.EBStudy.entity.Lecture;
import com.EBStudy.entity.MyStudy;
import com.EBStudy.entity.StudyLecture;
import com.EBStudy.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LectureRepositoryCustom {
    Page<Lecture> getLecturePage(LectureSearchDto lectureSearchDto, Pageable pageable);

    List<Lecture> getLectureAll();

    Page<StudyLecture> getStudyLecturePage(Pageable pageable, Long myStudyId);
}
