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

import com.aventstack.extentreports.ExtentTest;
import com.qualitrix.infinitum.UnitTest;
import org.mockito.ArgumentCaptor;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;

/**
 * Unit tests for {@link ExtentReporter}.
 */
public class ExtentReporterTest implements UnitTest {
    private ExtentTest backend;

    private ArgumentCaptor<String> messageCaptor;

    private ExtentReporter subject;

    /**
     * Initializes an {@link ExtentTest} to which reporting messages can be
     * written.
     */
    @BeforeMethod
    public void setup() {
        backend = mock(ExtentTest.class);

        messageCaptor = ArgumentCaptor.forClass(String.class);

        subject = new ExtentReporter(backend);
    }

    /**
     * Tests that messages can be reported under a particular category.
     */
    @Test
    public void testAssignCategory() {
        final String category = getString(),
            message = getString();
        subject.assignCategory(category).info(message);

        verify(backend).info(messageCaptor.capture());

        // Ensure that the message got written successfully.
        assertEquals(message, messageCaptor.getValue());
    }

    /**
     * Tests that messages can be reported for a particular device.
     */
    @Test
    public void testAssignDevice() {
        final String device = getString(),
            message = getString();
        subject.assignDevice(device).info(message);

        verify(backend).info(messageCaptor.capture());

        // Ensure that the message got written successfully.
        assertEquals(message, messageCaptor.getValue());
    }

    /**
     * Tests that an error message can be written to an Extent report.
     */
    @Test
    public void testError() {
        // Write a random error message to the report.
        final String message = getString();
        subject.error(message);

        verify(backend).error(messageCaptor.capture());

        // Ensure that the message got written successfully.
        assertEquals(message, messageCaptor.getValue());
    }

    /**
     * Tests that a failure message can be written to an Extent report.
     */
    @Test
    public void testFail() {
        // Write a random failure message to the report.
        final String message = getString();
        subject.fail(message);

        verify(backend).fail(messageCaptor.capture());

        // Ensure that the message got written successfully.
        assertEquals(message, messageCaptor.getValue());
    }

    /**
     * Tests that an informational message can be written to an Extent report.
     */
    @Test
    public void testInfo() {
        // Write a random informational message to the report.
        final String message = getString();
        subject.info(message);

        verify(backend).info(messageCaptor.capture());

        // Ensure that the message got written successfully.
        assertEquals(message, messageCaptor.getValue());
    }

    /**
     * Tests that a pass message can be written to an Extent report.
     */
    @Test
    public void testPass() {
        // Write a random pass message to the report.
        final String message = getString();
        subject.pass(message);

        verify(backend).pass(messageCaptor.capture());

        // Ensure that the message got written successfully.
        assertEquals(message, messageCaptor.getValue());
    }
}
