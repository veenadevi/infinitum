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

import com.qualitrix.infinitum.common.ConfigurationAware;
import com.qualitrix.infinitum.config.ConfigurationService;

/**
 * Provides methods common to all {@link NotificationService}s.
 */
public abstract class BaseNotificationService
    extends ConfigurationAware
    implements NotificationService {
    private static final String CONFIGURATION_PARAMETER_PROVIDER = "infinitum.notification.provider";

    private static final String DEFAULT_PROVIDER = "console";

    /**
     * Gets the name of the provider in use for sending notification messages.
     * Looks for the parameter {@code infinitum.notification.provider} in the
     * application configuration. It is not mandatory to configure a provider
     * since a default provider is included with the notification services.
     * However, specific notification implementations may require the provider
     * to be set in order to initialize themselves. Users must consult
     * documentation for specific providers to understand whether the provider
     * name must be configured in the application configuration.
     *
     * @return The name of the notification provider in use for the application
     * if configured, {@code console} otherwise.
     *
     * @see ConfigurationService#getString(String, String)
     */
    protected final String getProvider() {
        return getConfigurationService().getString(CONFIGURATION_PARAMETER_PROVIDER, DEFAULT_PROVIDER);
    }
}
