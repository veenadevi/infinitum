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

package $package;

import com.google.inject.Inject;

import com.qualitrix.infinitum.annotation.Author;
import com.qualitrix.infinitum.config.ConfigurationService;
import com.qualitrix.infinitum.inject.Injector;
import com.qualitrix.infinitum.inject.InjectorModule;
import com.qualitrix.infinitum.logging.Logger;
import com.qualitrix.infinitum.logging.LoggingService;
import com.qualitrix.infinitum.reporting.Reporter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * A functional or integration test that uses Infinitum components for
 * fast, reliable and flexible development of automated test cases.
 */
@Author(name = "Qualitrix")
@Guice(modules = InjectorModule.class)
public class InfinitumTest {
    @Inject
    protected ConfigurationService configurationService;

    @Inject
    private LoggingService loggingService;

    private Logger logger;

    /**
     * Creates components common to all tests.
     */
    @BeforeClass
    public void setup() {
        logger = loggingService.getLogger(getClass());
    }

    /**
     * Tests that configuration information for the project can be read from
     * an external source.
     */
    @Test
    public void testConfigurationAvailable() {
        assertNotNull(configurationService);
        assertTrue(configurationService.isAvailable());
    }

    /**
     * Tests that log messages can be generated while running tests.
     */
    @Test
    public void testLogger() {
        assertNotNull(logger);
    }

    /**
     * Tests that informational messages can be generated from tests and
     * written to a test report.
     */
    @Test(dataProvider = Injector.REPORTER
        , dataProviderClass = Injector.class
        , testName = "testReporter")
    public void testReporter(final Reporter reporter) {
        assertNotNull(reporter);
    }
}
