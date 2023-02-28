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

import com.qualitrix.infinitum.device.driver.MobileDriverService;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.TapOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertFalse;

/**
 * Unit tests for {@link BasicMobileGestureService}.
 */
public class BasicMobileGestureServiceTest
    extends BaseGestureServiceTest<MobileDriver<MobileElement>, MobileElement> {
    private BasicMobileGestureService subject;

    /**
     * Initializes a gesture service.
     */
    @BeforeMethod
    public void setup() {
        final AppiumDriver<MobileElement> driver = mock(AppiumDriver.class);
        when(driver.executeScript(any())).thenReturn(true);

        final MobileDriverService service = mock(MobileDriverService.class);
        doReturn(driver).when(service).getDriver();

        subject = new BasicMobileGestureService(service);
    }

    /**
     * Tests that an element can be double-tapped.
     */
    @Test
    public void testDoubleTap() {
        final TouchAction<?> action = mock(TouchAction.class);
        doReturn(action).when(action).tap((TapOptions) any());

        final BasicMobileGestureService subject = mock(BasicMobileGestureService.class);
        doReturn(action).when(subject).getTouchAction();
        when(subject.doubleTap(any())).thenCallRealMethod();

        assertTrue(subject.doubleTap(getElement()));
    }

    /**
     * Tests that an error during double-tap on an element is detected
     * correctly.
     */
    @Test
    public void testDoubleTapWithError() {
        final BasicMobileGestureService subject = mock(BasicMobileGestureService.class);
        doThrow(new RuntimeException()).when(subject).getTouchAction();
        when(subject.doubleTap(any())).thenCallRealMethod();

        assertFalse(subject.doubleTap(getElement()));
    }

    /**
     * Tests that the driver can be used to perform touch actions.
     */
    @Test
    public void testGetTouchAction() {
        assertNotNull(getGestureService().getTouchAction());
    }

    /**
     * Tests that scrolling cannot be performed without Javascript support.
     */
    @Test
    public void testScrollWithoutJavascript() {
        final AppiumDriver<MobileElement> driver = mock(AppiumDriver.class);

        final MobileDriverService service = mock(MobileDriverService.class);
        doReturn(driver).when(service).getDriver();

        final BasicMobileGestureService subject = new BasicMobileGestureService(service);

        Assert.assertFalse(subject.scroll(getString()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<MobileElement> getElementType() {
        return MobileElement.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BasicMobileGestureService getGestureService() {
        return subject;
    }
}
