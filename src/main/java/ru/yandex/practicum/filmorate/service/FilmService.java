package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IncorrectValueException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage inMemoryFilmStorage) {
        this.filmStorage = inMemoryFilmStorage;
    }

    public Optional<Film> create(Film film) {
        validateOfFilm(film);
        filmStorage.create(film);
        log.info("Film successfully created");
        return filmStorage.getFilm(film);
    }

    public Optional<Film> change(Film film) {
        validateOfFilm(film);
        filmStorage.change(film);
        log.info("Film successfully change");
        return filmStorage.getFilm(film);
    }

    public Collection<Film> getFilmList() {
        Collection<Film> collectionFilms = filmStorage.getFilmList();
        log.info("Return film list successfully");
        return collectionFilms;
    }

    public void addLike(int id, int userId) {
        filmStorage.addLike(id, userId);
        log.info("Like successfully add");
    }

    public Optional<Film> getFilm(int id) {
        Optional<Film> film = filmStorage.getFilmById(id);
        log.info("Return film by Id successfully");
        return film;
    }

    public void deleteLike(int id, int userId) {
        if (userId > 0) {
            filmStorage.deleteLike(id, userId);
            log.info("Like successfully deleting");
        } else {
            throw new ValidationException("Id user can't be negative");
        }
    }

    public Collection<Film> getPopFilms(int count) {
        Collection<Film> collectionFilms = filmStorage.getPopFilms(count);

        log.info("Return popular films successfully");
        return collectionFilms;
    }

    private void validateOfFilm(Film film) {
        if (film.getName() == null || film.getName().length() == 0) {
            log.warn("Error in name film: name film is can't be empty");
            throw new IncorrectValueException("Name film is can't be empty");
        } else if (film.getDescription().length() > 200) {
            log.warn("Error in length description film: length description max=200 characters");
            throw new IncorrectValueException("Length description max=200 characters");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Error in date release: date release can't be before 1895-12-28");
            throw new IncorrectValueException("Date release can't be before 1895-12-28");
        } else if (film.getDuration() < 0) {
            log.warn("Error in duration film: duration can't be negative");
            throw new IncorrectValueException("Duration can't be negative");
        }
    }
}
