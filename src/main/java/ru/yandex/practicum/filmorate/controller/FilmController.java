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

    @PutMapping("{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Ставим лайк фильму");
        filmService.addLike(id, userId);
    }

    @GetMapping
    public Collection<Film> getFilmList() {
        log.info("Возврат списка фильмов");
        return filmService.getFilmList();
    }

    @GetMapping("popular?count={count}")
    public Collection<Film> getPopFilms(@PathVariable(required = false) int count) {
        log.info("Возвтрат списка популярных фильмов");
        return filmService.returnPopFilms(count);
    }

    @DeleteMapping("{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Удаление лайка");
        filmService.deleteLike(id, userId);
    }
}
