/*
 * Copyright (c) 2021-2022 Qualitrix Technologies Pvt Ltd.  All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to use the
 * Software without restriction, subject to the following conditions:
 *
 * THE SOFTWARE MUST HAVE BEEN PROVIDED BY THE ORIGINAL AUTHORS OR AN AUTHORIZED
 * SIGNATORY THEREOF. THE PERSON TO WHOM THE SOFTWARE HAS BEEN PROVIDED MAY USE IT
 * FOR THE PURPOSE FOR WHICH IT HAS BEEN PROVIDED, AND EXTEND IT TO MEET THEIR
 * NEEDS. HOWEVER, THE PERSON TO WHOM THE SOFTWARE HAS BEEN PROVIDED MAY NOT SELL,
 * MODIFY, DISTRIBUTE, PUBLISH, MERGE, LICENSE OR SUBLICENSE IT TO ANYONE ELSE.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.qualitrix.infinitum.config;

import java.util.Optional;

/**
 * Provides the name of the configuration to use for the application. The
 * default name for reading configuration is {@code infinitum}. However,
 * applications can specify their own configuration name by passing it using the
 * Java system property {@code infinitum.configuration.name}. For example, an
 * application that wishes to use the configuration name {@code my-config} can
 * pass it as {@code -Dinfinitum.configuration.name=my-config}.
 */
public final class ConfigurationNameProvider {
    private static final ConfigurationNameProvider INSTANCE = new ConfigurationNameProvider();

    static final String DEFAULT_NAME = "infinitum";

    static final String SYSTEM_PROPERTY_NAME = "infinitum.configuration.name";

    /**
     * Deliberately hidden to prevent direct instantiation.
     */
    private ConfigurationNameProvider() {
    }

    /**
     * Gets the {@code singleton} instance of this class.
     *
     * @return A {@link ConfigurationNameProvider}.
     */
    public static ConfigurationNameProvider getInstance() {
        return INSTANCE;
    }

    /**
     * Gets the name of the configuration used for this application.
     *
     * @return The name of the configuration used for this application.
     */
    public String getConfigurationName() {
        return Optional.ofNullable(System.getProperty(SYSTEM_PROPERTY_NAME))
                       .orElse(DEFAULT_NAME);
    }
}
