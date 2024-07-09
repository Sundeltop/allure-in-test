package com.example;

import com.example.annotations.WebTest;
import com.example.pages.GooglePage;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;

@WebTest
public class GoogleTest {

    @Test
    void verifySearchResultIsDisplayed() {
        final String expectedSearchResult = "Reuters | Breaking International News & Views";

        assertThat(open("https://www.google.com", GooglePage.class)
                .enterTextToSearch("Reuters")
                .getFirstSearchResult()).isEqualTo(expectedSearchResult);
    }
}
