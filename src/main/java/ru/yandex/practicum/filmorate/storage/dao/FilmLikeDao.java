package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public interface FilmLikeDao {
    SqlRowSet getPopFilms(int count);

    void addLike(int id, int userId);

    void deleteLike(int id, int userId);
}
