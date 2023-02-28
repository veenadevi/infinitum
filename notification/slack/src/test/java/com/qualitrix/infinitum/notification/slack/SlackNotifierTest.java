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

package com.qualitrix.infinitum.notification.slack;

import com.qualitrix.infinitum.UnitTest;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * Unit tests for {@link SlackNotifier}.
 */
public class SlackNotifierTest implements UnitTest {
    private Slack slack;

    private SlackNotifier subject;

    /**
     * Sets up data required for running tests.
     */
    @BeforeClass
    public void setup() {
        slack = mock(Slack.class);

        subject = new SlackNotifier(slack, getString(), getString());
    }

    /**
     * Tests that adding recipients has no effect since Slack messages must
     * be sent to a single channel.
     */
    @Test
    public void testAddRecipients() {
        assertNotNull(subject.addRecipients());
    }

    /**
     * Tests that clearing recipients has no effect since Slack messages must
     * be sent to a particular channel.
     */
    @Test
    public void testClearRecipients() {
        assertNotNull(subject.clearRecipients());
    }

    /**
     * Tests that notification messages can be sent to a Slack channel.
     */
    @Test
    public void testNotify() {
        try {
            final ChatPostMessageResponse response = mock(ChatPostMessageResponse.class);
            when(response.isOk()).thenReturn(true);

            final MethodsClient methods = mock(MethodsClient.class);
            when(methods.chatPostMessage((ChatPostMessageRequest) any())).thenReturn(response);

            when(slack.methods()).thenReturn(methods);

            assertTrue(subject.notify(getString()));
        }
        catch (final Exception e) {
            fail();
        }
    }

    /**
     * Tests that notification messages cannot be sent to a Slack channel when
     * an exception occurs.
     */
    @Test
    public void testNotifyWithException() {
        try {
            final MethodsClient methods = mock(MethodsClient.class);
            when(methods.chatPostMessage((ChatPostMessageRequest) any())).thenThrow(new RuntimeException("Simulated exception on invoking Slack API."));

            when(slack.methods()).thenReturn(methods);

            assertFalse(subject.notify(getString()));
        }
        catch (final Exception e) {
            fail();
        }
    }
}
