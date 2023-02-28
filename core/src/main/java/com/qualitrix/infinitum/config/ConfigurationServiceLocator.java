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

import com.qualitrix.infinitum.ServiceLocator;
import com.qualitrix.infinitum.logging.LoggingServiceLocator;

/**
 * <p>
 * Provides access to application configuration in an implementation-agnostic
 * manner. Automatically detects possible sources of configuration and uses the
 * first one that indicates that it is available. See implementations of
 * {@link ConfigurationService} to understand how the application decides which
 * sources are available.
 * </p>
 *
 * <p>
 * Clients should load configuration by calling
 * {@code ConfigurationServiceLocator.getInstance().getConfigurationService()}.
 * </p>
 */
public final class ConfigurationServiceLocator
    extends ServiceLocator {
    private final ConfigurationService configurationService;

    /**
     * Loads a {@link ConfigurationService} using Java Service Provider
     * Interface (SPI). The first available implementation is used.
     */
    private ConfigurationServiceLocator() {
        configurationService = getFirstAvailable(ConfigurationService.class);

        LoggingServiceLocator.getInstance()
                             .getLoggingService()
                             .getLogger(ConfigurationServiceLocator.class)
                             .debug(String.format("%s selected as ConfigurationService."
                                 , configurationService.getClass().getName()));
    }

    /**
     * Gets an instance of this class.
     *
     * @return A {@link ConfigurationServiceLocator}.
     */
    public static ConfigurationServiceLocator getInstance() {
        return SingletonHolder.SINGLETON;
    }

    /**
     * Gets configuration for the current application. Guaranteed to never
     * return {@code null}, so users can confidently call methods on the
     * returned value without having to worry about
     * {@link NullPointerException}s.
     *
     * @return A {@link ConfigurationService}.
     */
    public ConfigurationService getConfigurationService() {
        return configurationService;
    }

    /**
     * Holds a singleton instance of the service locator.
     */
    private static final class SingletonHolder {
        private static final ConfigurationServiceLocator SINGLETON = new ConfigurationServiceLocator();
    }
}
