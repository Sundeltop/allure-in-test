package com.example;

import com.codeborne.selenide.logevents.SelenideLogger;
import com.example.jupiter.AllureEnvironmentExtension;
import com.example.jupiter.AssertFailAllureScreenshotExtension;
import com.example.pages.GooglePage;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({AssertFailAllureScreenshotExtension.class, AllureEnvironmentExtension.class})
public class GoogleTest {

    @BeforeAll
    static void setupAllureReports() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true)
        );
    }

    @Test
    void verifySearchResultIsDisplayed() {
        final String expectedSearchResult = "Reuters | Breaking International News & Views";

        assertThat(open("https://www.google.com", GooglePage.class)
                .enterTextToSearch("Reuters")
                .getFirstSearchResult()).isEqualTo(expectedSearchResult);
    }
}
