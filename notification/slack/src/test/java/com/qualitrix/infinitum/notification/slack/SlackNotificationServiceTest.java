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
import com.qualitrix.infinitum.config.ConfigurationService;
import com.qualitrix.infinitum.config.ConfigurationServiceLocator;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link SlackNotificationService}.
 */
@PowerMockIgnore("javax.net.ssl.*")
@PrepareForTest(ConfigurationServiceLocator.class)
public class SlackNotificationServiceTest
    extends PowerMockTestCase
    implements UnitTest {
    private SlackNotificationService subject;

    /**
     * Sets up data required for running tests.
     */
    @BeforeClass
    public void setup() {
        subject = new SlackNotificationService();
    }

    /**
     * Tests that the service always returns a notifier.
     */
    @Test
    public void testGetNotifier() {
        assertNotNull(subject.getNotifier());
    }

    /**
     * Tests that Slack notifications are available if Slack has been configured
     * as the notification backend.
     */
    @Test
    public void testIsAvailableWithConfiguration() {
        assertTrue(subject.isAvailable());
    }

    /**
     * Tests that Slack notifications are unavailable if Slack has not been
     * configured as the notification backend.
     */
    @Test
    public void testIsAvailableWithoutConfiguration() {
        final ConfigurationService configurationService = mock(ConfigurationService.class);
        when(configurationService.getString(any())).thenReturn(getString());

        final ConfigurationServiceLocator configurationServiceLocator = mock(ConfigurationServiceLocator.class);
        when(configurationServiceLocator.getConfigurationService()).thenReturn(configurationService);
        mockStatic(ConfigurationServiceLocator.class);
        when(ConfigurationServiceLocator.getInstance()).thenReturn(configurationServiceLocator);

        assertFalse(new SlackNotificationService().isAvailable());
    }
}
