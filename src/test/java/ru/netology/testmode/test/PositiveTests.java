package ru.netology.testmode.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.DataGenerator.Registration.*;

public class PositiveTests {

    @BeforeEach
    public void setUpTest() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLoginWithValidDataOfActiveUser() {
        var user = generateUser("active");
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[id='root']").shouldHave(text("Личный кабинет"), Duration.ofSeconds(15));
    }
}
