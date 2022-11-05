package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private int id = 1;

    public final Map<Integer, Film> filmList = new HashMap<>();

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        if (film.getName() == null || film.getName().length() == 0) {
            log.trace("Ошибка в названии фильма");
            throw new ValidationException("Название не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            log.trace("Ошибка в длинне комментария к фильму");
            throw new ValidationException("Длинна описания не может привышать 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            log.trace("Ошибка в дате релиза фильма");
            throw new ValidationException("Некорректная дата релиза");
        }
        if (film.getDuration() < 0) {
            log.trace("Ошибка в продолжительноски фильма");
            throw new ValidationException("продолжительность не может быть меньше 0");
        }
        film.setId(id++);
        filmList.put(film.getId(), film);
        log.info("Фильм создан успешно");
        return film;
    }

    @PutMapping
    public Film changeFilm(@RequestBody Film film){
        if (filmList.containsKey(film.getId())) {
            filmList.put(film.getId(), film);
            log.info("Фильм успешно изменен");
            return film;
        } else {
            log.trace("Ошибка при изменении фильма");
            throw new ValidationException("Изменения не внесены, данного фильма нет в базе");
        }
    }

    @GetMapping
    public Collection<Film> getFilmList() {
        log.info("Возврат списка фильмов");
        return filmList.values();
    }
}
