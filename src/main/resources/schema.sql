create table if not exists CONSUMER
(
    CONSUMER_ID BIGINT auto_increment,
    EMAIL       CHARACTER(255) not null,
    LOGIN       CHARACTER(255) not null,
    NAME        CHARACTER(255),
    BIRTHDAY    DATE,
    constraint "CONSUMER_pk"
        primary key (CONSUMER_ID)
);

create table if not exists FRINDSHIP
(
    FRINDSHIP_ID       BIGINT auto_increment,
    SOURCE_CONSUMER_ID BIGINT not null,
    TARGET_CONSUMER_ID BIGINT not null,
    STATUS             CHARACTER(255),
    constraint FRINDSHIP_PK
        primary key (FRINDSHIP_ID),
    constraint "FRINDSHIP_CONSUMER_INITIATOR_fk"
        foreign key (SOURCE_CONSUMER_ID) references CONSUMER,
    constraint "FRINDSHIP_CONSUMER_TARGET_fk"
        foreign key (TARGET_CONSUMER_ID) references CONSUMER
);

create table if not exists GENRE
(
    GENRE_ID BIGINT auto_increment,
    NAME     CHARACTER(50) not null,
    constraint GENRE_PK
        primary key (GENRE_ID)
);

create table if not exists RATING
(
    "rating_id   " BIGINT auto_increment,
    RATING_TYPE    CHARACTER(50) not null,
    constraint RATING_PK
        primary key ("rating_id   ")
);

create table if not exists FILM
(
    FILM_ID      BIGINT auto_increment,
    RATING_ID    BIGINT            not null,
    GENRE_ID     BIGINT            not null,
    NAME         CHARACTER(255) not null,
    DESCRIPTION  CHARACTER(200),
    DURATION     CHARACTER(255),
    RELEASE_DATE DATE,
    COLUMN_NAME  CHARACTER(255),
    constraint FILM_PK
        primary key (FILM_ID),
    constraint "film_GENRE_fk"
        foreign key (GENRE_ID) references GENRE,
    constraint "film_RATING_fk"
        foreign key (RATING_ID) references RATING
);

create table if not exists LIKES
(
    LIKES_ID    BIGINT auto_increment,
    FILM_ID     BIGINT not null,
    CONSUMER_ID BIGINT not null,
    constraint LIKES_PK
        primary key (LIKES_ID),
    constraint "likes_CONSUMER_null_fk"
        foreign key (CONSUMER_ID) references CONSUMER,
    constraint "likes_FILM_null_fk"
        foreign key (FILM_ID) references FILM
);

