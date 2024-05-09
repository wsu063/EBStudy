package com.EBStudy.dto;

import com.EBStudy.constant.Subject;
import com.EBStudy.entity.Lecture;
import com.EBStudy.entity.LectureImg;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LectureFormDto {
    private Long id;

    @NotBlank(message = "제목은 필수 입력입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력입니다.")
    private String content;
    
    private Subject subject;

    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private LocalDateTime endDate; // regDate + @ 입력받아서 해야될듯? int로할지 LDT로할지고민

    private String createdBy;
    private String modifiedBy;

    private String name;

    private LectureImgDto lectureImgDto = new LectureImgDto();
    private List<LectureIdxDto> lectureIdxDtoList = new ArrayList<>();

    private Long lectureImgId;

    private static ModelMapper modelMapper = new ModelMapper();

    //dto -> entity
    public Lecture createLecture() {
            Lecture lecture = new Lecture();
            lecture.setId(this.id);
            lecture.setTitle(this.title);
            lecture.setContent(this.content);
            lecture.setSubject(this.subject);
            lecture.setEndDate(this.endDate);
            lecture.setName(this.name);

            if (this.lectureImgDto != null) {
                LectureImg lectureImg = new LectureImg();
                lectureImg.setImgUrl(this.lectureImgDto.getImgUrl());
                lectureImg.setImgName(this.lectureImgDto.getImgName());
                lecture.setLectureImg(lectureImg);
            }

            return lecture;

    }

    //entity -> dto
    public static LectureFormDto of(Lecture lecture) {
        return modelMapper.map(lecture, LectureFormDto.class);
    }

}
