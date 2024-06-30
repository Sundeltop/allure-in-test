package com.example;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.LifecycleMethodExecutionExceptionHandler;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.opentest4j.AssertionFailedError;

import java.io.ByteArrayInputStream;

import static java.util.Objects.requireNonNull;
import static org.openqa.selenium.OutputType.BYTES;

public class AssertFailScreenshotExtension implements TestExecutionExceptionHandler, LifecycleMethodExecutionExceptionHandler {

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        if (throwable.getClass().equals(AssertionFailedError.class)) {
            screenshot();
        }
        throw throwable;
    }

    @Override
    public void handleBeforeEachMethodExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        screenshot();
        throw throwable;
    }

    @Override
    public void handleAfterEachMethodExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        screenshot();
        throw throwable;
    }

    @Override
    public void handleAfterAllMethodExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        screenshot();
        throw throwable;
    }

    private void screenshot() {
        if (WebDriverRunner.hasWebDriverStarted()) {
            Allure.addAttachment("Screenshot on test failure", new ByteArrayInputStream(
                    requireNonNull(Selenide.screenshot(BYTES))));
        }
    }
}
