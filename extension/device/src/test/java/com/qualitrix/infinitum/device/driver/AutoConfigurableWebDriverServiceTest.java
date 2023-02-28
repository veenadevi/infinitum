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

package com.qualitrix.infinitum.device.driver;

import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import static org.mockito.Mockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Unit tests for {@link AutoConfigurableWebDriverService}.
 */
public class AutoConfigurableWebDriverServiceTest extends DeviceDriverServiceTest {
    private final AutoConfigurableWebDriverService subject = new AutoConfigurableWebDriverService();

    /**
     * Tests that a driver cannot be obtained for a Selenium server.
     */
    @Test
    public void testCreateDriver() {
        final CommandExecutor executor = getMockCommandExecutor();

        final AutoConfigurableWebDriverService subject = mock(AutoConfigurableWebDriverService.class);
        when(subject.getCapabilities()).thenReturn(CAPABILITIES);
        when(subject.getCommandExecutor()).thenReturn(executor);
        when(subject.createDriver()).thenCallRealMethod();

        assertNotNull(subject.createDriver());
    }

    /**
     * Tests that the browser configuration to use for running tests on the
     * Selenium server can be determined from the application configuration.
     */
    @Test
    public void testGetCapabilities() {
        assertNotNull(subject.getCapabilities());
    }

    /**
     * Tests that a connection to a Selenium server can be obtained using
     * information provided in the application configuration.
     */
    @Test
    public void testGetCommandExecutor() {
        assertNotNull(subject.getCommandExecutor());
    }

    /**
     * Tests that a driver can be obtained for running tests on a Selenium
     * server.
     */
    @Test
    public void testGetDriver() {
        final AutoConfigurableWebDriverService subject = mock(AutoConfigurableWebDriverService.class);
        doReturn(mock(RemoteWebDriver.class)).when(subject).createDriver();
        when(subject.getDriver()).thenCallRealMethod();

        for (int i = 0; i < getInt(10, 20); ++i) {
            assertNotNull(subject.getDriver());
        }
    }

    /**
     * Tests that a URL for connecting to a Selenium server can be obtained
     * from the application configuration.
     */
    @Test
    public void testGetURL() {
        assertNotNull(subject.getURL());
    }

    /**
     * Tests that a URL for connecting to a Selenium server cannot be obtained
     * if the scheme, host or port for the URL is invalid.
     */
    @Test(expectedExceptions = RuntimeException.class)
    public void testGetURLWithMalformedURLSpecification() {
        final AutoConfigurableWebDriverService target = spy(subject);
        when(target.getURLSpecification()).thenReturn(getString());

        target.getURL();
    }

    /**
     * Tests that a service is considered available if an appropriate driver
     * configuration is available.
     */
    @Test
    public void testIsAvailable() {
        assertTrue(subject.isAvailable());
    }

    /**
     * Tests that a service is considered unavailable if the name of the browser
     * on which tests need to be run is not available.
     */
    @Test
    public void testIsAvailableWithNullBrowserName() {
        final AutoConfigurableWebDriverService target = spy(subject);
        when(target.getBrowserName()).thenReturn(null);

        assertFalse(target.isAvailable());
    }

    /**
     * Tests that a service is considered unavailable if the URL of the Selenium
     * server on which tests need to be run is not available.
     */
    @Test
    public void testIsAvailableWithNullURLSpecification() {
        final AutoConfigurableWebDriverService target = spy(subject);
        when(target.getURLSpecification()).thenReturn(null);

        assertFalse(target.isAvailable());
    }
}
