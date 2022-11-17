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
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage inMemoryFilmStorage) {
        this.filmStorage =  inMemoryFilmStorage;
    }

    public void create(Film film){
        validateOfFilm(film);
        filmStorage.add(film);
        log.info("Film successfully created");
    }

    public void change(Film film) {
        validateOfFilm(film);
        filmStorage.change(film);
        log.info("Film successfully change");
        }

    public Collection<Film> getFilmList() {
        Collection<Film> collectionFilms = filmStorage.getFilmList().values();
        log.info("Return film list successfully");
        return collectionFilms;
    }

    public void addLike(int id, int userId) {
        Film film = filmStorage.getFilmById(id);
        film.addLike(userId);
        log.info("Like successfully add");
    }

    public Film getFilm(int id) {
        Film film = filmStorage.getFilmById(id);
        log.info("Return successfully film by Id");
        return film;
    }

    public void deleteLike(int id, int userId) {
        if (userId > 0) {
            Film film = filmStorage.getFilmById(id);
            film.deleteLike(userId);
            log.info("Like successfully deleting");
        } else {
            throw new ValidationException("Id user can't be negative");
        }
    }

    public Collection<Film> returnPopFilms (int count) {
        Collection<Film> collectionFilms =  filmStorage.getFilmList().values();
        List<Film> listSortFilm = collectionFilms.stream().sorted((o1, o2) -> Integer.compare(o2.getLikes().size(), o1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
        log.info("Return popular films successfully");
        return listSortFilm;
    }

    private void validateOfFilm (Film film) {
        if (film.getName() == null || film.getName().length() == 0) {
            log.warn("Error in name film: name film is can't be empty");
            throw new IncorrectValueException("Name film is can't be empty");
        } else if (film.getDescription().length() > 200) {
            log.warn("Error in length description film: length description max=200 characters");
            throw new IncorrectValueException("Length description max=200 characters");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            log.warn("Error in date release: date release can't be before 1895-12-28");
            throw new IncorrectValueException("Date release can't be before 1895-12-28");
        } else if (film.getDuration() < 0) {
            log.warn("Error in duration film: duration can't be negative");
            throw new IncorrectValueException("Duration can't be negative");
        }
    }
}
