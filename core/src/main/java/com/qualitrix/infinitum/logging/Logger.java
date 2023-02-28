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

/**
 * Contract for logging messages.Supported log levels in increasing order of
 * severity (and thereby concern)are {@code DEBUG}, {@code INFO}, {@code WARN}
 * and {@code ERROR}.
 */
public interface Logger {
    /**
     * Writes a log message at {@code DEBUG} log level.
     *
     * @param message The message to write.
     */
    void debug(String message);

    /**
     * Writes a log message at {@code DEBUG} log level using a specified
     * format for the message and an additional contextual value.
     *
     * @param format The message format.
     * @param context Context to include in the message.
     */
    void debug(String format, Object context);

    /**
     * Writes a log message at {@code DEBUG} log level using a specified
     * format for the message and two additional contextual values.
     *
     * @param format The message format.
     * @param arg1 First contextual value to include in the message.
     * @param arg2 Second contextual value to include in the message.
     */
    void debug(String format, Object arg1, Object arg2);

    /**
     * Writes a log message at {@code DEBUG} log level using a specified
     * format for the message and additional contextual values.
     *
     * @param format The message format.
     * @param args Contextual values to include in the message.
     */
    void debug(String format, Object... args);

    /**
     * Writes a log message at {@code ERROR} log level.
     *
     * @param message The message to write.
     */
    void error(String message);

    /**
     * Writes a log message at {@code ERROR} log level using a specified
     * format for the message and an additional contextual value.
     *
     * @param format The message format.
     * @param context Context to include in the message.
     */
    void error(String format, Object context);

    /**
     * Writes a log message at {@code ERROR} log level using a specified
     * format for the message and two additional contextual values.
     *
     * @param format The message format.
     * @param arg1 First contextual value to include in the message.
     * @param arg2 Second contextual value to include in the message.
     */
    void error(String format, Object arg1, Object arg2);

    /**
     * Writes a log message at {@code ERROR} log level using a specified
     * format for the message and additional contextual values.
     *
     * @param format The message format.
     * @param args Contextual values to include in the message.
     */
    void error(String format, Object... args);

    /**
     * Writes an exception along with a log message at {@code ERROR} log level.
     *
     * @param throwable The exception that generated the log message.
     * @param message The message to write.
     */
    void error(Throwable throwable, String message);

    /**
     * Writes an exception along with a log message at {@code ERROR} log level
     * using a specified format for the message and an additional contextual
     * value.
     *
     * @param throwable The exception that generated the log message.
     * @param format The message format.
     * @param context Context to include in the message.
     */
    void error(Throwable throwable, String format, Object context);

    /**
     * Writes an exception along with a log message at {@code ERROR} log level
     * using a specified format for the message and two additional contextual
     * values.
     *
     * @param throwable The exception that generated the log message.
     * @param format The message format.
     * @param arg1 First contextual value to include in the message.
     * @param arg2 Second contextual value to include in the message.
     */
    void error(Throwable throwable, String format, Object arg1, Object arg2);

    /**
     * Writes an exception along with a log message at {@code ERROR} log level
     * using a specified format for the message and additional contextual
     * values.
     *
     * @param throwable The exception that generated the log message.
     * @param format The message format.
     * @param args Contextual values to include in the message.
     */
    void error(Throwable throwable, String format, Object... args);

    /**
     * Writes a log message at {@code INFO} log level.
     *
     * @param message The message to write.
     */
    void info(String message);

    /**
     * Writes a log message at {@code INFO} log level using a specified
     * format for the message and an additional contextual value.
     *
     * @param format The message format.
     * @param context Context to include in the message.
     */
    void info(String format, Object context);

    /**
     * Writes a log message at {@code INFO} log level using a specified
     * format for the message and two additional contextual values.
     *
     * @param format The message format.
     * @param arg1 First contextual value to include in the message.
     * @param arg2 Second contextual value to include in the message.
     */
    void info(String format, Object arg1, Object arg2);

    /**
     * Writes a log message at {@code INFO} log level using a specified
     * format for the message and additional contextual values.
     *
     * @param format The message format.
     * @param args Contextual values to include in the message.
     */
    void info(String format, Object... args);

    /**
     * Writes a log message at {@code WARN} log level.
     *
     * @param message The message to write.
     */
    void warn(String message);

    /**
     * Writes a log message at {@code WARN} log level using a specified
     * format for the message and an additional contextual value.
     *
     * @param format The message format.
     * @param context Context to include in the message.
     */
    void warn(String format, Object context);

    /**
     * Writes a log message at {@code WARN} log level using a specified
     * format for the message and two additional contextual values.
     *
     * @param format The message format.
     * @param arg1 First contextual value to include in the message.
     * @param arg2 Second contextual value to include in the message.
     */
    void warn(String format, Object arg1, Object arg2);

    /**
     * Writes a log message at {@code WARN} log level using a specified
     * format for the message and additional contextual values.
     *
     * @param format The message format.
     * @param args Contextual values to include in the message.
     */
    void warn(String format, Object... args);
}
