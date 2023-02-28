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

package com.qualitrix.infinitum.device.driver;

import com.qualitrix.infinitum.ServiceLocator;
import com.qualitrix.infinitum.logging.Logger;
import com.qualitrix.infinitum.logging.LoggingServiceLocator;

import java.util.Optional;

/**
 * Provides {@link DeviceDriverService}s in an implementation-agnostic manner.
 * Automatically detects possible ways of locating drivers and uses the first
 * one that indicates it is available.
 */
public final class DeviceDriverServiceLocator
    extends ServiceLocator {
    private static final Logger LOGGER = LoggingServiceLocator.getInstance()
                                                              .getLoggingService()
                                                              .getLogger(DeviceDriverServiceLocator.class);

    private final MobileDriverService mobileDriverService;

    private final WebDriverService webDriverService;

    /**
     * Loads {@link DeviceDriverService}s using Java Service Provider
     * Interface (SPI).
     */
    private DeviceDriverServiceLocator() {
        mobileDriverService = getFirstAvailable(MobileDriverService.class);

        Optional.ofNullable(mobileDriverService)
                .ifPresent(service -> LOGGER.debug(String.format("%s selected as MobileDriverService."
                    , service.getClass().getName())));

        webDriverService = getFirstAvailable(WebDriverService.class);

        Optional.ofNullable(webDriverService)
                .ifPresent(service -> LOGGER.debug(String.format("%s selected as WebDriverService."
                    , service.getClass().getName())));
    }

    /**
     * Gets an instance of this class.
     *
     * @return A {@link DeviceDriverServiceLocator}.
     */
    public static DeviceDriverServiceLocator getInstance() {
        return SingletonHolder.SINGLETON;
    }

    /**
     * <p>
     * Gets a service that can be used for locating a driver appropriate for
     * running tests on a mobile device. May return {@code null}, so clients
     * must check the returned value for nullability before using it.
     * </p>
     *
     * <p>
     * Clients should obtain a mobile driver service by calling
     * {@code DeviceDriverServiceLocator.getInstance().getMobileDriverService()}.
     * </p>
     *
     * @return A {@link MobileDriverService}.
     */
    public MobileDriverService getMobileDriverService() {
        return mobileDriverService;
    }

    /**
     * <p>
     * Gets a service that can be used for locating a driver appropriate for
     * running tests on a web browser. May return {@code null}, so clients
     * must check the returned value for nullability before using it.
     * </p>
     *
     * <p>
     * Clients should obtain a web driver service by calling
     * {@code DeviceDriverServiceLocator.getInstance().getWebDriverService()}.
     * </p>
     *
     * @return A {@link WebDriverService}.
     */
    public WebDriverService getWebDriverService() {
        return webDriverService;
    }

    /**
     * Holds a singleton instance of the service locator.
     */
    private static final class SingletonHolder {
        private static final DeviceDriverServiceLocator SINGLETON = new DeviceDriverServiceLocator();
    }
}
