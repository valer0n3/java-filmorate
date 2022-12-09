create table if not exists USERS
(
    USERS_ID BIGINT auto_increment,
    EMAIL       CHARACTER(255) not null,
    LOGIN       CHARACTER(255) not null,
    NAME        CHARACTER(255),
    BIRTHDAY    DATE,
    constraint "CONSUMER_pk"
        primary key (USERS_ID)
);

create table if not exists FRINDSHIP
(
    FRINDSHIP_ID       BIGINT auto_increment,
    SOURCE_USERS_ID BIGINT not null,
    TARGET_USERS_ID BIGINT not null,
    STATUS             CHARACTER(255),
    constraint FRINDSHIP_PK
        primary key (FRINDSHIP_ID),
    constraint "FRINDSHIP_CONSUMER_INITIATOR_fk"
        foreign key (SOURCE_USERS_ID) references USERS,
    constraint "FRINDSHIP_CONSUMER_TARGET_fk"
        foreign key (TARGET_USERS_ID) references USERS
);

create table if not exists GENRE
(
    GENRE_ID BIGINT,
    NAME     CHARACTER(50) not null,
    constraint GENRE_PK
        primary key (GENRE_ID)
);

create table if not exists MPA
(
    MPA_ID BIGINT,
    MPA_TYPE    CHARACTER(50) not null,
    constraint MPA_PK
        primary key (MPA_ID)
);

create table if not exists FILM
(
    FILM_ID      BIGINT auto_increment,
    MPA_ID    BIGINT,
    NAME         CHARACTER(255) not null,
    DESCRIPTION  CHARACTER(200),
    DURATION     CHARACTER(255),
    RELEASE_DATE DATE,
    COLUMN_NAME  CHARACTER(255),
    constraint FILM_PK
        primary key (FILM_ID),
    constraint "film_RATING_fk"
        foreign key (MPA_ID) references MPA
);

create table if not exists LIKES
(
    LIKES_ID    BIGINT auto_increment,
    FILM_ID     BIGINT not null,
    CONSUMER_ID BIGINT not null,
    constraint LIKES_PK
        primary key (LIKES_ID),
    constraint "likes_CONSUMER_null_fk"
        foreign key (CONSUMER_ID) references USERS,
    constraint "likes_FILM_null_fk"
        foreign key (FILM_ID) references FILM
);

create table FILM_GENRE
(
    FILM_ID  BIGINT,
    GENRE_ID INTEGER,
    constraint "FILM_GENRE_FILM_fk"
        foreign key (FILM_ID) references FILM,
    constraint "FILM_GENRE_GENRE_fk"
        foreign key (GENRE_ID) references GENRE
);



