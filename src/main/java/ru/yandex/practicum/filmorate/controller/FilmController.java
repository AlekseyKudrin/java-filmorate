package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    public FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }



    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        filmService.create(film);
        log.info("Фильм создан успешно");
        return film;
    }

    @PutMapping
    public Film changeFilm(@RequestBody Film film){
        filmService.change(film);
        log.info("Фильм успешно изменен");
        return film;
    }

    @GetMapping
    public Collection<Film> getFilmList() {
        log.info("Возврат списка фильмов");
        return filmService.getFilmList();
    }
}
