package ru.yandex.practicum.filmorate.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.dao.FilmLikeDao;
import ru.yandex.practicum.filmorate.storage.dao.FilmsDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class FilmStorageInDb implements FilmStorage {

    private final FilmsDao filmsDao;

    private final FilmLikeDao filmLikeDao;

    @Autowired
    public FilmStorageInDb(FilmsDao filmsDao, FilmLikeDao filmLikeDao) {
        this.filmsDao = filmsDao;
        this.filmLikeDao = filmLikeDao;
    }

    @Override
    public void create(Film film) {
        filmsDao.create(film);
    }

    @Override
    public void change(Film film) {
        validateId(film.getId());
        filmsDao.change(film);
    }

    @Override
    public Collection<Film> getFilmList() {
        return filmsDao.getFilmList();
    }

    @Override
    public Optional<Film> getFilm(Film film) {
        return filmsDao.getFilmAllFields(film);
    }

    @Override
    public Optional<Film> getFilmById(int id) {
        validateId(id);
        return filmsDao.getFilmById(id);
    }

    @Override
    public Collection<Film> getPopFilms(int count) {
        List<Film> list = new ArrayList<>();
        SqlRowSet sql = filmLikeDao.getPopFilms(count);
        while (sql.next()) {
            list.add(filmsDao.getFilmById(sql.getInt("ID")).get());
        }
        filmsDao.getFilmList();
        return list;
    }

    @Override
    public void addLike(int id, int userId) {
        filmLikeDao.addLike(id, userId);
    }

    @Override
    public void deleteLike(int id, int userId) {
        filmLikeDao.deleteLike(id, userId);
    }

    @Override
    public void validateId(int id) {
        if (filmsDao.getFilmById(id).isEmpty()) {
            throw new ValidationException("Film not found");
        }
    }
}
