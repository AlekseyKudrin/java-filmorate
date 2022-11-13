package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    UserController userController;
    @BeforeEach
    void beforeEach () {
        userController = new UserController(new UserService(new InMemoryUserStorage()));
    }

    @Test
    void createUser() {
        User user = new User();
        user.setEmail("test@mail.ru");
        user.setLogin("Login");
        user.setName("Test");
        user.setBirthday(LocalDate.of(2000,12,10));

        User userEmailEmpty = new User();
        userEmailEmpty.setLogin("Login");
        userEmailEmpty.setName("Test");
        userEmailEmpty.setBirthday(LocalDate.of(2000,12,10));

        User userIncorrectEmail = new User();
        userIncorrectEmail.setEmail("testmailru");
        userIncorrectEmail.setLogin("Login");
        userIncorrectEmail.setName("Test");
        userIncorrectEmail.setBirthday(LocalDate.of(2000,12,10));

        User userLoginEmpty = new User();
        userLoginEmpty.setEmail("test@mail.ru");
        userLoginEmpty.setName("Test");
        userLoginEmpty.setBirthday(LocalDate.of(2000,12,10));

        User userIncorrectLogin = new User();
        userIncorrectLogin.setEmail("test@mail.ru");
        userIncorrectLogin.setLogin("Login test");
        userIncorrectLogin.setName("Test");
        userIncorrectLogin.setBirthday(LocalDate.of(2000,12,10));

        User userNameEmpty = new User();
        userNameEmpty.setEmail("test@mail.ru");
        userNameEmpty.setLogin("Login");
        userNameEmpty.setBirthday(LocalDate.of(2000,12,10));

        User userBirthdayAfterDateNow = new User();
        userBirthdayAfterDateNow.setEmail("test@mail.ru");
        userBirthdayAfterDateNow.setLogin("Login");
        userBirthdayAfterDateNow.setName("Test");
        userBirthdayAfterDateNow.setBirthday(LocalDate.of(2023,12,10));


        userController.createUser(user);
        ValidationException thrownExceptionEmailEmpty = assertThrows(ValidationException.class,
                () -> userController.createUser(userEmailEmpty));
        ValidationException thrownExceptionIncorrectEmail = assertThrows(ValidationException.class,
                () -> userController.createUser(userIncorrectEmail));
        ValidationException thrownExceptionLoginEmpty = assertThrows(ValidationException.class,
                () -> userController.createUser(userLoginEmpty));
        ValidationException thrownExceptionIncorrectLogin = assertThrows(ValidationException.class,
                () -> userController.createUser(userIncorrectLogin));
        userController.createUser(userNameEmpty);
        ValidationException thrownExceptionBirthday = assertThrows(ValidationException.class,
                () -> userController.createUser(userBirthdayAfterDateNow));


        assertEquals(user, userController.userService.userStorage.getUserList().get(1));
        assertTrue(thrownExceptionEmailEmpty.getMessage().contains("Некорректно указан email"),
                "Тест, пустым email");
        assertTrue(thrownExceptionIncorrectEmail.getMessage().contains("Некорректно указан email"),
                "Тест, email не содержит @");
        assertTrue(thrownExceptionLoginEmpty.getMessage().contains("Некорректно указан login"),
                "Тест, логин пустой");
        assertTrue(thrownExceptionIncorrectLogin.getMessage().contains("Некорректно указан login"),
                "Тест, логин содержит пробел");
        assertEquals(userNameEmpty, userController.userService.userStorage.getUserList().get(2));
        assertTrue(thrownExceptionBirthday.getMessage().contains("Некорректно указана дата рождения"));
    }

    @Test
    void changeUser() {
        User user = new User();
        user.setEmail("test@mail.ru");
        user.setLogin("Login");
        user.setName("Test");
        user.setBirthday(LocalDate.of(2000,12,10));

        User userCorrectId = new User();
        userCorrectId.setId(1);
        userCorrectId.setEmail("test@mail.ru");
        userCorrectId.setLogin("LoginChange");
        userCorrectId.setName("Test");
        userCorrectId.setBirthday(LocalDate.of(2000,12,10));

        User userIncorrectId = new User();
        userIncorrectId.setId(999);
        userIncorrectId.setEmail("test@mail.ru");
        userIncorrectId.setLogin("LoginChange");
        userIncorrectId.setName("Test");
        userIncorrectId.setBirthday(LocalDate.of(2000,12,10));


        userController.createUser(user);
        userController.changeUser(userCorrectId);
        ValidationException thrownExceptionIncorrectId = assertThrows(ValidationException.class,
                () -> userController.changeUser(userIncorrectId));


        assertEquals(userCorrectId, userController.userService.userStorage.getUserList().get(1));
        assertTrue(thrownExceptionIncorrectId.getMessage().contains("Пользователь не найден"));
    }

    @Test
    void getUserList() {
        User user1 = new User();
        user1.setEmail("test@mail.ru");
        user1.setLogin("Login");
        user1.setName("Test");
        user1.setBirthday(LocalDate.of(2000,12,10));

        User user2 = new User();
        user2.setEmail("test@mail.ru");
        user2.setLogin("Login");
        user2.setName("Test");
        user2.setBirthday(LocalDate.of(2000,12,10));

        User user3 = new User();
        user3.setEmail("test@mail.ru");
        user3.setLogin("Login");
        user3.setName("Test");
        user3.setBirthday(LocalDate.of(2000,12,10));


        userController.createUser(user1);
        userController.createUser(user2);
        userController.createUser(user3);


        assertEquals(3, userController.getUserList().size());
    }
}