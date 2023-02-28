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

import java.io.PrintStream;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * Writes log messages to a {@link PrintStream}.
 */
abstract class PrintStreamLogger implements Logger {
    private final String name;

    private final PrintStream sink;

    /**
     * Creates a logger for writing reporting messages to a {@link PrintStream}.
     *
     * @param type The class after which the logger should be named.
     * @param stream A {@link PrintStream}.
     */
    protected PrintStreamLogger(final Class<?> type, final PrintStream stream) {
        name = Optional.ofNullable(type)
                       .map(Class::getName)
                       .orElse(null);

        sink = stream;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(final String message) {
        write("[DEBUG]", message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(final String format, final Object context) {
        debug(String.format(format, context));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(final String format, final Object arg1, final Object arg2) {
        debug(String.format(format, arg1, arg2));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(final String format, final Object... args) {
        debug(String.format(format, args));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(final String message) {
        write("[ERROR]", message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(final String format, final Object context) {
        error(String.format(format, context));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(final String format, final Object arg1, final Object arg2) {
        error(String.format(format, arg1, arg2));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(final String format, final Object... args) {
        error(String.format(format, args));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(final Throwable throwable, final String message) {
        error(message);

        Optional.ofNullable(throwable)
                .ifPresent(t -> {
                    error(t.getMessage());
                    t.printStackTrace(sink);
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(final Throwable throwable, final String format, final Object context) {
        error(format, context);

        Optional.ofNullable(throwable)
                .ifPresent(t -> {
                    error(t.getMessage());
                    t.printStackTrace(sink);
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(final Throwable throwable, final String format, final Object arg1, final Object arg2) {
        error(format, arg1, arg2);

        Optional.ofNullable(throwable)
                .ifPresent(t -> {
                    error(t.getMessage());
                    t.printStackTrace(sink);
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(final Throwable throwable, final String format, final Object... args) {
        error(format, args);

        Optional.ofNullable(throwable)
                .ifPresent(t -> {
                    error(t.getMessage());
                    t.printStackTrace(sink);
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(final String message) {
        write("[INFO]", message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(final String format, final Object context) {
        info(String.format(format, context));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(final String format, final Object arg1, final Object arg2) {
        info(String.format(format, arg1, arg2));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(final String format, final Object... args) {
        info(String.format(format, args));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(final String message) {
        write("[WARN]", message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(final String format, final Object context) {
        warn(String.format(format, context));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(final String format, final Object arg1, final Object arg2) {
        warn(String.format(format, arg1, arg2));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(final String format, final Object... args) {
        warn(String.format(format, args));
    }

    /**
     * Writes a message to the print stream, adding a prefix to it.
     *
     * @param prefix The prefix to add to the message.
     * @param message The message to write.
     */
    private void write(final String prefix, final String message) {
        final StringJoiner buffer = new StringJoiner(" ");

        // Add message prefix to the message.
        buffer.add(prefix);

        // Add logger name, if available.
        Optional.ofNullable(name)
                .ifPresent(context -> buffer.add(String.format("[%s]", context)));

        // Add the message.
        buffer.add(message);

        // Write the message.
        sink.printf("%s%n", buffer);
    }
}
