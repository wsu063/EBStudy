package com.EBStudy.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMyStudy is a Querydsl query type for MyStudy
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMyStudy extends EntityPathBase<MyStudy> {

    private static final long serialVersionUID = -439417247L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMyStudy myStudy = new QMyStudy("myStudy");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUser user;

    public QMyStudy(String variable) {
        this(MyStudy.class, forVariable(variable), INITS);
    }

    public QMyStudy(Path<? extends MyStudy> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMyStudy(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMyStudy(PathMetadata metadata, PathInits inits) {
        this(MyStudy.class, metadata, inits);
    }

    public QMyStudy(Class<? extends MyStudy> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

