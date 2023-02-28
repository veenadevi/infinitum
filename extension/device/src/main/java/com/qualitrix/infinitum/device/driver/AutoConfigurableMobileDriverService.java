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

import com.qualitrix.infinitum.common.ConfigurationAware;
import com.qualitrix.infinitum.util.StringUtil;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p>
 * Detects, loads, initializes and unloads a driver for running tests on a
 * mobile device, e.g. a smartphone or tablet.
 * </p>
 *
 * <p>
 * Requires Appium Server to be running and accessible via a fixed HTTP/HTTPS
 * URL - for example, {@code https://ci.domain.com/}. The URL must be configured
 * in the application configuration.
 * </p>
 */
public class AutoConfigurableMobileDriverService
    extends ConfigurationAware
    implements MobileDriverService {
    private static final String CAPABILITY_DEVICE_NAME = "deviceName";

    private static final String CAPABILITY_PLATFORM_VERSION = "platformVersion";

    private static final String CONFIGURATION_PARAMETER_DEVICE_NAME = "infinitum.device.mobile.device.name";

    private static final String CONFIGURATION_PARAMETER_PLATFORM_NAME = "infinitum.device.mobile.platform.name";

    private static final String CONFIGURATION_PARAMETER_PLATFORM_TYPE_PREFIX = "infinitum.device.mobile";

    private static final String CONFIGURATION_PARAMETER_PLATFORM_VERSION = "infinitum.device.mobile.platform.version";

    private static final String CONFIGURATION_PARAMETER_URL = "infinitum.device.mobile.url";

    private static final AtomicBoolean INITIALIZED = new AtomicBoolean(false);

    private volatile MobileDriver<MobileElement> driver;

    /**
     * {@inheritDoc}
     */
    @Override
    public MobileDriver<MobileElement> getDriver() {
        if (!INITIALIZED.get()) {
            synchronized (INITIALIZED) {
                INITIALIZED.set(true);

                driver = createDriver();
            }
        }

        return driver;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAvailable() {
        return StringUtil.isNotBlank(getDeviceName())
            && getPlatform() != null
            && StringUtil.isNotBlank(getPlatformVersion())
            && StringUtil.isNotBlank(getURLSpecification());
    }

    /**
     * Creates a driver for an Appium server running on a fixed, known URL.
     *
     * @return An Appium driver if one gets created successfully, {@code null}
     * otherwise.
     */
    MobileDriver<MobileElement> createDriver() {
        if (getPlatform() == null) {
            return null;
        }

        if (MobilePlatform.ANDROID.equals(getPlatform())) {
            return new AndroidDriver<>(getCommandExecutor(), getCapabilities());
        }

        return new IOSDriver<>(getCommandExecutor(), getCapabilities());
    }

    /**
     * Gets expected capabilities of the device on which tests will be run.
     *
     * @return Device capabilities for the tests to run.
     */
    Capabilities getCapabilities() {
        final DesiredCapabilities capabilities = new DesiredCapabilities(getDeviceCapabilities());

        Optional.ofNullable(getDeviceName())
                .ifPresent(deviceName -> capabilities.setCapability(CAPABILITY_DEVICE_NAME, deviceName));
        Optional.ofNullable(getPlatformVersion())
                .ifPresent(platformVersion -> capabilities.setCapability(CAPABILITY_PLATFORM_VERSION, platformVersion));

        return capabilities;
    }

    /**
     * Gets an executor that can be used for executing HTTP commands on an
     * Appium server running at a known URL, either locally or remotely.
     *
     * @return An executor that can be used for executing HTTP commands on an
     * Appium server running at a known URL.
     */
    HttpCommandExecutor getCommandExecutor() {
        return new HttpCommandExecutor(getURL());
    }

    /**
     * Gets device-specific capabilities needed for the tests to run.
     *
     * @return A map containing device-specific capabilities as key-value pairs.
     */
    Map<String, String> getDeviceCapabilities() {
        return getConfigurationService().getMap(getDeviceTypePrefix());
    }

    /**
     * Gets the name of the mobile device on which tests will be run.
     *
     * @return The name of the mobile device on which tests will be run.
     */
    String getDeviceName() {
        return getConfigurationService().getString(CONFIGURATION_PARAMETER_DEVICE_NAME);
    }

    /**
     * Gets a prefix that can be used for reading configuration settings
     * specific to the type of device for which tests need to be run.
     *
     * @return A prefix that can be used for reading configuration settings.
     */
    String getDeviceTypePrefix() {
        return String.format("%s.%s"
            , CONFIGURATION_PARAMETER_PLATFORM_TYPE_PREFIX
            , getPlatform().name().toLowerCase());
    }

    /**
     * Gets the mobile platform for which tests need to be run, e.g.
     * {@code ANDROID} or {@code IOS}.
     *
     * @return The mobile platform for which tests need to be run.
     */
    MobilePlatform getPlatform() {
        return getConfigurationService().getEnum(MobilePlatform.class, CONFIGURATION_PARAMETER_PLATFORM_NAME);
    }

    /**
     * Gets the mobile platform version for which tests need to be run.
     *
     * @return The mobile platform version for which tests need to be run.
     */
    String getPlatformVersion() {
        return getConfigurationService().getString(CONFIGURATION_PARAMETER_PLATFORM_VERSION);
    }

    /**
     * Gets the URL to use for connecting to the Appium server on which
     * tests will be run.
     *
     * @return A {@link URL}.
     */
    URL getURL() {
        try {
            return new URL(getURLSpecification());
        }
        catch (final MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads the URL on which Appium server is running from the application
     * configuration.
     *
     * @return The URL on which Appium server is running.
     */
    String getURLSpecification() {
        return getConfigurationService().getString(CONFIGURATION_PARAMETER_URL);
    }

    /**
     * A mobile platform on which tests can be run.
     */
    enum MobilePlatform {
        ANDROID, IOS
    }
}
