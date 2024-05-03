package com.EBStudy.dto;

import com.EBStudy.constant.Subject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LectureSearchDto {
    private String searchDateType;
    private Subject searchSubject;
    private String searchBy;
    private String searchQuery = "";
}
