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

import com.qualitrix.infinitum.TriConsumer;
import com.qualitrix.infinitum.UnitTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link PrintStreamLogger}.
 */
abstract class PrintStreamLoggerTest implements UnitTest {
    private ByteArrayOutputStream stream;

    /**
     * Initializes a buffer to accumulate log messages.
     */
    @BeforeMethod
    public void setup() {
        stream = new ByteArrayOutputStream();
    }

    /**
     * Tests that an information message can be logged successfully.
     */
    @Test
    public void testDebug() {
        testWriteMessage(getLogger(stream)::debug);
    }

    /**
     * Tests that a debug message with contextual information can be
     * logged successfully.
     */
    @Test
    public void testDebugWithMultipleContextualArguments() {
        testWriteMessageWithMultipleContextualArguments(getLogger(stream)::debug);
    }

    /**
     * Tests that a debug message with contextual information can be
     * logged successfully.
     */
    @Test
    public void testDebugWithSingleContextualArgument() {
        testWriteMessageWithSingleContextualArgument(getLogger(stream)::debug);
    }

    /**
     * Tests that a debug message with contextual information can be logged
     * successfully.
     */
    @Test
    public void testDebugWithTwoContextualArguments() {
        testWriteMessageWithTwoContextualArguments(getLogger(stream)::debug);
    }

    /**
     * Tests that an error can be logged successfully.
     */
    @Test
    public void testError() {
        testWriteMessage(getLogger(stream)::error);
    }

    /**
     * Tests that an error with contextual information can be logged
     * successfully.
     */
    @Test
    public void testErrorWithMultipleContextualArguments() {
        testWriteMessageWithMultipleContextualArguments(getLogger(stream)::error);
    }

    /**
     * Tests that an error with contextual information can be logged
     * successfully.
     */
    @Test
    public void testErrorWithMultipleContextualArgumentsAndThrowable() {
        final String message = "Quantity must be between %s and %s, and a multiple of %s.";
        final Object[] context = { getInt(), getInt(), getInt() };

        final Throwable throwable = new RuntimeException(getString());

        // Log a message with the context.
        getLogger(stream).error(throwable, message, context);

        final String log = stream.toString();

        // Ensure that the message got logged successfully.
        assertNotNull(log);
        assertTrue(log.contains(String.format(message, context[0], context[1], context[2])));
        assertTrue(log.contains(throwable.getMessage()));
    }

    /**
     * Tests that an error with contextual information can be logged
     * successfully.
     */
    @Test
    public void testErrorWithSingleContextualArgument() {
        testWriteMessageWithSingleContextualArgument(getLogger(stream)::error);
    }

    /**
     * Tests that an error with contextual information can be logged
     * successfully.
     */
    @Test
    public void testErrorWithSingleContextualArgumentAndThrowable() {
        final String message = "Invalid quantity %d.";
        final int context = getInt();

        final Throwable throwable = new RuntimeException(getString());

        // Log a message with the context.
        getLogger(stream).error(throwable, message, context);

        final String log = stream.toString();

        // Ensure that the message got logged successfully.
        assertNotNull(log);
        assertTrue(log.contains(String.format(message, context)));
        assertTrue(log.contains(throwable.getMessage()));
    }

    /**
     * Tests that an error can be logged successfully.
     */
    @Test
    public void testErrorWithThrowable() {
        final String message = "Log message.";

        final Throwable throwable = new RuntimeException(getString());

        // Log a message.
        getLogger(stream).error(throwable, message);

        final String log = stream.toString();

        // Ensure that the message got logged successfully.
        assertNotNull(log);
        assertTrue(log.contains(message));
        assertTrue(log.contains(throwable.getMessage()));
    }

    /**
     * Tests that an error with contextual information can be logged
     * successfully.
     */
    @Test
    public void testErrorWithTwoContextualArguments() {
        testWriteMessageWithTwoContextualArguments(getLogger(stream)::error);
    }

    /**
     * Tests that an error with contextual information can be logged
     * successfully.
     */
    @Test
    public void testErrorWithTwoContextualArgumentsAndThrowable() {
        final String message = "Quantity must be between %d and %d.";
        final int[] context = { getInt(), getInt() };

        final Throwable throwable = new RuntimeException(getString());

        // Log a message with the context.
        getLogger(stream).error(throwable, message, context[0], context[1]);

        final String log = stream.toString();

        // Ensure that the message got logged successfully.
        assertNotNull(log);
        assertTrue(log.contains(String.format(message, context[0], context[1])));
    }

    /**
     * Tests that an informational message can be logged successfully.
     */
    @Test
    public void testInfo() {
        testWriteMessage(getLogger(stream)::info);
    }

    /**
     * Tests that an informational message with contextual information can be
     * logged successfully.
     */
    @Test
    public void testInfoWithMultipleContextualArguments() {
        testWriteMessageWithMultipleContextualArguments(getLogger(stream)::info);
    }

    /**
     * Tests that an informational message with contextual information can be
     * logged successfully.
     */
    @Test
    public void testInfoWithSingleContextualArgument() {
        testWriteMessageWithSingleContextualArgument(getLogger(stream)::info);
    }

    /**
     * Tests that an informational message with contextual information can be
     * logged successfully.
     */
    @Test
    public void testInfoWithTwoContextualArguments() {
        testWriteMessageWithTwoContextualArguments(getLogger(stream)::info);
    }

    /**
     * Tests that an information message can be logged successfully.
     */
    @Test
    public void testWarn() {
        testWriteMessage(getLogger(stream)::warn);
    }

    /**
     * Tests that a warning with contextual information can be logged
     * successfully.
     */
    @Test
    public void testWarnWithMultipleContextualArguments() {
        testWriteMessageWithMultipleContextualArguments(getLogger(stream)::warn);
    }

    /**
     * Tests that a warning with contextual information can be logged
     * successfully.
     */
    @Test
    public void testWarnWithSingleContextualArgument() {
        testWriteMessageWithSingleContextualArgument(getLogger(stream)::warn);
    }

    /**
     * Tests that a warning with contextual information can be logged
     * successfully.
     */
    @Test
    public void testWarnWithTwoContextualArguments() {
        testWriteMessageWithTwoContextualArguments(getLogger(stream)::warn);
    }

    /**
     * Gets a {@link Logger} for running tests.
     *
     * @param stream An {@link OutputStream} to which log messages should be
     * written for the tests to validate that they are getting generated
     * successfully.
     *
     * @return A {@link Logger}.
     */
    abstract Logger getLogger(final OutputStream stream);

    /**
     * Tests that a message can be written using a specific method.
     *
     * @param method The method to use for writing the message.
     */
    private void testWriteMessage(final Consumer<String> method) {
        final String message = "Log message.";

        // Log a message.
        method.accept(message);

        final String log = stream.toString();

        // Ensure that the message got logged successfully.
        assertNotNull(log);
        assertTrue(log.contains(message));
    }

    /**
     * Tests that a message with contextual information can be written using a
     * specific method.
     *
     * @param method The method to use for writing the message.
     */
    private void testWriteMessageWithMultipleContextualArguments(final BiConsumer<String, Object[]> method) {
        final String message = "Quantity must be between %s and %s, and a multiple of %s.";
        final Object[] context = { getInt(), getInt(), getInt() };

        // Log a message with the context.
        method.accept(message, context);

        final String log = stream.toString();

        // Ensure that the message got logged successfully.
        assertNotNull(log);
        assertTrue(log.contains(String.format(message, context[0], context[1], context[2])));
    }

    /**
     * Tests that a message with contextual information can be written using a
     * specific method.
     *
     * @param method The method to use for writing the message.
     */
    private void testWriteMessageWithSingleContextualArgument(final BiConsumer<String, Object> method) {
        final String message = "Invalid quantity %d.";
        final int context = getInt();

        // Log a message with the context.
        method.accept(message, context);

        final String log = stream.toString();

        // Ensure that the message got logged successfully.
        assertNotNull(log);
        assertTrue(log.contains(String.format(message, context)));
    }

    /**
     * Tests that a message with contextual information can be written using a
     * specific method.
     *
     * @param method The method to use for writing the message.
     */
    private void testWriteMessageWithTwoContextualArguments(final TriConsumer<String, Object, Object> method) {
        final String message = "Quantity must be between %d and %d.";
        final int[] context = { getInt(), getInt() };

        // Log a message with the context.
        method.accept(message, context[0], context[1]);

        final String log = stream.toString();

        // Ensure that the message got logged successfully.
        assertNotNull(log);
        assertTrue(log.contains(String.format(message, context[0], context[1])));
    }
}
