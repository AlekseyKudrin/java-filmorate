package ru.yandex.practicum.filmorate.storage.dao.impl;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.GenreDao;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class GenreDaoImplTest {

    private final GenreDao genreDao;

    @Test
    void getGenreById() {
        Optional<Genre> genre = genreDao.getGenreById(1);
        Assertions.assertThat(genre.get())
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "Комедия");
    }

    @Test
    void getGenresList() {
        Collection<Genre> genre = genreDao.getGenresList();
        Assertions.assertThat(genre)
                .isNotEmpty()
                .extracting(Genre::getName)
                .containsAll(Arrays.asList("Комедия", "Драма", "Мультфильм", "Триллер", "Документальный", "Боевик"));
    }
}