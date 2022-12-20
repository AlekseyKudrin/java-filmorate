package ru.yandex.practicum.filmorate.storage.dao.impl;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.FilmsDao;

import java.time.LocalDate;
import java.util.LinkedHashSet;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmsDaoImplTest {

    private final FilmsDao filmsDao;

    @Test
    void create() {
        Film film = new Film(
                1,
                "test",
                "test",
                LocalDate.of(2010, 10, 10),
                100,
                5,
                new Mpa(1, "комедия"),
                new LinkedHashSet<>()
        );
        filmsDao.create(film);
        AssertionsForClassTypes.assertThat(film).extracting("id").isNotNull();
        AssertionsForClassTypes.assertThat(film).extracting("name").isNotNull();
    }

    @Test
    void change() {
        Film film = new Film(
                1,
                "test",
                "test",
                LocalDate.of(2010, 10, 10),
                100,
                5,
                new Mpa(1, "комедия"),
                new LinkedHashSet<>()
        );
        filmsDao.create(film);
        film.setName("testUpdateFilm");
        film.setDescription("testUpdateDesc");
        filmsDao.change(film);
        AssertionsForClassTypes.assertThat(filmsDao.getFilmById(film.getId()).get())
                .hasFieldOrPropertyWithValue("name", "testUpdateFilm")
                .hasFieldOrPropertyWithValue("description", "testUpdateDesc");
    }

    @Test
    void getFilmById() {
        Film film = new Film(
                1,
                "test",
                "test",
                LocalDate.of(2010, 10, 10),
                100,
                5,
                new Mpa(1, "�������"),
                new LinkedHashSet<>()
        );
        filmsDao.create(film);
        filmsDao.getFilmById(film.getId());
        AssertionsForClassTypes.assertThat(filmsDao.getFilmById(film.getId()).get())
                .hasFieldOrPropertyWithValue("id", film.getId());
    }
}