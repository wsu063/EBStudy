package com.EBStudy.service;

import com.EBStudy.entity.LectureImg;
import com.EBStudy.repository.LectureImgRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class LectureImgService {
    @Value("${lectureImgLocation}")
    private String lectureImgLocation;

    private final LectureImgRepository lectureImgRepository;
    private final FileService fileService;

    public void saveLectureImg(LectureImg lectureImg, MultipartFile lectureImgFile) throws Exception {
        String oriImgName = lectureImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";
        if (!StringUtils.isEmpty(oriImgName)) { // 빈 문자열인지 아닌지 검사
            //빈 문자열이 아니면 업로드
            imgName = fileService.uploadFile(lectureImgLocation,
                    oriImgName, lectureImgFile.getBytes());
            //itemImgFile.getBytes(): 이미지 파일을 byte배열로 만들어준다.

            imgUrl = "/images/lecture/" + imgName;
        }
        //DB에 insert를 하기전 유저가 직접 입력하지 못하는 값들을 개발자가 넣어준다.
        lectureImg.updateLectureImg(oriImgName, imgName, imgUrl);
        lectureImgRepository.save(lectureImg); // insert
    }

    //이미지 수정
    public void updateLectureImg(Long lectureImgId, MultipartFile lectureImgFile) throws Exception {
        if(!lectureImgFile.isEmpty()) { // 첨부한 이미지 파일이 있다면
            //1. 서버있는 이미지 수정
            LectureImg savedLectureImg = lectureImgRepository.findById(lectureImgId)
                    .orElseThrow(EntityExistsException::new);

            if(!StringUtils.isEmpty(savedLectureImg.getImgName())) {
                fileService.deleteFile(lectureImgLocation + "/" + savedLectureImg.getImgName());
            }

            String oriImgName = lectureImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(lectureImgLocation, oriImgName, lectureImgFile.getBytes());
            String imgUrl = "/images/lecture/" + imgName;

            //2. lecture_img 테이블 수정
            savedLectureImg.updateLectureImg(oriImgName, imgName, imgUrl);

        }
    }
}
