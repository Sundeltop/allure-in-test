package com.example.annotations;

import com.example.jupiter.AllureEnvironmentExtension;
import com.example.jupiter.AllureSelenideExtension;
import com.example.jupiter.AssertFailAllureScreenshotExtension;
import com.example.jupiter.SelenoidExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(TYPE)
@ExtendWith({
        AssertFailAllureScreenshotExtension.class,
        AllureEnvironmentExtension.class,
        AllureSelenideExtension.class,
        SelenoidExtension.class,
})
public @interface WebTest {
}
