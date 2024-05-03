package com.EBStudy.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudyLecture is a Querydsl query type for StudyLecture
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudyLecture extends EntityPathBase<StudyLecture> {

    private static final long serialVersionUID = 1187976977L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudyLecture studyLecture = new QStudyLecture("studyLecture");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLecture lecture;

    public final QMyStudy myStudy;

    public QStudyLecture(String variable) {
        this(StudyLecture.class, forVariable(variable), INITS);
    }

    public QStudyLecture(Path<? extends StudyLecture> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudyLecture(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudyLecture(PathMetadata metadata, PathInits inits) {
        this(StudyLecture.class, metadata, inits);
    }

    public QStudyLecture(Class<? extends StudyLecture> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lecture = inits.isInitialized("lecture") ? new QLecture(forProperty("lecture")) : null;
        this.myStudy = inits.isInitialized("myStudy") ? new QMyStudy(forProperty("myStudy"), inits.get("myStudy")) : null;
    }

}

