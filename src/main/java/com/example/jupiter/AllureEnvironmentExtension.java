package com.example.jupiter;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import static com.codeborne.selenide.Configuration.browser;
import static com.codeborne.selenide.Configuration.browserSize;
import static java.lang.System.getProperty;
import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

@Log4j2
public class AllureEnvironmentExtension implements AfterAllCallback {

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        extensionContext
                .getRoot()
                .getStore(GLOBAL)
                .getOrComputeIfAbsent(this.getClass(), key -> new AfterTestsCallback());
    }

    private static class AfterTestsCallback implements ExtensionContext.Store.CloseableResource {

        @Override
        public void close() {
            createAllureEnvironmentFile();
        }

        private void createAllureEnvironmentFile() {
            final String pathToFile = "%s/environment.properties".formatted(getProperty("allure.results.directory"));

            try (FileOutputStream fos = new FileOutputStream(pathToFile)) {

                Properties props = new Properties();
                props.setProperty("selenide.browser", browser);
                props.setProperty("selenide.browserSize", browserSize);
                props.setProperty("os", getProperty("os.name"));
                props.setProperty("user", getProperty("user.name"));
                props.setProperty("java.version", getProperty("java.version"));

                props.store(fos, null);
            } catch (IOException e) {
                log.error("IO problem when writing allure.properties file", e);
            }
        }
    }
}
