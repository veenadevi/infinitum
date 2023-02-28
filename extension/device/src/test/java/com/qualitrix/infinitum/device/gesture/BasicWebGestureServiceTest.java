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

import com.qualitrix.infinitum.device.driver.WebDriverService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

/**
 * Unit tests for {@link BasicWebGestureService}.
 */
public class BasicWebGestureServiceTest
    extends BaseGestureServiceTest<WebDriver, WebElement> {
    private BasicWebGestureService subject;

    /**
     * Initializes a gesture service.
     */
    @BeforeMethod
    public void setup() {
        final RemoteWebDriver driver = mock(RemoteWebDriver.class);
        when(driver.executeScript(any())).thenReturn(true);

        final WebDriverService service = mock(WebDriverService.class);
        doReturn(driver).when(service).getDriver();

        subject = new BasicWebGestureService(service);
    }

    /**
     * Tests that a gesture service can be created if a web driver is available.
     */
    @Test
    public void testConstruct() {
        assertNotNull(new BasicWebGestureService());
    }

    /**
     * Tests that a hidden element is not detected on the user interface.
     */
    @Test
    public void testElementExistsWithHidden() {
        final WebElement element = mock(WebElement.class);
        when(element.isDisplayed()).thenReturn(false);

        assertFalse(getGestureService().exists(element));
    }

    /**
     * Tests that scrolling cannot be performed without Javascript support.
     */
    @Test
    public void testScrollWithoutJavascript() {
        final RemoteWebDriver driver = mock(RemoteWebDriver.class);

        final WebDriverService service = mock(WebDriverService.class);
        doReturn(driver).when(service).getDriver();

        final BasicWebGestureService subject = new BasicWebGestureService(service);

        Assert.assertFalse(subject.scroll(getString()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<WebElement> getElementType() {
        return WebElement.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BasicWebGestureService getGestureService() {
        return subject;
    }
}
