package com.EBStudy.dto;

import com.EBStudy.entity.Lecture;
import com.EBStudy.entity.LectureIdx;
import com.EBStudy.entity.LectureImg;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class LectureIdxDto {

    private Long id;

    @NotNull(message = "제목은 필수 입력입니다.")
    private String idxName;

    @NotNull(message = "경로는 필수 입력입니다.")
    private String videoUrl;

    private String indexYn;

    private static ModelMapper modelMapper = new ModelMapper();

    // entity -> dto
    public static LectureIdxDto of(LectureIdx lectureIdx) {
        return modelMapper.map(lectureIdx, LectureIdxDto.class); // BoardImgDto 객체를 리턴
    }

    //dto -> entity
    public LectureIdx createIndex() {
        return modelMapper.map(this, LectureIdx.class);
    }

}
