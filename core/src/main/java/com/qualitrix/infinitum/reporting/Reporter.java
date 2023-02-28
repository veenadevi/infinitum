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

package com.qualitrix.infinitum.reporting;

/**
 * <p>
 * Contract for reporting events encountered while running a test. The following
 * categories of events are supported, in decreasing order of importance or
 * severity:
 * </p>
 *
 * <ol>
 *     <li><b>Pass</b>: The test has met its desired objective;</li>
 *     <li><b>Fail</b>: The test has encountered a situation where it cannot
 *     continue and has not met its desired objective so far;</li>
 *     <li><b>Error</b>: The test has encountered an unexpected situation
 *     not amounting to a failure and from which it can continue;</li>
 *     <li><b>Info</b>: An informational event, such as progress update, note
 *     or comment.</li>
 * </ol>
 */
public interface Reporter {
    /**
     * Assigns a category to this reporter, e.g. {@code Unit Test},
     * {@code Regression Test}, etc.
     *
     * @param category The category to assign.
     *
     * @return This reporter.
     */
    Reporter assignCategory(String category);

    /**
     * Assigns a device name or identifier to this reporter, e.g.
     * {@code Desktop}, {@code iOS 13.2 Tablet}.
     *
     * @param device The device name or identifier to assign.
     *
     * @return This reporter.
     */
    Reporter assignDevice(String device);

    /**
     * Logs a message in the report as an error. An error represents an
     * unexpected situation encountered while running a test but does not
     * amount to a failure of the test.
     *
     * @param message The message to log.
     */
    void error(String message);

    /**
     * Logs a message in the report as an error by using a specified format for
     * the message and an additional contextual value. The format must be
     * supported by {@link String#format(String, Object...)}. For example,
     * <code>error("Invalid value: %d", 101)</code> logs the message
     * {@code Invalid value: 101}.
     *
     * @param format The message format - e.g. <code>Invalid value: %s</code>.
     * @param context Context to include in the message - e.g. {@code 101}.
     */
    default void error(final String format, final Object context) {
        error(String.format(format, context));
    }

    /**
     * Logs a message in the report as an error by using a specified format for
     * the message and two additional contextual values. For example,
     * <code>error("Quantity must be between %d and %d", 1, 100)</code>
     * logs the message
     * {@code Quantity must be between 1 and 100}.
     *
     * @param format The message format.
     * @param arg1 First contextual value to include in the message.
     * @param arg2 Second contextual value to include in the message.
     */
    default void error(final String format, final Object arg1, final Object arg2) {
        error(String.format(format, arg1, arg2));
    }

    /**
     * Logs a message in the report as an error by using a specified format for
     * the message and additional contextual values. For example,
     * <code>error("Quantity must be between %d and %d, and a multiple of %d", 1, 100, 5)</code>
     * logs the message
     * {@code Quantity must be between 1 and 100, and a multiple of 5}.
     *
     * @param format The message format.
     * @param args Contextual values to include in the message.
     */
    default void error(final String format, final Object... args) {
        error(String.format(format, args));
    }

    /**
     * Logs a failure message in the report. A failure represents an unexpected
     * condition that leads to the test failing to meet its objective and not
     * being able to continue further.
     *
     * @param message The message to log.
     */
    void fail(String message);

    /**
     * Logs a failure message in the report by using a specified format for
     * the message and an additional contextual value. The format must be
     * supported by {@link String#format(String, Object...)}. For example,
     * <code>fail("Login failed for user %s", "john")</code> logs the message
     * {@code Login failed for user john}.
     *
     * @param format The message format.
     * @param context Context to include in the message.
     */
    default void fail(final String format, final Object context) {
        fail(String.format(format, context));
    }

    /**
     * Logs a failure message in the report by using a specified format for
     * the message and two additional contextual values.
     *
     * @param format The message format.
     * @param arg1 First contextual value to include in the message.
     * @param arg2 Second contextual value to include in the message.
     */
    default void fail(final String format, final Object arg1, final Object arg2) {
        fail(String.format(format, arg1, arg2));
    }

    /**
     * Logs a failure message in the report by using a specified format for
     * the message and additional contextual values.
     *
     * @param format The message format.
     * @param args Contextual values to include in the message.
     */
    default void fail(final String format, final Object... args) {
        fail(String.format(format, args));
    }

    /**
     * Logs an informational message in the report. An informational message can
     * be logged to track test progress, to track contextual information, or
     * simply to share notes or comments while running tests.
     *
     * @param message The message to log.
     */
    void info(String message);

    /**
     * Logs an informational message in the report by using a specified format for
     * the message and an additional contextual value. The format must be
     * supported by {@link String#format(String, Object...)}. For example,
     * <code>info("Invalid value: %d", 101)</code> logs the message
     * {@code Invalid value: 101}.
     *
     * @param format The message format - e.g. <code>Invalid value: %s</code>.
     * @param context Context to include in the message - e.g. {@code 101}.
     */
    default void info(final String format, final Object context) {
        info(String.format(format, context));
    }

    /**
     * Logs an informational message in the report by using a specified format for
     * the message and two additional contextual values. For example,
     * <code>info("Quantity must be between %d and %d", 1, 100)</code>
     * logs the message
     * {@code Quantity must be between 1 and 100}.
     *
     * @param format The message format.
     * @param arg1 First contextual value to include in the message.
     * @param arg2 Second contextual value to include in the message.
     */
    default void info(final String format, final Object arg1, final Object arg2) {
        info(String.format(format, arg1, arg2));
    }

    /**
     * Logs an informational message in the report by using a specified format for
     * the message and additional contextual values. For example,
     * <code>info("Quantity must be between %d and %d, and a multiple of %d", 1, 100, 5)</code>
     * logs the message
     * {@code Quantity must be between 1 and 100, and a multiple of 5}.
     *
     * @param format The message format.
     * @param args Contextual values to include in the message.
     */
    default void info(final String format, final Object... args) {
        info(String.format(format, args));
    }

    /**
     * Logs a pass message in the report - indicates that the test met its
     * desired objective.
     *
     * @param message The message to log.
     */
    void pass(String message);

    /**
     * Logs a pass message in the report by using a specified format for
     * the message and an additional contextual value. The format must be
     * supported by {@link String#format(String, Object...)}. For example,
     * <code>pass("User %s logged-in successfully", "john")</code> logs the
     * message {@code User john logged-in successfully}.
     *
     * @param format The message format.
     * @param context Context to include in the message.
     */
    default void pass(final String format, final Object context) {
        pass(String.format(format, context));
    }

    /**
     * Logs a pass message in the report by using a specified format for
     * the message and two additional contextual values.
     *
     * @param format The message format.
     * @param arg1 First contextual value to include in the message.
     * @param arg2 Second contextual value to include in the message.
     */
    default void pass(final String format, final Object arg1, final Object arg2) {
        pass(String.format(format, arg1, arg2));
    }

    /**
     * Logs a pass message in the report by using a specified format for
     * the message and additional contextual values.
     *
     * @param format The message format.
     * @param args Contextual values to include in the message.
     */
    default void pass(final String format, final Object... args) {
        pass(String.format(format, args));
    }
}
