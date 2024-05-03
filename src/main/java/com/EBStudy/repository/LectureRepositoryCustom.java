package com.EBStudy.repository;

import com.EBStudy.dto.LectureFormDto;
import com.EBStudy.dto.LectureSearchDto;
import com.EBStudy.entity.Lecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LectureRepositoryCustom {
    Page<Lecture> getLecturePage(LectureSearchDto lectureSearchDto, Pageable pageable);

}
