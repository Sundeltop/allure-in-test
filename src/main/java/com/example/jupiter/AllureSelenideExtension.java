package com.example.jupiter;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

public class AllureSelenideExtension implements BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        extensionContext
                .getRoot()
                .getStore(GLOBAL)
                .getOrComputeIfAbsent(this.getClass(), key -> {
                    beforeAll();
                    return null;
                });
    }

    private void beforeAll() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true)
        );
    }
}
