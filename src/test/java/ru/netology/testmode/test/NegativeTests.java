package ru.netology.testmode.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.DataGenerator.*;
import static ru.netology.testmode.data.DataGenerator.Registration.*;

public class NegativeTests {

    @BeforeEach
    public void setUpTest() {
        open("http://localhost:9999");
    }

    @Test
    void shouldNotValidateEmptyFormFields() {
        $("[data-test-id='action-login']").click();
        $("[data-test-id='login'] .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"), Duration.ofSeconds(15));
        $("[data-test-id='password'] .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"), Duration.ofSeconds(15));
    }

    @Test
    void shouldNotLoginWithInvalidLoginOfActiveUser() {
        var user = generateUser("active");
        $("[data-test-id='login'] input").setValue(getRandomLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__title")
                .shouldHave(text("Ошибка"), Duration.ofSeconds(15));
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15));
    }

    @Test
    void shouldNotLoginWithInvalidPasswordOfActiveUser() {
        var user = generateUser("active");
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(getRandomPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__title")
                .shouldHave(text("Ошибка"), Duration.ofSeconds(15));
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15));
    }

    @Test
    void shouldNotLoginThenShowBlockMessageIfValidDataOfBlockedUser() {
        var user = generateUser("blocked");
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__title")
                .shouldHave(text("Ошибка"), Duration.ofSeconds(15));
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(text("Ошибка! Пользователь заблокирован"), Duration.ofSeconds(15));
    }

    @Test
    void shouldNotLoginThenShowErrorIfInvalidLoginOfBlockedUser() {
        var user = generateUser("blocked");
        $("[data-test-id='login'] input").setValue(getRandomLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__title")
                .shouldHave(text("Ошибка"), Duration.ofSeconds(15));
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15));
    }

    @Test
    void shouldNotLoginThenShowErrorIfInvalidPasswordOfBlockedUser() {
        var user = generateUser("blocked");
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(getRandomPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__title")
                .shouldHave(text("Ошибка"), Duration.ofSeconds(15));
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15));
    }
}
