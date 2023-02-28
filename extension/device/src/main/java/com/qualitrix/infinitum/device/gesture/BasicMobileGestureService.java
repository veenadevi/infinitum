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

import com.qualitrix.infinitum.device.driver.DeviceDriverServiceLocator;
import com.qualitrix.infinitum.device.driver.MobileDriverService;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;

import java.util.Optional;

/**
 * Handles user gestures on a mobile device.
 */
public class BasicMobileGestureService
    extends BaseGestureService<MobileDriver<MobileElement>, MobileElement>
    implements MobileGestureService {
    /**
     * Creates a gesture service for a mobile device, with an auto-discovered
     * driver.
     */
    public BasicMobileGestureService() {
        this(DeviceDriverServiceLocator.getInstance().getMobileDriverService());
    }

    /**
     * Creates a gesture service for a mobile device using a service that
     * can be used for loading a driver appropriate for running tests.
     *
     * @param mobileDriverService A service that can be used for loading a
     * driver appropriate for running tests.
     */
    BasicMobileGestureService(final MobileDriverService mobileDriverService) {
        super(mobileDriverService::getDriver);
    }

    /**
     * Performs a double-tap on an element.
     *
     * @param element The element to double-tap.
     *
     * @return {@code true} if double-tap is performed successfully,
     * {@code false} if it fails or an error occurs while doing so.
     */
    @Override
    public boolean doubleTap(final MobileElement element) {
        try {
            Optional.ofNullable(element)
                    .ifPresent(target -> getTouchAction().tap(new TapOptions()
                                                                  .withElement(ElementOption.element(target))
                                                                  .withTapsCount(2))
                                                         .perform());

            return true;
        }
        catch (final Exception e) {
            return false;
        }
    }

    /**
     * Gets a handler for performing touch actions on the mobile device.
     *
     * @return A {@link TouchAction}.
     */
    protected TouchAction<?> getTouchAction() {
        return new TouchAction<>(getDriver());
    }
}
