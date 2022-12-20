package ru.yandex.practicum.filmorate.storage.dao.impl;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.UsersDao;

import java.time.LocalDate;
import java.util.Collection;


@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UsersDaoImplTest {

    private final UsersDao usersDao;

    @Test
    void create() {
        User user = new User(
                1,
                "test@mail.ru",
                "test",
                "name",
                LocalDate.of(2020, 10, 10)

        );
        usersDao.create(user);
        AssertionsForClassTypes.assertThat(user).extracting("id").isNotNull();
        AssertionsForClassTypes.assertThat(user).extracting("name").isNotNull();
    }

    @Test
    void change() {
        User user = new User(
                1,
                "test@mail.ru",
                "test",
                "name",
                LocalDate.of(2020, 10, 10)
        );
        usersDao.create(user);
        user.setName("testUpdatedName");
        user.setLogin("testUpdatedLogin");
        user.setEmail("updated@mail.mail");
        usersDao.change(user);
        AssertionsForClassTypes.assertThat(usersDao.getUserById(user.getId()).get())
                .hasFieldOrPropertyWithValue("login", "testUpdatedLogin")
                .hasFieldOrPropertyWithValue("name", "testUpdatedName")
                .hasFieldOrPropertyWithValue("email", "updated@mail.mail");
    }

    @Test
    void getUserList() {
        User user = new User(
                1,
                "test@mail.ru",
                "test",
                "name",
                LocalDate.of(2020, 10, 10)
        );
        usersDao.create(user);
        Collection<User> users = usersDao.getUserList();
        Assertions.assertThat(users).isNotEmpty().isNotNull().doesNotHaveDuplicates();
        Assertions.assertThat(users).extracting("email").contains(user.getEmail());
        Assertions.assertThat(users).extracting("login").contains(user.getLogin());
    }

    @Test
    void getUserById() {
        User user = new User(
                1,
                "test@mail.ru",
                "test",
                "name",
                LocalDate.of(2020, 10, 10)
        );
        usersDao.create(user);
        AssertionsForClassTypes.assertThat(usersDao.getUserById(user.getId()).get())
                .hasFieldOrPropertyWithValue("id", user.getId());
    }

}