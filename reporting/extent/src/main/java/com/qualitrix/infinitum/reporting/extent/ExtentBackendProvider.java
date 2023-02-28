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

package com.qualitrix.infinitum.reporting.extent;

import com.aventstack.extentreports.reporter.ConfigurableReporter;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.qualitrix.infinitum.common.ConfigurationAware;
import com.qualitrix.infinitum.util.ClasspathUtil;

import java.io.File;
import java.util.Optional;

/**
 * Provides a backend for writing Extent reports. Decouples the task of writing
 * reporting messages from "where" they are written. Currently, only HTML
 * backend is supported, even though Extent Reports itself provides support for
 * more backends.
 */
final class ExtentBackendProvider extends ConfigurationAware {
    private static final String CONFIGURATION_PARAMETER_CONFIG_FILE_PATH = "infinitum.reporting.extent.config";

    private static final String CONFIGURATION_PARAMETER_REPORT_PATH = "infinitum.reporting.extent.report";

    private static final String PATH_REPORT_DEFAULT = "report/extent.html";

    /**
     * Gets an Extent Reports HTML backend to use for generating reports.
     *
     * @return An Extent Reports backend.
     */
    ConfigurableReporter getBackend() {
        // Create a backend for writing Extent reports.
        final ExtentHtmlReporter reporter = new ExtentHtmlReporter(getReportPath());

        // Attempt to find path to Extent configuration, then attempt to
        // load Extent configuration from the specified path, and finally
        // initialize the backend with the loaded configuration.
        Optional.ofNullable(getConfiguration())
                .ifPresent(config -> reporter.loadXMLConfig(config, false));

        return reporter;
    }

    /**
     * Gets Extent configuration, if available.
     *
     * @return A {@link File} if custom Extent configuration is
     * provided in the application configuration and is available on the
     * runtime application classpath, {@code null} otherwise.
     */
    File getConfiguration() {
        return Optional.ofNullable(getConfigurationFilePath())
                       .map(ClasspathUtil::getResourceFile)
                       .orElse(null);
    }

    /**
     * Gets the path to the Extent Configuration file from the application
     * configuration as the value of the configuration parameter
     * {@code infinitum.reporting.extent.config}.
     *
     * @return Path to the Extent configuration file if configured,
     * {@code null} otherwise.
     */
    String getConfigurationFilePath() {
        return getConfigurationService().getString(CONFIGURATION_PARAMETER_CONFIG_FILE_PATH);
    }

    /**
     * Gets the path where the report must be saved as the value of the
     * configuration parameter {@code infinitum.reporting.extent.report}.
     *
     * @return Path to the Extent report file if configured,
     * {@code reports/extent.html} otherwise.
     */
    String getReportPath() {
        return getConfigurationService().getString(CONFIGURATION_PARAMETER_REPORT_PATH, PATH_REPORT_DEFAULT);
    }
}
