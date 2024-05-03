package com.EBStudy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="study_lecture")
@Getter
@Setter
@ToString
public class StudyLecture {
    @Id
    @Column(name = "study_lecture")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mystudy_id")
    private MyStudy myStudy;
}
