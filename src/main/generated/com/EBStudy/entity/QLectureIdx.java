package com.EBStudy.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLectureIdx is a Querydsl query type for LectureIdx
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLectureIdx extends EntityPathBase<LectureIdx> {

    private static final long serialVersionUID = 199578363L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLectureIdx lectureIdx = new QLectureIdx("lectureIdx");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath idxName = createString("idxName");

    public final StringPath indexYn = createString("indexYn");

    public final QLecture lecture;

    public final StringPath videoUrl = createString("videoUrl");

    public QLectureIdx(String variable) {
        this(LectureIdx.class, forVariable(variable), INITS);
    }

    public QLectureIdx(Path<? extends LectureIdx> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLectureIdx(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLectureIdx(PathMetadata metadata, PathInits inits) {
        this(LectureIdx.class, metadata, inits);
    }

    public QLectureIdx(Class<? extends LectureIdx> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lecture = inits.isInitialized("lecture") ? new QLecture(forProperty("lecture"), inits.get("lecture")) : null;
    }

}

