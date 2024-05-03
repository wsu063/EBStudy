package com.EBStudy.dto;

import com.EBStudy.entity.LectureImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class LectureImgDto {
    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYN;

    private static ModelMapper modelMapper = new ModelMapper();

    // entity -> dto
    public static LectureImgDto of(LectureImg lectureImg) {
        return modelMapper.map(lectureImg, LectureImgDto.class); // BoardImgDto 객체를 리턴
    }
}
