package ru.yandex.practicum.filmorate.storage.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genres;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.FilmsDao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;

@Component
public class FilmDaoImpl implements FilmsDao {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Film film) {
        String sqlQueryFilm = "INSERT INTO PUBLIC.FILMS (NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA)" +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlQueryFilm,
                film.getName(),
                film.getDescription(),
                Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                film.getMpa().getId());
    }

    @Override
    public void change(Film film) {
        String sqlQuery = "UPDATE PUBLIC.FILMS SET NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?," +
                "MPA = ? WHERE ID = ?";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
    }

    @Override
    public Collection<Film> getFilmList() {
        return jdbcTemplate.query("SELECT * FROM PUBLIC.FILMS", this::mapRowToFilms);
    }

    @Override
    public Optional<Film> getFilmById(int id) {
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("SELECT *,M.NAME AS FILM_MPA FROM PUBLIC.FILMS AS F JOIN MPA M on M.ID = F.MPA" +
                " WHERE ID = ?", id);
        if (filmRows.next()) {
            Film film = new Film(
                    filmRows.getInt("ID"),
                    filmRows.getString("NAME"),
                    filmRows.getString("DESCRIPTION"),
                    filmRows.getDate("RELEASE_DATE").toLocalDate(),
                    filmRows.getInt("DURATION"),
                    new Mpa (filmRows.getInt("MPA"), filmRows.getString("FILM_MPA")),
                    new LinkedHashSet<>() //доделать
            );
            return Optional.of(film);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Film> getFilmAllFields(Film film) {
        Film filmFromDb = new Film();
        LinkedHashSet<Genres> genresFilm = new LinkedHashSet<>();
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("SELECT F.ID, F.NAME, F.DESCRIPTION, F.RELEASE_DATE, F.DURATION," +
                        "F.MPA, M.NAME AS FILM_MPA from PUBLIC.FILMS AS F JOIN PUBLIC.MPA AS M ON F.MPA = M.ID" +
                        " WHERE F.NAME = ? AND F.DESCRIPTION = ? AND F.RELEASE_DATE = ? AND F.DURATION = ?", film.getName(),
                        film.getDescription(), film.getReleaseDate(), film.getDuration());
        if (filmRows.next()) {
            filmFromDb.setId(filmRows.getInt("ID"));
            filmFromDb.setName(filmRows.getString("NAME"));
            filmFromDb.setDescription(filmRows.getString("DESCRIPTION"));
            filmFromDb.setReleaseDate(filmRows.getDate("RELEASE_DATE").toLocalDate());
            filmFromDb.setDuration(filmRows.getInt("DURATION"));
            filmFromDb.setMpa(new Mpa (filmRows.getInt("MPA"), filmRows.getString("FILM_MPA")));
        } else {
            return Optional.empty();
        }
        SqlRowSet genresRows = jdbcTemplate.queryForRowSet("SELECT FG.GENRE_ID, G2.GENRE_NAME AS GENRE FROM PUBLIC.FILM_GENRE AS FG JOIN GENRE G2 ON" +
                        " G2.ID = FG.GENRE_ID WHERE FILM_ID = ?",
                        filmFromDb.getId());
        while (genresRows.next()) {
            genresFilm.add(new Genres(genresRows.getInt("GENRE_ID"), genresRows.getString("GENRE")));
        }
        filmFromDb.setGenres(genresFilm);
        return Optional.of(filmFromDb);
    }
    private Film mapRowToFilms(ResultSet resultSet, int rowNum) throws SQLException {
        int id = resultSet.getInt("ID");
        String name = resultSet.getString("NAME");
        String description = resultSet.getString("DESCRIPTION");
        LocalDate releaseDate = resultSet.getDate("RELEASE_DATE").toLocalDate();
        int duration = resultSet.getInt("DURATION");
        Mpa mpa = new Mpa(resultSet.getInt("MPA"),resultSet.getString("FILM_MPA"));
        LinkedHashSet<Genres> genres = new LinkedHashSet<>(); // доделать
        return new Film(id, name, description, releaseDate, duration, mpa, genres);
    }
}
