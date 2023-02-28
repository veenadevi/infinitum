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

package com.qualitrix.infinitum.logging.slf4j;

import com.qualitrix.infinitum.logging.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

/**
 * Writes log messages using SLF4J API.
 */
final class SLF4JLogger implements Logger {
    private final org.slf4j.Logger destination;

    /**
     * Creates a logger for writing log messages using SLF4J API.
     *
     * @param type The class after which the logger should be named.
     */
    SLF4JLogger(final Class<?> type) {
        destination = LoggerFactory.getLogger(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(final String message) {
        destination.debug(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(final String format, final Object context) {
        destination.debug(format, context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(final String format, final Object arg1, final Object arg2) {
        destination.debug(format, arg1, arg2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(final String format, final Object... args) {
        destination.debug(format, args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(final String message) {
        destination.error(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(final String format, final Object context) {
        destination.error(format, context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(final String format, final Object arg1, final Object arg2) {
        destination.error(format, arg1, arg2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(final String format, final Object... args) {
        destination.error(format, args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(Throwable throwable, String message) {
        destination.error(message, throwable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(Throwable throwable, final String format, final Object context) {
        destination.error(MessageFormatter.format(format, context).getMessage()
            , throwable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(final Throwable throwable, final String format, final Object arg1, final Object arg2) {
        destination.error(MessageFormatter.format(format, arg1, arg2).getMessage()
            , throwable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(final Throwable throwable, final String format, final Object... args) {
        destination.error(MessageFormatter.format(format, args).getMessage()
            , throwable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(final String message) {
        destination.info(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(final String format, final Object context) {
        destination.info(format, context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(final String format, final Object arg1, final Object arg2) {
        destination.info(format, arg1, arg2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(final String format, final Object... args) {
        destination.info(format, args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(final String message) {
        destination.warn(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(final String format, final Object context) {
        destination.warn(format, context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(final String format, final Object arg1, final Object arg2) {
        destination.warn(format, arg1, arg2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(final String format, final Object... args) {
        destination.warn(format, args);
    }
}
