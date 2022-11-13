package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    FilmController filmController;

    @BeforeEach
    void beforeEach() {
        filmController = new FilmController(new FilmService(new InMemoryFilmStorage()));
    }

    @Test
    void createFilm() {
        Film filmAllData = new Film();
        filmAllData.setName("Test");
        filmAllData.setDescription("Test create Film");
        filmAllData.setReleaseDate(LocalDate.of(1991,10,10));
        filmAllData.setDuration(60);

        Film filmLength = new Film();
        filmLength.setName("Test");
        filmLength.setDescription("Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. " +
                "Здесь они хотят разыскать господина Огюста Куглова, который задолжал им деньги, а именно " +
                "20 миллионов. о Куглов, который за время «свое");
        filmLength.setReleaseDate(LocalDate.of(1991,10,10));
        filmLength.setDuration(60);

        Film filmNotName = new Film();
        filmNotName.setDescription("Test create Film");
        filmNotName.setReleaseDate(LocalDate.of(1991,10,10));
        filmNotName.setDuration(60);

        Film filmNameEmpty = new Film();
        filmNameEmpty.setName("");
        filmNameEmpty.setDescription("Test create Film");
        filmNameEmpty.setReleaseDate(LocalDate.of(1991,10,10));
        filmNameEmpty.setDuration(60);

        Film filmIncorrectReleaseDate = new Film();
        filmIncorrectReleaseDate.setName("Test");
        filmIncorrectReleaseDate.setDescription("Test Film");
        filmIncorrectReleaseDate.setReleaseDate(LocalDate.of(1895,12,27));
        filmIncorrectReleaseDate.setDuration(60);

        Film filmIncorrectDuration = new Film();
        filmIncorrectDuration.setName("Test");
        filmIncorrectDuration.setDescription("Test Film");
        filmIncorrectDuration.setReleaseDate(LocalDate.of(1895,12,28));
        filmIncorrectDuration.setDuration(-1);


        filmController.createFilm(filmAllData);
        ValidationException thrownExceptionLength = assertThrows(ValidationException.class,
                () -> filmController.createFilm(filmLength));
        ValidationException thrownExceptionNameNull = assertThrows(ValidationException.class,
                () -> filmController.createFilm(filmNotName));
        ValidationException thrownExceptionNameEmpty = assertThrows(ValidationException.class,
                () -> filmController.createFilm(filmNameEmpty));
        ValidationException thrownExceptionNReleaseDate = assertThrows(ValidationException.class,
                () -> filmController.createFilm(filmIncorrectReleaseDate));
        ValidationException thrownExceptionIncorrectDuration = assertThrows(ValidationException.class,
                () -> filmController.createFilm(filmIncorrectDuration));


        assertEquals(filmAllData, filmController.filmService.filmStorage.getFilmList().get(1));
        assertTrue(thrownExceptionLength.getMessage().contains("Длинна описания не может привышать 200 символов"));
        assertTrue(thrownExceptionNameNull.getMessage().contains("Название не может быть пустым"));
        assertTrue(thrownExceptionNameEmpty.getMessage().contains("Название не может быть пустым"));
        assertTrue(thrownExceptionNReleaseDate.getMessage().contains("Некорректная дата релиза"));
        assertTrue(thrownExceptionIncorrectDuration.getMessage().contains("продолжительность не может быть меньше 0"));
    }

    @Test
    void changeFilm() {
        Film film = new Film();
        film.setName("Test");
        film.setDescription("Test Film");
        film.setReleaseDate(LocalDate.of(1991,10,10));
        film.setDuration(60);

        Film filmCorrectId = new Film();
        filmCorrectId.setId(1);
        filmCorrectId.setName("Test");
        filmCorrectId.setDescription("Test Film");
        filmCorrectId.setReleaseDate(LocalDate.of(1991,10,12));
        filmCorrectId.setDuration(60);

        Film filmIncorrectId = new Film();
        filmIncorrectId.setId(999);
        filmIncorrectId.setName("Test");
        filmIncorrectId.setDescription("Test Film");
        filmIncorrectId.setReleaseDate(LocalDate.of(1991,10,10));
        filmIncorrectId.setDuration(60);


        filmController.createFilm(film);
        filmController.changeFilm(filmCorrectId);
        ValidationException thrownExceptionIncorrectId = assertThrows(ValidationException.class,
                () -> filmController.changeFilm(filmIncorrectId));


        assertEquals(filmCorrectId, filmController.filmService.filmStorage.getFilmList().get(1));
        assertTrue(thrownExceptionIncorrectId.getMessage().contains("Изменения не внесены, данного фильма нет в базе"));


    }

    @Test
    void getFilmList() {
        Film film1 = new Film();
        film1.setName("Test");
        film1.setDescription("Test Film");
        film1.setReleaseDate(LocalDate.of(1991,10,10));
        film1.setDuration(60);

        Film film2= new Film();
        film2.setName("Test");
        film2.setDescription("Test Film");
        film2.setReleaseDate(LocalDate.of(1991,10,12));
        film2.setDuration(60);

        Film film3 = new Film();
        film3.setName("Test");
        film3.setDescription("Test Film");
        film3.setReleaseDate(LocalDate.of(1991,10,10));
        film3.setDuration(60);


        filmController.createFilm(film1);
        filmController.createFilm(film2);
        filmController.createFilm(film3);


        assertEquals(3, filmController.getFilmList().size());
    }
}