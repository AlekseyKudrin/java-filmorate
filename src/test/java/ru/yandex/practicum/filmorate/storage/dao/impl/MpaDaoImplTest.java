package ru.yandex.practicum.filmorate.storage.dao.impl;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.MpaDao;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class MpaDaoImplTest {

    private final MpaDao mpaDao;

    @Test
    void getMpaById() {
        Optional<Mpa> mpa = mpaDao.getMpaById(4);
        Assertions.assertThat(mpa.get())
                .hasFieldOrPropertyWithValue("id", 4)
                .hasFieldOrPropertyWithValue("name", "R");
    }

    @Test
    void getMpaList() {
        Collection<Mpa> mpaList = mpaDao.getMpaList();
        Assertions.assertThat(mpaList)
                .isNotEmpty()
                .extracting(Mpa::getName)
                .containsAll(Arrays.asList("G", "PG", "PG-13", "R", "NC-17"));
    }
}