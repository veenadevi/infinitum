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

package com.qualitrix.infinitum.device.gesture;

import com.qualitrix.infinitum.ServiceLocator;
import com.qualitrix.infinitum.logging.Logger;
import com.qualitrix.infinitum.logging.LoggingServiceLocator;

import java.util.Optional;

/**
 * Provides {@link GestureService}s in an implementation-agnostic manner.
 * Automatically detects possible ways of perfomring gestures and uses the first
 * one that indicates it is available.
 */
public class GestureServiceLocator
    extends ServiceLocator {
    private static final Logger LOGGER = LoggingServiceLocator.getInstance()
                                                              .getLoggingService()
                                                              .getLogger(GestureServiceLocator.class);

    private final MobileGestureService mobileGestureService;

    private final WebGestureService webGestureService;

    /**
     * Loads {@link GestureService}s using Java Service Provider Interface
     * (SPI).
     */
    private GestureServiceLocator() {
        mobileGestureService = getFirstAvailable(MobileGestureService.class);

        Optional.ofNullable(mobileGestureService)
                .ifPresent(service -> LOGGER.debug(String.format("%s selected as MobileGestureService."
                    , service.getClass().getName())));

        webGestureService = getFirstAvailable(WebGestureService.class);

        Optional.ofNullable(webGestureService)
                .ifPresent(service -> LOGGER.debug(String.format("%s selected as WebGestureService."
                    , service.getClass().getName())));
    }

    /**
     * Gets an instance of this class.
     *
     * @return A {@link GestureServiceLocator}.
     */
    public static GestureServiceLocator getInstance() {
        return SingletonHolder.SINGLETON;
    }

    /**
     * <p>
     * Gets a service that can be used for performing gestures on a mobile
     * device. May return {@code null}, so clients must check the returned
     * value for nullability before using it.
     * </p>
     *
     * <p>
     * Clients should obtain a mobile gesture service by calling
     * {@code GestureServiceLocator.getInstance().getMobileGestureService()}.
     * </p>
     *
     * @return A {@link MobileGestureService}.
     */
    public MobileGestureService getMobileGestureService() {
        return mobileGestureService;
    }

    /**
     * <p>
     * Gets a service that can be used for performing gestures on a web
     * browser. May return {@code null}, so clients must check the returned
     * value for nullability before using it.
     * </p>
     *
     * <p>
     * Clients should obtain a web gesture service by calling
     * {@code GestureServiceLocator.getInstance().getWebGestureService()}.
     * </p>
     *
     * @return A {@link WebGestureService}.
     */
    public WebGestureService getWebGestureService() {
        return webGestureService;
    }

    /**
     * Holds a singleton instance of the service locator.
     */
    private static final class SingletonHolder {
        private static final GestureServiceLocator SINGLETON = new GestureServiceLocator();
    }
}
