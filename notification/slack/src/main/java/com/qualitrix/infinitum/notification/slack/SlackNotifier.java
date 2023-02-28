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

import com.qualitrix.infinitum.logging.Logger;
import com.qualitrix.infinitum.logging.LoggingServiceLocator;
import com.qualitrix.infinitum.notification.Notifier;
import com.slack.api.Slack;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;

/**
 * Sends notification messages to a Slack channel.
 */
final class SlackNotifier implements Notifier {
    private static final Logger LOGGER = LoggingServiceLocator.getInstance()
                                                              .getLoggingService()
                                                              .getLogger(SlackNotifier.class);

    private final String channel;

    private final Slack client;

    private final String token;

    /**
     * Creates a notifier for sending messages to a Slack channel.
     *
     * @param client A {@link Slack} client for invoking the Slack API.
     * @param channel The name of the channel to which messages should be sent.
     * @param token API token for authenticating with the Slack API.
     */
    SlackNotifier(final Slack client
        , final String channel
        , final String token) {
        this.channel = channel;
        this.client = client;
        this.token = token;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Notifier addRecipients(final String... recipients) {
        // Do nothing, since messages can be sent to only one channel.

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Notifier clearRecipients() {
        // Do nothing, since messages must be sent to a channel.

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean notify(final String message) {
        // Prepare a Slack API request for sending the notification message to
        // the designated channel.
        final ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                                                                     .channel(channel)
                                                                     .text(message)
                                                                     .token(token)
                                                                     .build();

        try {
            // Post the message in the designated Slack channel.
            return client.methods()
                         .chatPostMessage(request)
                         .isOk();
        }
        catch (final Exception e) {
            LOGGER.error(e
                , String.format("Unable to send notification message to Slack channel %s due to the following error."
                    , channel));
        }

        return false;
    }
}
