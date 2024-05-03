package com.EBStudy.repository;

import com.EBStudy.entity.LectureIdx;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureIdxRepository extends JpaRepository<LectureIdx, Long> {
    List<LectureIdx> findByLectureIdOrderByIdAsc(Long lectureId);
}
