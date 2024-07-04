package com.example.pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class GooglePage {

    @Step("Enter {0} text to search")
    public GooglePage enterTextToSearch(String text) {
        $(byId("APjFqb"))
                .setValue(text)
                .shouldHave(value(text))
                .pressEnter();

        return this;
    }

    public String getFirstSearchResult() {
        return $$("#search h3")
                .first()
                .shouldBe(visible)
                .getText();
    }
}
