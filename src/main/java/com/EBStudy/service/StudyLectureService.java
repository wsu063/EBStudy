package com.EBStudy.service;

import com.EBStudy.dto.LectureSearchDto;
import com.EBStudy.entity.Lecture;
import com.EBStudy.entity.StudyLecture;
import com.EBStudy.entity.User;
import com.EBStudy.repository.LectureRepository;
import com.EBStudy.repository.StudyLectureRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyLectureService {
    private final StudyLectureRepository studyLectureRepository;
    private final LectureRepository lectureRepository;

    //insert 저장
    public Long saveStudyLecture(Lecture lecture, User user) {
        // StudyLecture를 만든다.
        StudyLecture studyLecture = StudyLecture.createStudyLecture(lecture, user);

        // 중복된 강의인지 확인한다.
        for(StudyLecture sL : user.getMyStudy().getStudyLectures()) {
            if (Objects.equals(sL.getLecture().getId(), lecture.getId())) {
                return 0L;
            }
        }
        // 모두 완료되면 저장하고, MyStudy에 StudyLecture를 추가한다.
        studyLectureRepository.save(studyLecture);
        user.getMyStudy().getStudyLectures().add(studyLecture);
        return studyLecture.getId();
    }
    //delete 삭제
    public void deleteStudyLecture(Long studyLectureId) {
            StudyLecture studyLecture = studyLectureRepository.findById(studyLectureId)
                    .orElseThrow(EntityNotFoundException::new);

            studyLectureRepository.delete(studyLecture);
    }



}
