package com.EBStudy.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLectureImg is a Querydsl query type for LectureImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLectureImg extends EntityPathBase<LectureImg> {

    private static final long serialVersionUID = 199578625L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLectureImg lectureImg = new QLectureImg("lectureImg");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgName = createString("imgName");

    public final StringPath imgUrl = createString("imgUrl");

    public final QLecture lecture;

    public final StringPath oriImgName = createString("oriImgName");

    public final StringPath repImgYn = createString("repImgYn");

    public QLectureImg(String variable) {
        this(LectureImg.class, forVariable(variable), INITS);
    }

    public QLectureImg(Path<? extends LectureImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLectureImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLectureImg(PathMetadata metadata, PathInits inits) {
        this(LectureImg.class, metadata, inits);
    }

    public QLectureImg(Class<? extends LectureImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lecture = inits.isInitialized("lecture") ? new QLecture(forProperty("lecture"), inits.get("lecture")) : null;
    }

}

