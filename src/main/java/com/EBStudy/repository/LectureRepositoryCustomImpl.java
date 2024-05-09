package com.EBStudy.repository;

import com.EBStudy.constant.Subject;
import com.EBStudy.dto.LectureFormDto;
import com.EBStudy.dto.LectureSearchDto;
import com.EBStudy.entity.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

public class LectureRepositoryCustomImpl implements LectureRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public LectureRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    //현재 날짜로부터 이전 날짜를 구해주는 메소드
    private BooleanExpression regDtsAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now(); // 현재 날짜, 시간

        if(StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if (StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if (StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if (StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }

        return QLecture.lecture.regDate.after(dateTime); // 몇일전 이후부터
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if(StringUtils.equals("title", searchBy)) { // 글제목 검색
            return QLecture.lecture.title.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("createdBy", searchBy)) { // 작성자 검색시
            return QLecture.lecture.createdBy.like("%" + searchQuery + "%");
        }
        return null;
    }
    //전체검색할때 null이 있으므로 처리한다
    private BooleanExpression searchSubjectEq(Subject subject) {
        return subject == null ? null : QLecture.lecture.subject.eq(subject);
    }

    private BooleanExpression titleLike(String searchQuery) {
        return StringUtils.isEmpty(searchQuery) ? null : QLecture.lecture.title.like("%" + searchQuery + "%");
    }



    @Override
    public List<Lecture> getLectureAll() {
        List<Lecture> content = queryFactory
                .selectFrom(QLecture.lecture)
                .orderBy(QLecture.lecture.regDate.desc())
                .fetch();

        return content;
    }

    @Override
    public Page<Lecture> getLecturePage(LectureSearchDto lectureSearchDto, Pageable pageable) {
        List<Lecture> content = queryFactory
                .selectFrom(QLecture.lecture)
                .where(regDtsAfter(lectureSearchDto.getSearchDateType()),
                        searchSubjectEq(lectureSearchDto.getSearchSubject()),
                        searchByLike(lectureSearchDto.getSearchBy(), lectureSearchDto.getSearchQuery()))
                .orderBy(QLecture.lecture.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(QLecture.lecture)
                .where(regDtsAfter(lectureSearchDto.getSearchDateType()),
                        searchSubjectEq(lectureSearchDto.getSearchSubject()),
                        searchByLike(lectureSearchDto.getSearchBy(), lectureSearchDto.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<StudyLecture> getStudyLecturePage(Pageable pageable, Long myStudyId) {
        List<StudyLecture> content = queryFactory
                .selectFrom(QStudyLecture.studyLecture)
                .where(QStudyLecture.studyLecture.myStudy.id.eq(myStudyId))
                .orderBy(QStudyLecture.studyLecture.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(QStudyLecture.studyLecture)
                .where(QStudyLecture.studyLecture.myStudy.id.eq(myStudyId))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }
}
