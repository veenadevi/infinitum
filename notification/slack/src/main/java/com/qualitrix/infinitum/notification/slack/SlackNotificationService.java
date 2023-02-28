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
import com.qualitrix.infinitum.notification.BaseNotificationService;
import com.qualitrix.infinitum.notification.Notifier;
import com.qualitrix.infinitum.util.StringUtil;
import com.slack.api.Slack;

import java.util.stream.Stream;

/**
 * <p>
 * Allows sending notification messages to a Slack channel.
 * </p>
 *
 * <p>
 * Uses the Slack Client Java API for sending notification messages. Follow the
 * steps below to use Slack notifications for a test automation project:
 * </p>
 *
 * <ol>
 *     <li>Add parameter {@code infinitum.notification.provider} to the
 *     application configuration and set its value to {@code slack}, e.g.
 *     <b>{@code infinitum.notification.provider=slack}</b> or
 *     <b>{@code infinitum.notification.provider: slack}</b>. Configuring this
 *     parameter is mandatory - if this is not done, Slack notifications will
 *     not be available for the application.</li>
 *     <li>Add parameter {@code infinitum.notification.slack.channel} and set
 *     its value to a Slack channel to which notification messages should be
 *     send. For example, if messages need to be sent to a channel named
 *     {@code builds}, the parameter can be configured as
 *     <b>{@code infinitum.notification.slack.channel=builds}</b> or
 *     <b>{@code infinitum.notification.slack.channel: builds}</b>. Configuring
 *     this parameter is mandatory - if this is not done, Slack notifications
 *     will not be available for the application.</li>
 *     <li>Add parameter {@code infinitum.notification.slack.token} and set
 *     its value to a Slack API token. API tokens can be obtained by logging
 *     into the Slack web console as an administrator of a Slack team and
 *     generating a new API token. Configuring this parameter is mandatory - if
 *     this is not done, Slack notifications will not be available for the
 *     application.</li>
 * </ol>
 *
 * <p>
 * The API token and the channel must both be under the same Slack team and
 * the user for whom the token was generated must have permissions to post
 * messages to the said channel.
 * </p>
 */
public class SlackNotificationService extends BaseNotificationService {
    private static final String CONFIGURATION_PARAMETER_CHANNEL = "infinitum.notification.slack.channel";

    private static final String CONFIGURATION_PARAMETER_TOKEN = "infinitum.notification.slack.token";

    private static final Logger LOGGER = LoggingServiceLocator.getInstance()
                                                              .getLoggingService()
                                                              .getLogger(SlackNotificationService.class);

    private static final String PROVIDER = "SLACK";

    private final boolean available;

    private final String channel;

    private final Slack client;

    private final String token;

    /**
     * Creates a service for sending notification messages to a Slack channel.
     */
    public SlackNotificationService() {
        channel = getConfigurationService().getString(CONFIGURATION_PARAMETER_CHANNEL);
        token = getConfigurationService().getString(CONFIGURATION_PARAMETER_TOKEN);

        available = Stream.of(PROVIDER.equalsIgnoreCase(getProvider())
                              , StringUtil.isNotBlank(channel)
                              , StringUtil.isNotBlank(token))
                          .allMatch(flag -> flag);

        if (available) {
            LOGGER.debug("Slack notifications available.");
            LOGGER.debug(String.format("Notifications will be sent to channel named %s.", channel));

            client = Slack.getInstance();
        }
        else {
            LOGGER.info("Slack notifications unavailable.");

            client = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Notifier getNotifier() {
        return new SlackNotifier(client, channel, token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAvailable() {
        return available;
    }
}
