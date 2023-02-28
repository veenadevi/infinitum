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
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p>
 * Detects, loads, initializes and unloads a driver for running tests on a
 * web browser.
 * </p>
 *
 * <p>
 * Requires Selenium Server to be running and accessible via a fixed HTTP/HTTPS
 * URL - for example, {@code https://ci.domain.com/}. The URL must be configured
 * in the application configuration.
 * </p>
 *
 * <p>
 * The following settings must be configured in the application configuration:
 * </p>
 *
 * <ol>
 *     <li>{@code infinitum.device.web.url}: URL to a running Selenium server
 *     that will be used for running the tests. The server can be run in
 *     standalone or grid mode, depending on the needs of the application
 *     under test. The URL must be accessible from the machine on which the
 *     tests are run. A plain local installation of the Selenium server
 *     running in standalone mode is usually accessible at
 *     {@code http://localhost:4444}. <b>This setting is mandatory.</b></li>
 *     <li>{@code infinitum.device.web.browser.name}: name of the browser to
 *     use for running tests. Usually this corresponds to the name of a Selenium
 *     driver - e.g. {@code infinitum.device.web.browser.name=chrome} for
 *     Google Chrome, {@code infinitum.device.web.browser.name=firefox} for
 *     Mozilla Firefox, and so on. The Selenium server whose URL is provided
 *     in the configuration must have a Selenium driver corresponding to the
 *     specified browser name. The server on which Selenium server is run
 *     must also have the requested browser installed and Selenium must have
 *     access to the installed browser. <b>This setting is mandatory.</b></li>
 *     <li>{@code infinitum.device.web.browser.version}: browser version to use
 *     for running tests. This setting is optional.</li>
 *     <li>{@code infinitum.device.web.browser.version}: browser version to use
 *     for running tests. This setting is optional.</li>
 *     <li>{@code infinitum.device.web.platform}: name of the platform for
 *     which tests should be run - e.g. Linux, Windows or macOS. This setting is
 *     optional.</li>
 *     <li>{@code infinitum.device.web.javascript.enabled}: whether Javascript
 *     should be enabled on the browser on which tests are to be run. This
 *     setting is optional.</li>
 * </ol>
 *
 * <p>
 * It is possible to run the same set of tests with different web browsers.
 * The suggested way to do this would be to create one configuration file per
 * browser and run the tests with a specific configuration file at a time.
 * See documentation for the configuration service for details on how to run
 * tests with a specific configuration file.
 * </p>
 *
 * @see com.qualitrix.infinitum.config.ConfigurationService
 */
public class AutoConfigurableWebDriverService
    extends ConfigurationAware
    implements WebDriverService {
    private static final String CONFIGURATION_PARAMETER_BROWSER_NAME = "infinitum.device.web.browser.name";

    private static final String CONFIGURATION_PARAMETER_BROWSER_VERSION = "infinitum.device.web.browser.version";

    private static final String CONFIGURATION_PARAMETER_JAVASCRIPT_ENABLED = "infinitum.device.web.javascript.enabled";

    private static final String CONFIGURATION_PARAMETER_PLATFORM = "infinitum.device.web.platform";

    private static final String CONFIGURATION_PARAMETER_URL = "infinitum.device.web.url";

    private static final AtomicBoolean INITIALIZED = new AtomicBoolean(false);

    private volatile WebDriver driver;

    /**
     * {@inheritDoc}
     */
    @Override
    public WebDriver getDriver() {
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
        return StringUtil.isNotBlank(getBrowserName())
            && StringUtil.isNotBlank(getURLSpecification());
    }

    /**
     * Creates a remote web driver for a Selenium server running on a fixed,
     * known URL.
     *
     * @return A web driver if one gets created successfully, {@code null}
     * otherwise.
     */
    RemoteWebDriver createDriver() {
        return new RemoteWebDriver(getCommandExecutor(), getCapabilities());
    }

    /**
     * Reads the name of the browser to use for running tests from the
     * application configuration.
     *
     * @return The name of the browser to use for running tests from the
     * application configuration.
     */
    String getBrowserName() {
        return getConfigurationService().getString(CONFIGURATION_PARAMETER_BROWSER_NAME);
    }

    /**
     * Reads the browser version to use for running tests, from the application
     * configuration.
     *
     * @return The browser version to use for running tests from the application
     * configuration.
     */
    String getBrowserVersion() {
        return getConfigurationService().getString(CONFIGURATION_PARAMETER_BROWSER_VERSION);
    }

    /**
     * Gets expected capabilities of the browser on which tests will be run.
     *
     * @return Browser capabilities for the tests to run.
     */
    Capabilities getCapabilities() {
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(getBrowserName());
        capabilities.setJavascriptEnabled(getJavascriptEnabled());

        Optional.ofNullable(getBrowserVersion())
                .ifPresent(capabilities::setVersion);
        Optional.ofNullable(getPlatform())
                .map(String::toUpperCase)
                .map(Platform::valueOf)
                .ifPresent(capabilities::setPlatform);

        return capabilities;
    }

    /**
     * Gets an executor that can be used for executing HTTP commands on a
     * Selenium server running at a known URL, either locally or remotely.
     *
     * @return An executor that can be used for executing HTTP commands on a
     * Selenium server running at a known URL.
     */
    CommandExecutor getCommandExecutor() {
        return new HttpCommandExecutor(getURL());
    }

    /**
     * Reads whether Javascript should be enabled while running tests, from the
     * application configuration.
     *
     * @return Whether Javascript should be enabled while running tests.
     */
    boolean getJavascriptEnabled() {
        return getConfigurationService().getBoolean(CONFIGURATION_PARAMETER_JAVASCRIPT_ENABLED);
    }

    /**
     * Reads the name of the platform for which tests should be run, from the
     * application configuration.
     *
     * @return The name of the platform for which tests should be run, from
     * the application configuration.
     */
    String getPlatform() {
        return getConfigurationService().getString(CONFIGURATION_PARAMETER_PLATFORM);
    }

    /**
     * Gets the URL to use for connecting to the Selenium server on which
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
     * Reads the URL on which Selenium server is running from the application
     * configuration.
     *
     * @return The URL on which Selenium server is running.
     */
    String getURLSpecification() {
        return getConfigurationService().getString(CONFIGURATION_PARAMETER_URL);
    }
}
