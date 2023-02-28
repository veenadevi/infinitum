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

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;

/**
 * Unit tests for {@link ExtentBackendProvider}.
 */
public class ExtentBackendProviderTest {
    private final ExtentBackendProvider subject = new ExtentBackendProvider();

    /**
     * Tests that an Extent Reports backend can be obtained correctly.
     */
    @Test
    public void testGetBackend() {
        assertNotNull(subject.getBackend());
    }

    /**
     * Tests that the Extent Reports configuration file can be loaded
     * successfully.
     */
    @Test
    public void testGetConfiguration() {
        assertNotNull(subject.getConfiguration());
    }

    /**
     * Tests that the Extent Reports configuration file path can be read
     * correctly from the application configuration.
     */
    @Test
    public void testGetConfigurationFilePath() {
        assertNotNull(subject.getConfigurationFilePath());
        assertFalse(subject.getConfigurationFilePath().isEmpty());
    }

    /**
     * Tests that the Extent Reports path can be read correctly from the
     * application configuration.
     */
    @Test
    public void testGetReportPath() {
        assertNotNull(subject.getReportPath());
        assertFalse(subject.getReportPath().isEmpty());
    }
}
