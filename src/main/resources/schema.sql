create table if not exists CONSUMER
(
    CONSUMER_ID BIGINT            not null,
    EMAIL       CHARACTER VARYING not null,
    LOGIN       CHARACTER VARYING not null,
    NAME        CHARACTER VARYING,
    BIRTHDAY    DATE,
    constraint "CONSUMER_pk"
        primary key (CONSUMER_ID)
);

create table if not exists FRINDSHIP
(
    FRINDSHIP_ID       BIGINT not null,
    SOURCE_CONSUMER_ID BIGINT not null,
    TARGET_CONSUMER_ID BIGINT not null,
    STATUS             CHARACTER VARYING,
    constraint FRINDSHIP_PK
        primary key (FRINDSHIP_ID),
    constraint "FRINDSHIP_CONSUMER_INITIATOR_fk"
        foreign key (SOURCE_CONSUMER_ID) references CONSUMER,
    constraint "FRINDSHIP_CONSUMER_TARGET_fk"
        foreign key (TARGET_CONSUMER_ID) references CONSUMER
);


create table if not exists GENRE
(
    GENRE_ID BIGINT  not null,
    NAME     INTEGER not null,
    constraint GENRE_PK
        primary key (GENRE_ID)
);

create table if not exists RATING
(
    "rating_id   " BIGINT            not null,
    RATING_TYPE    CHARACTER VARYING not null,
    constraint RATING_PK
        primary key ("rating_id   ")
);

create table if not exists FILM
(
    FILM_ID      BIGINT            not null,
    RATING_ID    BIGINT            not null,
    GENRE_ID     BIGINT            not null,
    NAME         CHARACTER VARYING not null,
    DESCRIPTION  CHARACTER VARYING,
    DURATION     CHARACTER VARYING,
    RELEASE_DATE DATE,
    COLUMN_NAME  CHARACTER VARYING,
    constraint FILM_PK
        primary key (FILM_ID),
    constraint "film_GENRE_fk"
        foreign key (GENRE_ID) references GENRE,
    constraint "film_RATING_fk"
        foreign key (RATING_ID) references RATING
);

create table if not exists LIKES
(
    LIKES_ID    BIGINT not null,
    FILM_ID     BIGINT not null,
    CONSUMER_ID BIGINT not null,
    constraint LIKES_PK
        primary key (LIKES_ID),
    constraint "likes_CONSUMER_null_fk"
        foreign key (CONSUMER_ID) references CONSUMER,
    constraint "likes_FILM_null_fk"
        foreign key (FILM_ID) references FILM
);

