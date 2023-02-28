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

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.qualitrix.infinitum.annotation.Author;
import com.qualitrix.infinitum.reporting.BaseReportingService;
import com.qualitrix.infinitum.reporting.Reporter;
import org.testng.annotations.Test;

import java.util.Optional;
import java.util.UUID;

/**
 * <p>
 * Provides access to a {@link Reporter} that can generate HTML reports using
 * Extent Reports. Follow the steps below to use Extent Reports for a test
 * automation project:
 * </p>
 *
 * <ol>
 *     <li>Add the parameter {@code infinitum.reporting.provider}
 *     to the application configuration and set its value to {@code extent},
 *     e.g. <b>{@code infinitum.reporting.provider=extent}</b> or
 *     <b>{@code infinitum.reporting.provider: extent}</b>. Configuring this
 *     parameter is mandatory - if this is not done, Extent reporting will not
 *     be available for the application.</li>
 *     <li>Optionally, add the parameter
 *     {@code infinitum.reporting.extent.config} and set its value to a relative
 *     path on the runtime application classpath from where an Extent Reports
 *     configuration file can be loaded. For example, if an Extent Reports
 *     configuration file exists in the project's directory structure at
 *     {@code src/test/resources/reporting/extent/config.xml}, the parameter can
 *     be configured as
 *     <b>{@code infinitum.reporting.extent.config=reporting/extent/config.xml}</b>
 *     or
 *     <b>{@code infinitum.reporting.extent.config: reporting/extent/config.xml}</b>
 *     (note that the path prefix {@code src/test/resources} must not be
 *     included in the configuration path, as it is already part of the runtime
 *     classpath). If the parameter is not configured, Extent Reports will be
 *     initialized with defaults.</li>
 *     <li>Optionally, add the parameter
 *     {@code infinitum.reporting.extent.report} and set its value to a path
 *     where Extent Reports should save the HTML report. For example, if the
 *     report should be saved at {@code reports/final/extent.html}, the
 *     parameter can be configured as
 *     <b>{@code infinitum.reporting.extent.report=reports/final/extent.html}</b>
 *     or
 *     <b>{@code infinitum.reporting.extent.report: reports/final/extent.html}</b>
 *     If the parameter is not configured, the report will be saved to
 *     <b>{@code report/extent.html}</b>.</li>
 * </ol>
 */
public class ExtentReportingService extends BaseReportingService {
    private static final String PROVIDER = "EXTENT";

    private final boolean available;

    private ExtentReports reports;

    /**
     * Creates an Extent reporting service.
     */
    public ExtentReportingService() {
        // Check whether Extent reporting is enabled for the application.
        available = PROVIDER.equalsIgnoreCase(getProvider());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Reporter getReporter(final Test test, final Author author) {
        if (reports == null) {
            synchronized (ExtentReportingService.class) {
                reports = new ExtentReports();
                reports.attachReporter(new ExtentBackendProvider().getBackend());
            }
        }

        // Create an Extent test with the specified name and description. If
        // the test name is unspecified, assign a dynamically generated test
        // name, as required by the Extent Reports API.
        final String testName = Optional.ofNullable(test)
                                        .map(Test::testName)
                                        .orElseGet(() -> UUID.randomUUID().toString()),
            testDescription = Optional.ofNullable(test)
                                      .map(Test::description)
                                      .orElse(null);
        final ExtentTest extentTest = reports.createTest(testName, testDescription);

        Optional.ofNullable(author)
                .map(Author::name)
                .ifPresent(extentTest::assignAuthor);

        return new ExtentReporter(extentTest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAvailable() {
        return available;
    }
}
