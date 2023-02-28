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

package com.qualitrix.infinitum.data.json;

import com.qualitrix.infinitum.UnitTest;
import com.qualitrix.infinitum.data.DataFormat;
import com.qualitrix.infinitum.data.DataReaderServiceLocator;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link JSONDataReaderService}.
 */
public class JSONDataReaderServiceTest implements UnitTest {
    private final JSONDataReaderService subject = new JSONDataReaderService();

    /**
     * Tests that JSON test data reading service is discovered
     * automatically.
     */
    @Test
    public void testAutoDiscovery() {
        assertTrue(DataReaderServiceLocator.getInstance()
                                           .getAvailableFormats()
                                           .containsAll(subject.getSupportedFormats()));
    }

    /**
     * Tests that a data reader cannot be obtained without specifying the
     * format for the reader.
     */
    @Test
    public void testGetDataReaderWithNullFormat() {
        assertNull(subject.getDataReader(null));
    }

    /**
     * Tests that a data reader can be obtained by specifying a supported
     * format.
     */
    @Test
    public void testGetDataReaderWithSupportedFormat() {
        subject.getSupportedFormats()
               .forEach(format -> assertNotNull(subject.getDataReader(format)));
    }

    /**
     * Tests that a data reader cannot be obtained by specifying an unsupported
     * format.
     */
    @Test
    public void testGetDataReaderWithUnSupportedFormat() {
        assertNull(subject.getDataReader(new DataFormat() {
        }));
    }

    /**
     * Tests that all supported JSON text formats are known.
     */
    @Test
    public void testGetSupportedFormats() {
        final List<DataFormat> formats = subject.getSupportedFormats();

        assertNotNull(formats);
        assertFalse(formats.isEmpty());
    }

    /**
     * Tests that a service is available for reading JSON text data.
     */
    @Test
    public void testIsAvailable() {
        assertTrue(subject.isAvailable());
    }
}
