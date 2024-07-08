package com.example.jupiter;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Map;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

public class SelenoidExtension implements BeforeAllCallback {

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
        Configuration.remote = "http://localhost:4444/wd/hub";
        Configuration.browserCapabilities.setCapability("selenoid:options", Map.of(
                "enableVNC", false, "enableVideo", false, "enableLog", false));
    }
}
