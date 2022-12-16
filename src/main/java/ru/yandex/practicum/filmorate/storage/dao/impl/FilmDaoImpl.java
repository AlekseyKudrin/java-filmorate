package ru.yandex.practicum.filmorate.storage.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.FilmsDao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
        String sqlQueryFilm = "INSERT INTO PUBLIC.FILMS (NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATE, MPA)" +
                "VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sqlQueryFilm,
                film.getName(),
                film.getDescription(),
                Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                film.getRate(),
                film.getMpa().getId());

        if (!(film.getGenres() == null) && !film.getGenres().isEmpty()) {
            for (int i=0; i<film.getGenres().size(); i++) {
                String sqlQueryGenre = "INSERT INTO PUBLIC.FILM_GENRE (FILM_ID, GENRE_ID) VALUES ( ?, ? )";
                jdbcTemplate.update(sqlQueryGenre,
                        getFilmAllFields(film).get().getId(), new ArrayList<>(film.getGenres()).get(i).getId());
            }
        }
    }

    @Override
    public void change(Film film) {
        String sqlQuery = "UPDATE PUBLIC.FILMS SET NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?, RATE = ?," +
                " MPA = ? WHERE ID = ?";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                film.getRate(),
                film.getMpa().getId(),
                film.getId());
        if (!(film.getGenres() == null) && !film.getGenres().isEmpty()) {
            jdbcTemplate.update("DELETE FROM PUBLIC.FILM_GENRE WHERE FILM_ID = ?", film.getId());
            for (int i=0; i<film.getGenres().size(); i++) {
                SqlRowSet genreRow = jdbcTemplate.queryForRowSet("SELECT * FROM PUBLIC.FILM_GENRE WHERE FILM_ID = ?" +
                        "AND GENRE_ID = ?", film.getId(),new ArrayList<>(film.getGenres()).get(i).getId());
                if (!genreRow.next()) {
                    String sqlQueryGenre = "INSERT INTO PUBLIC.FILM_GENRE (FILM_ID, GENRE_ID) VALUES ( ?, ? )";
                    jdbcTemplate.update(sqlQueryGenre,
                            getFilmAllFields(film).get().getId(), new ArrayList<>(film.getGenres()).get(i).getId());
                }
            }
        } else {
            jdbcTemplate.update("DELETE FROM PUBLIC.FILM_GENRE WHERE FILM_ID = ?", film.getId());
        }
    }

    @Override
    public Collection<Film> getFilmList() {
        return jdbcTemplate.query("SELECT *,M.NAME AS FILM_MPA FROM PUBLIC.FILMS AS F " +
                "JOIN PUBLIC.MPA AS M ON F.MPA = M.ID", this::mapRowToFilms);
    }

    @Override
    public Optional<Film> getFilmById(int id) {
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("SELECT F.ID, F.NAME, F.DESCRIPTION, F.RELEASE_DATE, F.DURATION," +
                        "F.RATE, F.MPA, M.NAME AS FILM_MPA from PUBLIC.FILMS AS F JOIN PUBLIC.MPA AS M ON F.MPA = M.ID" +
                        " WHERE F.ID = ?", id);
        return serializationFilm(filmRows);
    }

    @Override
    public Optional<Film> getFilmAllFields(Film film) {
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("SELECT F.ID, F.NAME, F.DESCRIPTION, F.RELEASE_DATE, F.DURATION," +
                        "F.RATE, F.MPA, M.NAME AS FILM_MPA from PUBLIC.FILMS AS F JOIN PUBLIC.MPA AS M ON F.MPA = M.ID" +
                        " WHERE F.NAME = ? AND F.DESCRIPTION = ? AND F.RELEASE_DATE = ? AND F.DURATION = ?", film.getName(),
                        film.getDescription(), film.getReleaseDate(), film.getDuration());
        return serializationFilm(filmRows);
    }

    private Optional<Film> serializationFilm (SqlRowSet sqlRowSet) {
        Film filmFromDb = new Film();
        LinkedHashSet<Genre> genresFilm = new LinkedHashSet<>();
        if (sqlRowSet.next()) {
            filmFromDb.setId(sqlRowSet.getInt("ID"));
            filmFromDb.setName(sqlRowSet.getString("NAME"));
            filmFromDb.setDescription(sqlRowSet.getString("DESCRIPTION"));
            filmFromDb.setReleaseDate(sqlRowSet.getDate("RELEASE_DATE").toLocalDate());
            filmFromDb.setDuration(sqlRowSet.getInt("DURATION"));
            filmFromDb.setRate(sqlRowSet.getInt("RATE"));
            filmFromDb.setMpa(new Mpa (sqlRowSet.getInt("MPA"), sqlRowSet.getString("FILM_MPA")));
        } else {
            return Optional.empty();
        }
        SqlRowSet genresRows = jdbcTemplate.queryForRowSet("SELECT FG.GENRE_ID, G2.NAME AS GENRE FROM PUBLIC.FILM_GENRE AS FG JOIN GENRE G2 ON" +
                        " G2.ID = FG.GENRE_ID WHERE FILM_ID = ?", filmFromDb.getId());
        while (genresRows.next()) {
            genresFilm.add(new Genre(genresRows.getInt("GENRE_ID"), genresRows.getString("GENRE")));
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
        int rate = resultSet.getInt("RATE");
        Mpa mpa = new Mpa(resultSet.getInt("MPA"),resultSet.getString("FILM_MPA"));
        LinkedHashSet<Genre> genres = new LinkedHashSet<>();

        SqlRowSet genresRows = jdbcTemplate.queryForRowSet("SELECT FG.GENRE_ID, G2.NAME AS GENRE FROM PUBLIC.FILM_GENRE AS FG JOIN GENRE G2 ON" +
                " G2.ID = FG.GENRE_ID WHERE FILM_ID = ?", id);
        while (genresRows.next()) {
            genres.add(new Genre(genresRows.getInt("GENRE_ID"), genresRows.getString("GENRE")));
        }
        return new Film(id, name, description, releaseDate, duration, rate, mpa, genres);
    }
}
