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

import com.qualitrix.infinitum.ServiceLocator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Provides access services that can be used for reading data while running
 * tests.
 */
public class DataReaderServiceLocator extends ServiceLocator {
    private static Set<DataFormat> AVAILABLE_FORMATS;

    private static Map<DataFormat, DataReaderService> SERVICES;

    /**
     * Loads all {@link DataReaderService}s using Java Service Provider
     * Interface (SPI).
     */
    private DataReaderServiceLocator() {
        // Load all options available for loading test data.
        final Iterable<DataReaderService> services = getAllAvailable(DataReaderService.class);

        SERVICES = new HashMap<>();
        services.forEach(service -> service.getSupportedFormats()
                                           .forEach(format -> SERVICES.put(format, service)));

        AVAILABLE_FORMATS = SERVICES.keySet();
    }

    /**
     * Gets an instance of this class.
     *
     * @return A {@link DataReaderServiceLocator}.
     */
    public static DataReaderServiceLocator getInstance() {
        return SingletonHolder.SINGLETON;
    }

    /**
     * Gets all formats in which data can be read.
     *
     * @return All formats in which data can be read.
     */
    public Set<DataFormat> getAvailableFormats() {
        return AVAILABLE_FORMATS;
    }

    /**
     * Gets a service for reading test data stored in a specified format.
     *
     * @param format A data format.
     *
     * @return A {@link DataReaderService} if one supporting the specified format
     * is found, {@code null} otherwise.
     */
    public DataReaderService getDataReaderService(final DataFormat format) {
        return SERVICES.getOrDefault(format, null);
    }

    /**
     * Holds a singleton instance of the service locator.
     */
    private static final class SingletonHolder {
        private static final DataReaderServiceLocator SINGLETON = new DataReaderServiceLocator();
    }
}
