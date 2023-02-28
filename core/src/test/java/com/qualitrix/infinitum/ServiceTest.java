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

package com.qualitrix.infinitum;

import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.fail;

/**
 * Unit tests for {@link Service}.
 */
public class ServiceTest implements FileAwareUnitTest {
    /**
     * Tests that a service can be type-casted into a compatible type.
     */
    @Test
    public void testCastWithCompatibleType() {
        final Service service = new SimpleServiceB();

        assertNotNull(service.cast(ServiceA.class));
        assertNotNull(service.cast(ServiceB.class));
    }

    /**
     * Tests that a service cannot be type-casted into an incompatible type.
     */
    @Test
    public void testCastWithIncompatibleType() {
        assertNull(new SimpleServiceA().cast(ServiceC.class));
    }

    /**
     * Tests that a resource known to exist on the runtime application classpath
     * can be loaded successfully.
     */
    @Test
    public void testGetResourceStream() {
        try {
            assertNotNull(new SimpleServiceA().getResourceStream(SAMPLE_FILE_PATH));
        }
        catch (final Exception e) {
            fail(e.getMessage(), e);
        }
    }
}
