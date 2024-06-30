package com.example;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MySimpleTest {

    @Test
    void openGooglePageTest() {
        open("https://www.google.com");

        sleep(1000);
        assertThat(title()).isEqualTo("Google");
    }
}
