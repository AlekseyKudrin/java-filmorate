package ru.yandex.practicum.filmorate.storage.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.GenreDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@Component
public class GenreDaoImpl implements GenreDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Genre> getGenreById(int id) {
        SqlRowSet genreRow = jdbcTemplate.queryForRowSet("SELECT * FROM PUBLIC.GENRE WHERE ID = ?", id);
        if (genreRow.next()) {
            Genre genre = new Genre(
                    genreRow.getInt("ID"),
                    genreRow.getString("NAME")
            );
            return Optional.of(genre);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Collection<Genre> getGenresList() {
        return jdbcTemplate.query("SELECT * FROM PUBLIC.GENRE", this::mapRowToGenres);
    }

    private Genre mapRowToGenres(ResultSet resultSet, int rowNum) throws SQLException {
        int id = resultSet.getInt("ID");
        String name = resultSet.getString("NAME");
        return new Genre(id, name);
    }
}
