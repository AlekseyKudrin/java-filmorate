create table MPA
(
    ID     INTEGER auto_increment
        unique,
    RATING CHARACTER VARYING(10),
    constraint "MPA_pk"
        primary key (ID)
);

create table FILMS
(
    ID           INTEGER auto_increment
        unique,
    NAME         CHARACTER VARYING(100),
    DESCRIPTION  CHARACTER VARYING(200),
    RELEASE_DATE DATE,
    DURATION     INTEGER,
    RATE         INTEGER,
    constraint "FILM_pk"
        primary key (ID),
    constraint FILMS_MPA_ID_FK
        foreign key (RATE) references MPA
);

create table USERS
(
    ID       INTEGER auto_increment
        unique,
    EMAIL    CHARACTER VARYING(100),
    LOGIN    CHARACTER VARYING(100),
    NAME     CHARACTER VARYING(100),
    BIRTHDAY DATE,
    constraint "USERS_pk"
        primary key (ID)
);

create table GENRE
(
    ID         INTEGER auto_increment
        primary key
        unique,
    GENRE_NAME CHARACTER VARYING(255) not null
);

create table STATUS
(
    ID     INTEGER auto_increment
        unique,
    STATUS CHARACTER VARYING(20),
    constraint "STATUS_pk"
        primary key (ID)
);

create table FILM_GENRE
(
    FILM_ID  INTEGER not null,
    GENRE_ID INTEGER not null,
    constraint FILM_GENRE_FILMS_ID_FK
        foreign key (FILM_ID) references FILMS,
    constraint FILM_GENRE_GENRE_ID_FK
        foreign key (GENRE_ID) references GENRE
);

create table FRIENDS
(
    ID_USER   INTEGER,
    ID_FRIEND INTEGER,
    STATUS_ID INTEGER,
    constraint "FRIENDS_STATUS_null_fk"
        foreign key (STATUS_ID) references STATUS,
    constraint "FRIENDS_USERS_null_fk"
        foreign key (ID_USER) references USERS,
    constraint "FRIENDS_USER_fRIEND_null_fk"
        foreign key (ID_FRIEND) references USERS
);

create table FILM_LIKE
(
    FILM_ID INTEGER not null,
    USER_ID INTEGER,
    constraint "FILM_LIKE_FILM_null_fk"
        foreign key (FILM_ID) references FILMS,
    constraint "FILM_LIKE_USERS_null_fk"
        foreign key (USER_ID) references USERS
);

