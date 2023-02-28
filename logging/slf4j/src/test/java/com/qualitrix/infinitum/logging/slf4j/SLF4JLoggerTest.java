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

import com.qualitrix.infinitum.TriConsumer;
import com.qualitrix.infinitum.UnitTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link SLF4JLogger}.
 */
public class SLF4JLoggerTest implements UnitTest {
    private ByteArrayOutputStream stream;

    private SLF4JLogger subject;

    /**
     * Initializes a buffer to accumulate log messages and prepares to write
     * log messages to the buffer from where they can be examined.
     */
    @BeforeMethod
    public void setup() {
        stream = new ByteArrayOutputStream();

        System.setOut(new PrintStream(stream));

        subject = new SLF4JLogger(getClass());
    }

    /**
     * Tests that an information message can be logged successfully.
     */
    @Test
    public void testDebug() {
        testWriteMessage(subject::debug);
    }

    /**
     * Tests that a debug message with contextual information can be
     * logged successfully.
     */
    @Test
    public void testDebugWithMultipleContextualArguments() {
        testWriteMessageWithMultipleContextualArguments(subject::debug);
    }

    /**
     * Tests that a debug message with contextual information can be
     * logged successfully.
     */
    @Test
    public void testDebugWithSingleContextualArgument() {
        testWriteMessageWithSingleContextualArgument(subject::debug);
    }

    /**
     * Tests that a debug message with contextual information can be logged
     * successfully.
     */
    @Test
    public void testDebugWithTwoContextualArguments() {
        testWriteMessageWithTwoContextualArguments(subject::debug);
    }

    /**
     * Tests that an error can be logged successfully.
     */
    @Test
    public void testError() {
        testWriteMessage(subject::error);
    }

    /**
     * Tests that an error with contextual information can be logged
     * successfully.
     */
    @Test
    public void testErrorWithMultipleContextualArguments() {
        testWriteMessageWithMultipleContextualArguments(subject::error);
    }

    /**
     * Tests that an error with contextual information can be logged
     * successfully.
     */
    @Test
    public void testErrorWithMultipleContextualArgumentsAndThrowable() {
        // Log a message with context.
        subject.error(new RuntimeException(getString())
            , "Quantity must be between {} and {}, and a multiple of {}."
            , getInt()
            , getInt()
            , getInt());

        final String log = stream.toString();

        // Ensure that the message got logged successfully.
        assertNotNull(log);
    }

    /**
     * Tests that an error with contextual information can be logged
     * successfully.
     */
    @Test
    public void testErrorWithSingleContextualArgument() {
        testWriteMessageWithSingleContextualArgument(subject::error);
    }

    /**
     * Tests that an error with contextual information can be logged
     * successfully.
     */
    @Test
    public void testErrorWithSingleContextualArgumentAndThrowable() {
        // Log a message with context.
        subject.error(new RuntimeException(getString())
            , "Invalid quantity {}."
            , getInt());

        final String log = stream.toString();

        // Ensure that the message got logged successfully.
        assertNotNull(log);
    }

    /**
     * Tests that an error can be logged successfully.
     */
    @Test
    public void testErrorWithThrowable() {
        // Log a message.
        subject.error(new RuntimeException(getString()), "Log message.");

        final String log = stream.toString();

        // Ensure that the message got logged successfully.
        assertNotNull(log);
    }

    /**
     * Tests that an error with contextual information can be logged
     * successfully.
     */
    @Test
    public void testErrorWithTwoContextualArguments() {
        testWriteMessageWithTwoContextualArguments(subject::error);
    }

    /**
     * Tests that an error with contextual information can be logged
     * successfully.
     */
    @Test
    public void testErrorWithTwoContextualArgumentsAndThrowable() {
        // Log a message with context.
        subject.error(new RuntimeException(getString())
            , "Quantity must be between {} and {}."
            , getInt()
            , getInt());

        final String log = stream.toString();

        // Ensure that the message got logged successfully.
        assertNotNull(log);
    }

    /**
     * Tests that an informational message can be logged successfully.
     */
    @Test
    public void testInfo() {
        testWriteMessage(subject::info);
    }

    /**
     * Tests that an informational message with contextual information can be
     * logged successfully.
     */
    @Test
    public void testInfoWithMultipleContextualArguments() {
        testWriteMessageWithMultipleContextualArguments(subject::info);
    }

    /**
     * Tests that an informational message with contextual information can be
     * logged successfully.
     */
    @Test
    public void testInfoWithSingleContextualArgument() {
        testWriteMessageWithSingleContextualArgument(subject::info);
    }

    /**
     * Tests that an informational message with contextual information can be
     * logged successfully.
     */
    @Test
    public void testInfoWithTwoContextualArguments() {
        testWriteMessageWithTwoContextualArguments(subject::info);
    }

    /**
     * Tests that an information message can be logged successfully.
     */
    @Test
    public void testWarn() {
        testWriteMessage(subject::warn);
    }

    /**
     * Tests that a warning with contextual information can be logged
     * successfully.
     */
    @Test
    public void testWarnWithMultipleContextualArguments() {
        testWriteMessageWithMultipleContextualArguments(subject::warn);
    }

    /**
     * Tests that a warning with contextual information can be logged
     * successfully.
     */
    @Test
    public void testWarnWithSingleContextualArgument() {
        testWriteMessageWithSingleContextualArgument(subject::warn);
    }

    /**
     * Tests that a warning with contextual information can be logged
     * successfully.
     */
    @Test
    public void testWarnWithTwoContextualArguments() {
        testWriteMessageWithTwoContextualArguments(subject::warn);
    }

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
        final String message = "Quantity must be between {} and {}, and a multiple of {}.";
        final Object[] context = { getInt(), getInt(), getInt() };

        // Log a message with the context.
        method.accept(message, context);

        final String log = stream.toString();

        // Ensure that the message got logged successfully.
        assertNotNull(log);
    }

    /**
     * Tests that a message with contextual information can be written using a
     * specific method.
     *
     * @param method The method to use for writing the message.
     */
    private void testWriteMessageWithSingleContextualArgument(final BiConsumer<String, Object> method) {
        final String message = "Invalid quantity {}.";
        final int context = getInt();

        // Log a message with the context.
        method.accept(message, context);

        final String log = stream.toString();

        // Ensure that the message got logged successfully.
        assertNotNull(log);
    }

    /**
     * Tests that a message with contextual information can be written using a
     * specific method.
     *
     * @param method The method to use for writing the message.
     */
    private void testWriteMessageWithTwoContextualArguments(final TriConsumer<String, Object, Object> method) {
        final String message = "Quantity must be between {} and {}.";
        final int[] context = { getInt(), getInt() };

        // Log a message with the context.
        method.accept(message, context[0], context[1]);

        final String log = stream.toString();

        // Ensure that the message got logged successfully.
        assertNotNull(log);
    }
}
