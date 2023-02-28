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
 * Unit tests for {@link PrintStreamReporter}.
 */
abstract class PrintStreamReporterTest implements UnitTest {
    private ByteArrayOutputStream stream;

    /**
     * Initializes a buffer to accumulate reporting messages.
     */
    @BeforeMethod
    public void setup() {
        stream = new ByteArrayOutputStream();
    }

    /**
     * Tests that messages can be reported under a particular category.
     */
    @Test
    public void testAssignCategory() {
        final String category = getString(),
            message = getString();

        // Log a message.
        getReporter(stream).assignCategory(category)
                           .info(message);

        final String log = stream.toString();

        // Ensure that the message got logged successfully.
        assertNotNull(log);
        assertTrue(log.contains(category));
        assertTrue(log.contains(message));
    }

    /**
     * Tests that messages can be reported for a particular device.
     */
    @Test
    public void testAssignDevice() {
        final String device = getString(),
            message = getString();

        // Log a message.
        getReporter(stream).assignDevice(device)
                           .info(message);

        final String log = stream.toString();

        // Ensure that the message got logged successfully.
        assertNotNull(log);
        assertTrue(log.contains(device));
        assertTrue(log.contains(message));
    }

    /**
     * Tests that an error can be logged successfully.
     */
    @Test
    public void testError() {
        testWriteMessage(getReporter(stream)::error);
    }

    /**
     * Tests that an error with contextual information can be logged successfully.
     */
    @Test
    public void testErrorWithMultipleContextualArguments() {
        testWriteMessageWithMultipleContextualArguments(getReporter(stream)::error);
    }

    /**
     * Tests that an error with contextual information can be logged successfully.
     */
    @Test
    public void testErrorWithSingleContextualArgument() {
        testWriteMessageWithSingleContextualArgument(getReporter(stream)::error);
    }

    /**
     * Tests that an error with contextual information can be logged successfully.
     */
    @Test
    public void testErrorWithTwoContextualArguments() {
        testWriteMessageWithTwoContextualArguments(getReporter(stream)::error);
    }

    /**
     * Tests that a failure message can be logged successfully.
     */
    @Test
    public void testFail() {
        testWriteMessage(getReporter(stream)::fail);
    }

    /**
     * Tests that a failure message with contextual information can be logged
     * successfully.
     */
    @Test
    public void testFailWithMultipleContextualArguments() {
        testWriteMessageWithMultipleContextualArguments(getReporter(stream)::fail);
    }

    /**
     * Tests that a failure message with contextual information can be logged
     * successfully.
     */
    @Test
    public void testFailWithSingleContextualArgument() {
        testWriteMessageWithSingleContextualArgument(getReporter(stream)::fail);
    }

    /**
     * Tests that a failure message with contextual information can be logged
     * successfully.
     */
    @Test
    public void testFailWithTwoContextualArguments() {
        testWriteMessageWithTwoContextualArguments(getReporter(stream)::fail);
    }

    /**
     * Tests that an informational message can be logged successfully.
     */
    @Test
    public void testInfo() {
        testWriteMessage(getReporter(stream)::info);
    }

    /**
     * Tests that an informational message with contextual information can be
     * logged successfully.
     */
    @Test
    public void testInfoWithMultipleContextualArguments() {
        testWriteMessageWithMultipleContextualArguments(getReporter(stream)::info);
    }

    /**
     * Tests that an informational message with contextual information can be
     * logged successfully.
     */
    @Test
    public void testInfoWithSingleContextualArgument() {
        testWriteMessageWithSingleContextualArgument(getReporter(stream)::info);
    }

    /**
     * Tests that an informational message with contextual information can be
     * logged successfully.
     */
    @Test
    public void testInfoWithTwoContextualArguments() {
        testWriteMessageWithTwoContextualArguments(getReporter(stream)::info);
    }

    /**
     * Tests that a pass message can be logged successfully.
     */
    @Test
    public void testPass() {
        testWriteMessage(getReporter(stream)::pass);
    }

    /**
     * Tests that a pass message with contextual information can be logged
     * successfully.
     */
    @Test
    public void testPassWithMultipleContextualArguments() {
        testWriteMessageWithMultipleContextualArguments(getReporter(stream)::pass);
    }

    /**
     * Tests that a pass message with contextual information can be logged
     * successfully.
     */
    @Test
    public void testPassWithSingleContextualArgument() {
        testWriteMessageWithSingleContextualArgument(getReporter(stream)::pass);
    }

    /**
     * Tests that a pass message with contextual information can be logged
     * successfully.
     */
    @Test
    public void testPassWithTwoContextualArguments() {
        testWriteMessageWithTwoContextualArguments(getReporter(stream)::pass);
    }

    /**
     * Gets a {@link Reporter} for running tests.
     *
     * @param stream An {@link OutputStream} to which the reports should be
     * written for the tests to validate that they are getting generated
     * successfully.
     *
     * @return A {@link Reporter}.
     */
    abstract Reporter getReporter(final OutputStream stream);

    /**
     * Tests that a message can be written using a specific method.
     *
     * @param method The method to use for writing the message.
     */
    private void testWriteMessage(final Consumer<String> method) {
        final String message = "A message.";

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
