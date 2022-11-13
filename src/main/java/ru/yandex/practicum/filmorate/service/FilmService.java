package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class FilmService {
    public FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage inMemoryFilmStorage) {
        this.filmStorage =  inMemoryFilmStorage;
    }

    public void create(Film film){
        validateOfFilm(film);
        filmStorage.add(film);
    }

    public void change(Film film) {
        if (filmStorage.getFilmList().containsKey(film.getId())) {
            validateOfFilm(film);
            filmStorage.change(film);
        } else {
            log.trace("Фильм не изменен");
            throw new ValidationException("Изменения не внесены, данного фильма нет в базе");
        }
    }

    public Collection<Film> getFilmList() {
        return filmStorage.getFilmList().values();
    }

    public void addLike() {

    }

    public void deleteLike() {

    }

    public Collection<Film> returnPopFilms () {
        return List.of();
    }


    private void validateOfFilm (Film film) {
        if (film.getName() == null || film.getName().length() == 0) {
            log.trace("Ошибка в названии фильма");
            throw new ValidationException("Название не может быть пустым");
        } else if (film.getDescription().length() > 200) {
            log.trace("Ошибка в длинне комментария к фильму");
            throw new ValidationException("Длинна описания не может привышать 200 символов");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            log.trace("Ошибка в дате релиза фильма");
            throw new ValidationException("Некорректная дата релиза");
        } else if (film.getDuration() < 0) {
            log.trace("Ошибка в продолжительноски фильма");
            throw new ValidationException("продолжительность не может быть меньше 0");
        }
    }
}
