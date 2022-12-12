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
    ID       INTEGER not null,
    EMAIL    CHARACTER VARYING(100),
    LOGIN    CHARACTER VARYING(100),
    NAME     CHARACTER VARYING(100),
    BIRTHDAY DATE,
    constraint "USERS_pk"
        primary key (ID)
);

create table FILM_LIKE
(
    FILM_ID INT not null,
    USER_ID INT,
    constraint "FILM_LIKE_FILM_null_fk"
        foreign key (FILM_ID) references FILMS (ID),
    constraint "FILM_LIKE_USERS_null_fk"
        foreign key (USER_ID) references USERS (ID)
);

create table GENRE
(
    ID         INT               not null,
    GENRE_NAME CHARACTER VARYING(255) not null,
    constraint "GENRE_pk"
        primary key (GENRE_NAME)
);

create table FILM_GENRE
(
    FILM_ID  INTEGER,
    GENRE_ID INTEGER,
    constraint "FILM_GENRE_FILM_null_fk"
        foreign key (FILM_ID) references FILMS,
    constraint "FILM_GENRE_GENRE_null_fk"
        foreign key (GENRE_ID) references GENRE
);

create table STATUS
(
    ID     INTEGER not null,
    STATUS CHARACTER VARYING(10),
    constraint "STATUS_pk"
        primary key (ID)
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

create table MPA
(
    ID     INTEGER not null,
    RATING INTEGER,
    constraint "MPA_pk"
        primary key (ID)
);




