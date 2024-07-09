package com.example.jupiter;

import com.codeborne.selenide.Configuration;

import java.util.Map;

public class SelenoidExtension implements SuiteExtension {

    @Override
    public void beforeSuite() {
        Configuration.remote = "http://localhost:4444/wd/hub";
        Configuration.browserCapabilities.setCapability("selenoid:options", Map.of(
                "enableVNC", false, "enableVideo", false, "enableLog", false));
    }
}
