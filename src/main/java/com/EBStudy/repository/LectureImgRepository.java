package com.EBStudy.repository;

import com.EBStudy.entity.LectureImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureImgRepository extends JpaRepository<LectureImg, Long> {

    LectureImg findByLectureId(Long lectureId);

    LectureImg findByLectureIdAndRepImgYn(Long lectureId, String repImgYn);
}
