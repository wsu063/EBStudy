package com.EBStudy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mystudy")
@Getter
@Setter
@ToString
public class MyStudy {
    @Id
    @Column(name = "mystudy_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "myStudy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudyLecture> studyLectures = new ArrayList<>();

    //추가하기
    public void addMyLecture(StudyLecture studyLecture) {
        studyLectures.add(studyLecture);
        studyLecture.setMyStudy(this);
    }

    //생성하기
    public static MyStudy createMyStudy(User user) {
        MyStudy myStudy = new MyStudy();
        myStudy.setUser(user);

        return myStudy;
    }

}
