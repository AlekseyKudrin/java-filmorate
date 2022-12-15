package ru.yandex.practicum.filmorate.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.dao.FilmsDao;

import java.util.Collection;
import java.util.Optional;

@Component
public class FilmStorageInDb implements FilmStorage {

    FilmsDao filmsDao;

    @Autowired
    public FilmStorageInDb(FilmsDao filmsDao) {
        this.filmsDao = filmsDao;
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
    public Film getFilmById(int id) {
        validateId(id);
        return null;
    }

    @Override
    public void validateId(int id) {
        if (filmsDao.getFilmById(id).isEmpty()) {
            throw new ValidationException("Film not found");
        }
    }
}
