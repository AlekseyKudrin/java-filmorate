package ru.yandex.practicum.filmorate.storage.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.dao.FilmLikeDao;

@Component
public class FilmLikeDaoImpl implements FilmLikeDao {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmLikeDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLike(int id, int userId) {
        String sqlQuery = "INSERT INTO PUBLIC.FILM_LIKE (FILM_ID, USER_ID)" +
                "VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery,
                id,
                userId);
    }

    @Override
    public SqlRowSet getPopFilms(int count) {
        SqlRowSet popRow = jdbcTemplate.queryForRowSet("SELECT ID " +
                "FROM FILMS LEFT OUTER JOIN FILM_LIKE FL on FILMS.ID = FL.FILM_ID GROUP BY FILMS.ID, FL.FILM_ID IN (SELECT COUNT(FL.USER_ID) " +
                "FROM FILM_LIKE) ORDER BY COUNT(FL.FILM_ID) DESC LIMIT ?", count);
        return popRow;
    }
}
