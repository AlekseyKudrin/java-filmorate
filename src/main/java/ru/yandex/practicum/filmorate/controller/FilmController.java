package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }


    @PostMapping
    public Optional<Film> createFilm(@RequestBody Film film) {
        log.info("Creating film");
        return filmService.create(film);
    }

    @PutMapping
    public Optional<Film> changeFilm(@RequestBody Film film) {
        log.info("Film change");
        return filmService.change(film);
    }

    @PutMapping("{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Like add to the film");
        filmService.addLike(id, userId);
    }

    @GetMapping
    public Collection<Film> getFilmList() {
        log.info("Return film list");
        return filmService.getFilmList();
    }

    @GetMapping("{id}")
    public Optional<Film> getFilm(@PathVariable int id) {
        log.info("Return film by Id");
        return filmService.getFilm(id);
    }

    @GetMapping("popular")
    public Collection<Film> getPopFilms(@RequestParam(defaultValue = "10") Integer count) {
        log.info("Return list popular films");
        return filmService.getPopFilms(count);
    }

    @DeleteMapping("{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Deleting like");
        filmService.deleteLike(id, userId);
    }
}
