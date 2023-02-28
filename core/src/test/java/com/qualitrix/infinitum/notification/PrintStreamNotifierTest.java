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

package com.qualitrix.infinitum.notification;

import com.qualitrix.infinitum.UnitTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link PrintStreamNotifier}.
 */
abstract class PrintStreamNotifierTest implements UnitTest {
    private ByteArrayOutputStream stream;

    /**
     * Initializes a buffer to accumulate notification messages.
     */
    @BeforeMethod
    public void setup() {
        stream = new ByteArrayOutputStream();
    }

    /**
     * Tests that recipients can be reset as and when required.
     */
    @Test
    public void testClearRecipients() {
        final String message = getString();

        // Send a notification message to the recipient.
        final Notifier notifier = getNotifier(stream);
        assertTrue(notifier.addRecipient(getString())
                           .notify(message));

        final String notification = stream.toString();

        // Ensure that the message was sent successfully.
        assertNotNull(notification);
        assertTrue(notification.contains(message));

        // Clear existing recipients and add new.
        notifier.clearRecipients();

        // Ensure that messages cannot be sent anymore because there is no
        // recipient to send the messages to.
        assertFalse(notifier.notify(message));

        // Add a new recipient and ensure that the message gets sent
        // successfully.
        assertTrue(notifier.addRecipient(getString())
                           .notify(message));
    }

    /**
     * Tests that a notification message can include multiple contextual
     * arguments.
     */
    @Test
    public void testNotifyMessageWithMultipleContextualArguments() {
        final String message = "Test suite containing %d tests for %d modules completed in %d seconds.";
        final Object[] context = { getInt(), getInt(), getInt() };

        // Send a notification message.
        assertTrue(getNotifier(stream).addRecipient(getString())
                                      .notify(message, context));
    }

    /**
     * Tests that a notification message can be sent to multiple recipients.
     */
    @Test
    public void testNotifyMessageWithMultipleRecipients() {
        final String message = getString();

        // Add recipients.
        final Notifier notifier = getNotifier(stream);

        for (int i = 0; i < getInt(11, 20); ++i) {
            notifier.addRecipient(getString());
        }

        // Send a notification message.
        assertTrue(notifier.notify(message));

        final String notification = stream.toString();

        // Ensure that the message was sent successfully.
        assertNotNull(notification);
        assertTrue(notification.contains(message));
    }

    /**
     * Tests that a notification message can include a contextual argument.
     */
    @Test
    public void testNotifyMessageWithSingleContextualArgument() {
        final String message = "Test suite containing %d tests completed.";

        // Send a notification message.
        assertTrue(getNotifier(stream).addRecipient(getString())
                                      .notify(message, getInt()));
    }

    /**
     * Tests that a notification message can be sent to a single recipient.
     */
    @Test
    public void testNotifyMessageWithSingleRecipient() {
        final String message = getString(),
            recipient = getString();

        // Send a notification message.
        assertTrue(getNotifier(stream).addRecipient(recipient)
                                      .notify(message));

        final String notification = stream.toString();

        // Ensure that the message was sent successfully.
        assertNotNull(notification);
        assertTrue(notification.contains(message));
        assertTrue(notification.contains(recipient));
    }

    /**
     * Tests that a notification message can include two contextual arguments.
     */
    @Test
    public void testNotifyMessageWithTwoContextualArguments() {
        final String message = "Test suite containing %d tests for %d modules completed.";

        // Send a notification message.
        assertTrue(getNotifier(stream).addRecipient(getString())
                                      .notify(message, getInt(), getInt()));
    }

    /**
     * Tests that a notification message cannot be sent if no recipient has
     * been specified.
     */
    @Test
    public void testNotifyMessageWithoutRecipient() {
        final String message = getString();

        // Send a notification message.
        assertFalse(getNotifier(stream).notify(message));
    }

    /**
     * Gets a {@link Notifier} for running tests.
     *
     * @param stream An {@link OutputStream} to which the notification messages
     * should be sent for the tests to validate that they are getting sent
     * successfully.
     *
     * @return A {@link Notifier}.
     */
    abstract Notifier getNotifier(final OutputStream stream);
}
