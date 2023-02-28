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

import java.util.ServiceLoader;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Locates service implementations using Java Service Provider Interface (SPI)
 * mechanism.
 */
public abstract class ServiceLocator {
    /**
     * Gets all available implementations of a service of a specified type.
     * Uses Java SPI to find and instantiate all available implementations of
     * the specified type. The order is not deterministic and is dependent
     * entirely on the internal implementation of SPI in the Java runtime in
     * use.
     *
     * @param type The type of service to find.
     * @param <T> The type of service.
     *
     * @return All instances of the specified type if found.
     */
    protected final <T extends Service> Iterable<T> getAllAvailable(final Class<T> type) {
        // Use Java SPI to find available service implementations.
        return StreamSupport.stream(ServiceLoader.load(type).spliterator(), false)
                            .filter(Service::isAvailable)
                            .collect(Collectors.toList());
    }

    /**
     * Gets the first available implementation of a service of specified type.
     * Uses Java SPI to find and instantiate all available implementations of
     * the specified type and selects the <b>first</b> one that indicates it is
     * available for use. In order to determine the <b>first</b> available
     * implementation of the service, the implementations are first checked
     * for their availability by invoking {@link Service#isAvailable()} on each
     * implementation and then arranging the matching implementations in
     * increasing order of priority, as determined by the return value of
     * {@link Service#getPriority()}.
     *
     * @param type The type of service to find.
     * @param <T> The type of service.
     *
     * @return An instance of the specified type if one is found, instantiated
     * successfully and indicates it is available for use.
     */
    protected final <T extends Service> T getFirstAvailable(final Class<T> type) {
        // Use Java SPI to find available service implementations.
        final Iterable<T> loader = getAllAvailable(type);

        // Return the first implementation that indicates it is currently
        // available for use.
        return StreamSupport.stream(loader.spliterator(), false)
                            .min((a, b) -> Integer.compare(b.getPriority(), a.getPriority()))
                            .orElse(null);
    }
}
