package com.example.aspects;


import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.util.ObjectUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.qameta.allure.model.Status.BROKEN;
import static io.qameta.allure.model.Status.PASSED;
import static io.qameta.allure.util.ResultsUtils.getStatus;
import static io.qameta.allure.util.ResultsUtils.getStatusDetails;
import static java.util.UUID.randomUUID;

@SuppressWarnings("unused")
@Aspect
public class AllureAssertAspect {

    private final AllureLifecycle lifecycle = Allure.getLifecycle();

    @Pointcut("execution(!private org.assertj.core.api.AbstractAssert.new(..))")
    public void anyAssertCreation() {
    }

    @Pointcut("execution(public * org.assertj.core.api.AbstractAssert.*(..))")
    public void anyAssert() {
    }

    @After("anyAssertCreation()")
    public void logAssertActual(JoinPoint joinPoint) {
        final String actual = joinPoint.getArgs().length > 0 ? ObjectUtils.toString(joinPoint.getArgs()[0]) : "<?>";
        final String uuid = randomUUID().toString();
        final String name = "assertThat('%s')".formatted(actual);

        final StepResult result = new StepResult()
                .setName(name)
                .setStatus(PASSED);

        lifecycle.startStep(uuid, result);
        lifecycle.stopStep(uuid);
    }

    @Before("anyAssert()")
    public void logAssertExpected(JoinPoint joinPoint) {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final String uuid = randomUUID().toString();
        final String name = joinPoint.getArgs().length > 0
                ? String.format("%s('%s')", methodSignature.getName(), arrayToString(joinPoint.getArgs()))
                : methodSignature.getName();

        final StepResult result = new StepResult().setName(name);

        lifecycle.startStep(uuid, result);
    }

    @AfterReturning(
            pointcut = "anyAssert()"
    )
    public void assertPassed() {
        lifecycle.updateStep((s) -> s.setStatus(PASSED));
        lifecycle.stopStep();
    }

    @AfterThrowing(
            pointcut = "anyAssert()",
            throwing = "e"
    )
    public void assertFailed(Throwable e) {
        lifecycle.updateStep((s) -> s
                .setStatus(getStatus(e).orElse(BROKEN))
                .setStatusDetails((getStatusDetails(e).orElse(null))));
        lifecycle.stopStep();
    }

    private String arrayToString(Object... array) {
        return Stream.of(array).map(ObjectUtils::toString).collect(Collectors.joining(" "));
    }
}

