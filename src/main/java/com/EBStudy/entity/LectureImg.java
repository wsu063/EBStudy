package com.EBStudy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="lecture_img")
@Getter
@Setter
@ToString
public class LectureImg {
    @Id
    @Column(name = "lecture_img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    public void updateLectureImg(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
