package com.EBStudy.repository;

import com.EBStudy.dto.LectureFormDto;
import com.EBStudy.entity.Lecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface LectureRepository extends JpaRepository<Lecture, Long>,
QuerydslPredicateExecutor<Lecture>, LectureRepositoryCustom {

}
