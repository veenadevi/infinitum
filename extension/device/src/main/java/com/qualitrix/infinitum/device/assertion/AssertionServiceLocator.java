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

import com.qualitrix.infinitum.ServiceLocator;
import com.qualitrix.infinitum.logging.Logger;
import com.qualitrix.infinitum.logging.LoggingServiceLocator;

import java.util.Optional;

/**
 * Provides {@link AssertionService}s in an implementation-agnostic manner.
 * Automatically detects possible ways of asserting common conditions and uses
 * the first one that indicates it is available.
 */
public class AssertionServiceLocator
    extends ServiceLocator {
    private static final Logger LOGGER = LoggingServiceLocator.getInstance()
                                                              .getLoggingService()
                                                              .getLogger(AssertionServiceLocator.class);

    private final APIAssertionService apiAssertionService;

    private final MobileAssertionService mobileAssertionService;

    private final WebAssertionService webAssertionService;

    /**
     * Loads {@link AssertionService}s using Java Service Provider Interface
     * (SPI).
     */
    private AssertionServiceLocator() {
        apiAssertionService = getFirstAvailable(APIAssertionService.class);

        Optional.ofNullable(apiAssertionService)
                .ifPresent(service -> LOGGER.debug(String.format("%s selected as APIAssertionService."
                    , service.getClass().getName())));

        mobileAssertionService = getFirstAvailable(MobileAssertionService.class);

        Optional.ofNullable(mobileAssertionService)
                .ifPresent(service -> LOGGER.debug(String.format("%s selected as MobileAssertionService."
                    , service.getClass().getName())));

        webAssertionService = getFirstAvailable(WebAssertionService.class);

        Optional.ofNullable(webAssertionService)
                .ifPresent(service -> LOGGER.debug(String.format("%s selected as WebAssertionService."
                    , service.getClass().getName())));
    }

    /**
     * Gets an instance of this class.
     *
     * @return An {@link AssertionServiceLocator}.
     */
    public static AssertionServiceLocator getInstance() {
        return SingletonHolder.SINGLETON;
    }

    /**
     * <p>
     * Gets a service that can be used for performing assertions while testing
     * an API. Never returns {@code null}.
     * </p>
     *
     * <p>
     * Clients should obtain an API assertions service by calling
     * {@code AssertionServiceLocator.getInstance().getAPIAssertionService()}.
     * </p>
     *
     * @return An {@link APIAssertionService}.
     */
    public APIAssertionService getAPIAssertionService() {
        return apiAssertionService;
    }

    /**
     * <p>
     * Gets a service that can be used for performing assertions on a mobile
     * device. Never returns {@code null}.
     * </p>
     *
     * <p>
     * Clients should obtain a mobile assertions service by calling
     * {@code AssertionServiceLocator.getInstance().getMobileAssertionService()}.
     * </p>
     *
     * @return A {@link MobileAssertionService}.
     */
    public MobileAssertionService getMobileAssertionService() {
        return mobileAssertionService;
    }

    /**
     * <p>
     * Gets a service that can be used for performing assertions on a web
     * browser. Never returns {@code null}.
     * </p>
     *
     * <p>
     * Clients should obtain a web assertions service by calling
     * {@code AssertionServiceLocator.getInstance().getWebAssertionService()}.
     * </p>
     *
     * @return A {@link WebAssertionService}.
     */
    public WebAssertionService getWebAssertionService() {
        return webAssertionService;
    }

    /**
     * Holds a singleton instance of the service locator.
     */
    private static final class SingletonHolder {
        private static final AssertionServiceLocator SINGLETON = new AssertionServiceLocator();
    }
}
