package com.example.jupiter;

import lombok.extern.log4j.Log4j2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import static com.codeborne.selenide.Configuration.browser;
import static com.codeborne.selenide.Configuration.browserSize;
import static java.lang.System.getProperty;
import static java.util.Optional.ofNullable;

@Log4j2
public class AllureEnvironmentExtension implements SuiteExtension {

    @Override
    public void afterSuite() {
        createAllureEnvironmentFile();
    }

    private void createAllureEnvironmentFile() {
        final String pathToFile = "%s/environment.properties".formatted(getProperty("allure.results.directory"));

        try (FileOutputStream fos = new FileOutputStream(pathToFile)) {

            Properties props = new Properties();
            props.setProperty("selenide.browser", browser);
            props.setProperty("selenide.browserSize", browserSize);
            ofNullable(getProperty("os.name")).ifPresent(property -> props.setProperty("os", property));
            ofNullable(getProperty("user.name")).ifPresent(property -> props.setProperty("user", property));
            ofNullable(getProperty("java.version")).ifPresent(property -> props.setProperty("java", property));

            props.store(fos, null);
        } catch (IOException e) {
            log.error("IO problem when writing allure.properties file", e);
        }
    }
}
