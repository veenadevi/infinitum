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

package com.qualitrix.infinitum.logging;

import com.qualitrix.infinitum.ServiceLocator;

/**
 * <p>
 * Provides a {@link LoggingService} in an implementation-agnostic manner.
 * Automatically detects possible ways of writing log messages and uses the
 * first one that indicates that it is available.
 * </p>
 *
 * <p>
 * Clients should get a {@link LoggingService} by calling
 * {@code LoggingServiceLocator.getInstance().getLoggingService()}.
 * </p>
 */
public final class LoggingServiceLocator
    extends ServiceLocator {
    private final LoggingService service;

    /**
     * Loads a {@link LoggingService} using Java Service Provider Interface
     * (SPI). The first available implementation is used.
     */
    private LoggingServiceLocator() {
        service = getFirstAvailable(LoggingService.class);

        service.getLogger(LoggingServiceLocator.class)
               .debug(String.format("%s selected as LoggingService."
                   , service.getClass().getName()));
    }

    /**
     * Gets an instance of this class.
     *
     * @return A {@link LoggingServiceLocator}.
     */
    public static LoggingServiceLocator getInstance() {
        return SingletonHolder.SINGLETON;
    }

    /**
     * Gets a {@link LoggingService} for the current application. Guaranteed to
     * never return {@code null}, so users can confidently call methods on the
     * returned value without having to worry about
     * {@link NullPointerException}s.
     *
     * @return A {@link LoggingService}.
     */
    public LoggingService getLoggingService() {
        return service;
    }

    /**
     * Holds a singleton instance of the service locator.
     */
    private static final class SingletonHolder {
        private static final LoggingServiceLocator SINGLETON = new LoggingServiceLocator();
    }
}
