package com.EBStudy.entity;

import com.EBStudy.constant.Subject;
import com.EBStudy.dto.LectureFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "lecture")
@Getter
@Setter
@ToString
public class Lecture extends BaseEntity {
    @Id
    @Column(name = "lecture_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    private Subject subject;

    private LocalDateTime endDate;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LectureImg> lectrueImgs;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LectureIdx> lectrueIdxs;

    //lecture엔티티 수정
    public void updateLecture(LectureFormDto lectureFormDto) {
        this.title = lectureFormDto.getTitle();
        this.content = lectureFormDto.getContent();
        this.subject = lectureFormDto.getSubject();
    }
}
