package ru.yandex.practicum.filmorate.service;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.security.KeyStore;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        validateOfFilm(film);
        filmStorage.change(film);
        log.trace("Фильм изменен");
        }

    public Collection<Film> getFilmList() {
        return filmStorage.getFilmList().values();
    }

    public void addLike(int id, int userId) {
        Film film = filmStorage.getFilmById(id);
        film.addLike(userId);
        log.trace("Лайк добавлен");
    }

    public Film getFilm(int id) {
        return filmStorage.getFilmById(id);
    }

    public void deleteLike(int id, int userId) {
        Film film = filmStorage.getFilmById(id);
        film.deleteLike(userId);
        log.trace("Лайк удален");
    }

    public Collection<Film> returnPopFilms (int count) {
        Map<Integer, Integer> amountLike = new HashMap<>();// Не отсортированный список лайков
        Map<Integer, Integer> sortLike;// Отсортированный список по кол-ву лайков

        Map<Integer, Film> sortFilm = new LinkedHashMap<>(); //Отсортированный список фильмов по кол-ву лайков
        Map<Integer, Film> countFilms = new LinkedHashMap<>();// Список состоящий из Count=количества фильмов
        if (!(count < 0)) {
            for (Integer filmId : filmStorage.getFilmList().keySet()) {
                int a = filmStorage.getFilmList().get(filmId).getLikes().size();
                amountLike.put(filmId, a);
            }
            Stream<Map.Entry<Integer, Integer>> stream = amountLike.entrySet().stream().sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed());
            sortLike = stream.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            for (Integer key : sortLike.keySet()) {
                sortFilm.put(key, filmStorage.getFilmList().get(key));
            }
            System.out.println(sortLike.getClass());
            if (count == 0) {
                return sortFilm.values();
            }
            for (int i = 1; i <= count; i++) {
                for(Map.Entry<Integer, Film> entry : sortFilm.entrySet()) {
                    countFilms.put(entry.getKey(), entry.getValue());
                    sortFilm.remove(entry.getKey());
                    break;
                }
            }
            return countFilms.values();
        } else {
            throw new ValidationException("кол-во лайков не может быть отрицательным");
        }
    }

    public Collection<Film> returnPop (int count) {
        Collection<Film> col =  filmStorage.getFilmList().values();
        return List.of();
    }


    private void validateOfFilm (Film film) {
        if (film.getName() == null || film.getName().length() == 0) {
            log.trace("Ошибка в названии фильма");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Название не может быть пустым");
        } else if (film.getDescription().length() > 200) {
            log.trace("Ошибка в длинне комментария к фильму");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Длинна описания не может привышать 200 символов");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            log.trace("Ошибка в дате релиза фильма");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Некорректная дата релиза");
        } else if (film.getDuration() < 0) {
            log.trace("Ошибка в продолжительноски фильма");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"продолжительность не может быть меньше 0");
        }
    }
}
