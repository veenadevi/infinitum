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

package com.qualitrix.infinitum.data;

import com.qualitrix.infinitum.UnitTest;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

/**
 * Unit tests for {@link DataReaderServiceLocator}.
 */
public class DataReaderServiceLocatorTest implements UnitTest {
    /**
     * Tests that all formats available for reading data can be retrieved.
     */
    @Test
    public void testGetAvailableFormats() {
        final Set<DataFormat> availableFormats = DataReaderServiceLocator.getInstance().getAvailableFormats();

        assertNotNull(availableFormats);
        assertFalse(availableFormats.isEmpty());
    }

    /**
     * Tests that a service locator instance can be obtained successfully.
     */
    @Test
    public void testGetInstance() {
        for (int i = 0; i < getInt(10, 20); ++i) {
            assertNotNull(DataReaderServiceLocator.getInstance());
        }
    }

    /**
     * Tests that a default test data service is available.
     */
    @Test
    public void testGetTestDataService() {
        final DataReaderService service = DataReaderServiceLocator.getInstance().getDataReaderService(DummyDataReaderService.FORMAT);

        assertNotNull(service);
    }

    /**
     * A dummy (fake) test data reader service.
     */
    public static class DummyDataReaderService implements DataReaderService {
        static final DataFormat FORMAT = DummyDataFormat.DUMMY;

        static final List<DataFormat> FORMATS = Collections.singletonList(FORMAT);

        /**
         * {@inheritDoc}
         */
        @Override
        public DataReader getDataReader(final DataFormat format) {
            return new DummyDataReader();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<DataFormat> getSupportedFormats() {
            return FORMATS;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isAvailable() {
            return true;
        }

        /**
         * A dummy (fake) test data reader.
         */
        private static class DummyDataReader implements DataReader {
            /**
             * {@inheritDoc}
             */
            @Override
            public List<DataFormat> getSupportedFormats() {
                return FORMATS;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public <T> List<T> read(final String source, final Class<T> type) {
                return Collections.emptyList();
            }
        }
    }

    /**
     * A dummy (fake) data format.
     */
    private enum DummyDataFormat implements DataFormat {
        DUMMY
    }
}
