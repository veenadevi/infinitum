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

import io.appium.java_client.MobileDriver;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.testng.annotations.Test;

import static org.mockito.Mockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;

/**
 * Unit tests for {@link AutoConfigurableMobileDriverService}.
 */
public class AutoConfigurableMobileDriverServiceTest extends DeviceDriverServiceTest {
    private final AutoConfigurableMobileDriverService subject = new AutoConfigurableMobileDriverService();

    /**
     * Tests that a driver can be created for running tests on an Android
     * device.
     */
    @Test
    public void testCreateDriverForAndroid() {
        final HttpCommandExecutor executor = getMockCommandExecutor();

        final AutoConfigurableMobileDriverService subject = mock(AutoConfigurableMobileDriverService.class);
        when(subject.getCapabilities()).thenReturn(CAPABILITIES);
        when(subject.getCommandExecutor()).thenReturn(executor);
        when(subject.getPlatform()).thenReturn(AutoConfigurableMobileDriverService.MobilePlatform.ANDROID);
        when(subject.createDriver()).thenCallRealMethod();

        assertNotNull(subject.createDriver());
    }

    /**
     * Tests that a driver can be created for running tests on an iOS
     * device.
     */
    @Test
    public void testCreateDriverForIOS() {
        final HttpCommandExecutor executor = getMockCommandExecutor();

        final AutoConfigurableMobileDriverService subject = mock(AutoConfigurableMobileDriverService.class);
        when(subject.getCapabilities()).thenReturn(CAPABILITIES);
        when(subject.getCommandExecutor()).thenReturn(executor);
        when(subject.getPlatform()).thenReturn(AutoConfigurableMobileDriverService.MobilePlatform.IOS);
        when(subject.createDriver()).thenCallRealMethod();

        assertNotNull(subject.createDriver());
    }

    /**
     * Tests that a driver cannot be created for an unknown mobile platform.
     */
    @Test
    public void testCreateDriverWithUnknownPlatform() {
        final AutoConfigurableMobileDriverService subject = mock(AutoConfigurableMobileDriverService.class);
        when(subject.getPlatform()).thenReturn(null);
        when(subject.createDriver()).thenCallRealMethod();

        assertNull(subject.createDriver());
    }

    /**
     * Tests that expected capabilities of the device on which tests will be run
     * can be determined from the application configuration.
     */
    @Test
    public void testGetCapabilities() {
        assertNotNull(subject.getCapabilities());
    }

    /**
     * Tests that a connection to an Appium server can be obtained using
     * information provided in the application configuration.
     */
    @Test
    public void testGetCommandExecutor() {
        assertNotNull(subject.getCommandExecutor());
    }

    /**
     * Tests that device-specific capabilities needed for the tests to run
     * can be determined from the application configuration.
     */
    @Test
    public void testGetDeviceCapabilities() {
        assertNotNull(subject.getDeviceCapabilities());
    }

    /**
     * Tests that a prefix that can be used for reading configuration settings
     * specific to a type of device can be determined from the application
     * configuration.
     */
    @Test
    public void testGetDeviceTypePrefix() {
        final String prefix = subject.getDeviceTypePrefix();

        assertNotNull(prefix);
        assertFalse(prefix.isEmpty());
        assertTrue(prefix.endsWith(subject.getPlatform().name().toLowerCase()));
    }

    /**
     * Tests that a driver can be obtained for running tests on an Appium
     * server.
     */
    @Test
    public void testGetDriver() {
        final AutoConfigurableMobileDriverService subject = mock(AutoConfigurableMobileDriverService.class);
        doReturn(mock(MobileDriver.class)).when(subject).createDriver();
        when(subject.getDriver()).thenCallRealMethod();

        for (int i = 0; i < getInt(10, 20); ++i) {
            assertNotNull(subject.getDriver());
        }
    }

    /**
     * Tests that a URL for connecting to an Appium server can be obtained
     * from the application configuration.
     */
    @Test
    public void testGetURL() {
        assertNotNull(subject.getURL());
    }

    /**
     * Tests that a URL for connecting to an Appium server cannot be obtained
     * if the scheme, host or port for the URL is invalid.
     */
    @Test(expectedExceptions = RuntimeException.class)
    public void testGetURLWithMalformedURLSpecification() {
        final AutoConfigurableMobileDriverService target = spy(subject);
        when(target.getURLSpecification()).thenReturn(getString());

        target.getURL();
    }

    /**
     * Tests that a service is considered available if an appropriate driver
     * is available.
     */
    @Test
    public void testIsAvailable() {
        assertTrue(subject.isAvailable());
    }

    /**
     * Tests that a service is considered unavailable if the name of the device
     * on which tests need to be run is not available.
     */
    @Test
    public void testIsAvailableWithNullDeviceName() {
        final AutoConfigurableMobileDriverService target = spy(subject);
        when(target.getDeviceName()).thenReturn(null);

        assertFalse(target.isAvailable());
    }

    /**
     * Tests that a service is considered unavailable if the platform for
     * which tests need to be run is not available.
     */
    @Test
    public void testIsAvailableWithNullPlatform() {
        final AutoConfigurableMobileDriverService target = spy(subject);
        when(target.getPlatform()).thenReturn(null);

        assertFalse(target.isAvailable());
    }

    /**
     * Tests that a service is considered unavailable if the version of the
     * platform for which tests need to be run is not available.
     */
    @Test
    public void testIsAvailableWithNullPlatformVersion() {
        final AutoConfigurableMobileDriverService target = spy(subject);
        when(target.getPlatformVersion()).thenReturn(null);

        assertFalse(target.isAvailable());
    }

    /**
     * Tests that a service is considered unavailable if the URL of the Appium
     * server on which tests need to be run is not available.
     */
    @Test
    public void testIsAvailableWithNullURLSpecification() {
        final AutoConfigurableMobileDriverService target = spy(subject);
        when(target.getURLSpecification()).thenReturn(null);

        assertFalse(target.isAvailable());
    }
}
