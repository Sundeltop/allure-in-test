package com.example;

import com.example.jupiter.AllureEnvironmentExtension;
import com.example.jupiter.AllureSelenideExtension;
import com.example.jupiter.AssertFailAllureScreenshotExtension;
import com.example.jupiter.SelenoidExtension;
import com.example.pages.GooglePage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({AssertFailAllureScreenshotExtension.class, AllureEnvironmentExtension.class,
        AllureSelenideExtension.class, SelenoidExtension.class})
public class GoogleTest {

    @Test
    void verifySearchResultIsDisplayed() {
        final String expectedSearchResult = "Reuters | Breaking International News & Views";

        assertThat(open("https://www.google.com", GooglePage.class)
                .enterTextToSearch("Reuters")
                .getFirstSearchResult()).isEqualTo(expectedSearchResult);
    }
}
