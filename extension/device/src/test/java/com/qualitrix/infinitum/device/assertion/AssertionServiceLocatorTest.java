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

package com.qualitrix.infinitum.device.assertion;

import com.qualitrix.infinitum.UnitTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

/**
 * Unit tests for {@link AssertionServiceLocator}.
 */
public class AssertionServiceLocatorTest implements UnitTest {
    /**
     * Tests that a service instance can be obtained successfully.
     */
    @Test
    public void testGetInstance() {
        for (int i = 0; i < getInt(10, 20); ++i) {
            assertNotNull(AssertionServiceLocator.getInstance());
        }
    }

    /**
     * Tests that API assertions service can be obtained successfully.
     */
    @Test
    public void testGetAPIAssertionService() {
        assertNotNull(AssertionServiceLocator.getInstance().getAPIAssertionService());
    }

    /**
     * Tests that mobile assertions service can be obtained successfully.
     */
    @Test
    public void testGetMobileAssertionService() {
        assertNotNull(AssertionServiceLocator.getInstance().getMobileAssertionService());
    }

    /**
     * Tests that web assertions service can be obtained successfully.
     */
    @Test
    public void testGetWebAssertionService() {
        assertNotNull(AssertionServiceLocator.getInstance().getWebAssertionService());
    }
}
