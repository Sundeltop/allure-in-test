package com.example.jupiter;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

public interface SuiteExtension extends BeforeAllCallback {

    @Override
    default void beforeAll(ExtensionContext extensionContext) {
        extensionContext
                .getStore(GLOBAL)
                .getOrComputeIfAbsent(this.getClass(), key -> {
                    beforeSuite();
                    return (ExtensionContext.Store.CloseableResource) this::afterSuite;
                });
    }

    default void beforeSuite() {
    }

    default void afterSuite() {
    }
}
