package com.EBStudy.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLecture is a Querydsl query type for Lecture
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLecture extends EntityPathBase<Lecture> {

    private static final long serialVersionUID = -1884727198L;

    public static final QLecture lecture = new QLecture("lecture");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final DateTimePath<java.time.LocalDateTime> endDate = createDateTime("endDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<LectureIdx, QLectureIdx> lectrueIdxs = this.<LectureIdx, QLectureIdx>createList("lectrueIdxs", LectureIdx.class, QLectureIdx.class, PathInits.DIRECT2);

    public final ListPath<LectureImg, QLectureImg> lectrueImgs = this.<LectureImg, QLectureImg>createList("lectrueImgs", LectureImg.class, QLectureImg.class, PathInits.DIRECT2);

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final EnumPath<com.EBStudy.constant.Subject> subject = createEnum("subject", com.EBStudy.constant.Subject.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QLecture(String variable) {
        super(Lecture.class, forVariable(variable));
    }

    public QLecture(Path<? extends Lecture> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLecture(PathMetadata metadata) {
        super(Lecture.class, metadata);
    }

}

