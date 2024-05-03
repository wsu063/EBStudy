package com.EBStudy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="lecture_idx")
@Getter
@Setter
@ToString
public class LectureIdx {
    @Id
    @Column(name = "lecture_idx_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //idxName: 01.안녕
    private String idxName;

    private String videoUrl;

    private String indexYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    public void updateLectureIdx(String idxName, String videoUrl, String indexYn) {
        this.idxName = idxName;
        this.videoUrl = videoUrl;
        this.indexYn = indexYn;
    }
}
