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

import com.qualitrix.infinitum.device.driver.DeviceDriverServiceLocator;
import com.qualitrix.infinitum.device.driver.WebDriverService;
import com.qualitrix.infinitum.util.StringUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.function.Supplier;

/**
 * Provides common assertions for testing web applications.
 */
public class BasicWebAssertionService
    extends BaseAssertionService
    implements WebAssertionService {
    private static final String ATTRIBUTE_TYPE = "type";

    private static final String ATTRIBUTE_VALUE_HIDDEN = "HIDDEN";

    private static final String ATTRIBUTE_VALUE_RADIO = "RADIO";

    private static final String ELEMENT_TYPE_INPUT = "INPUT";

    private static final String ELEMENT_TYPE_IMAGE = "IMG";

    private final Supplier<WebDriver> driverSupplier;

    /**
     * Creates an assertion service for a web application, with an
     * auto-discovered driver service.
     */
    public BasicWebAssertionService() {
        this(DeviceDriverServiceLocator.getInstance().getWebDriverService());
    }

    /**
     * Creates an assertion service for a web application.
     *
     * @param webDriverService A service that can be used for loading a driver
     * appropriate for running tests.
     */
    BasicWebAssertionService(final WebDriverService webDriverService) {
        this.driverSupplier = webDriverService::getDriver;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean assertCookieExists(final String name) {
        return StringUtil.isNotBlank(name)
            && getDriver().manage()
                          .getCookieNamed(name) != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean assertHiddenInput(final WebElement element) {
        return assertInputType(element, ATTRIBUTE_VALUE_HIDDEN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean assertRadio(final WebElement element) {
        return assertInputType(element, ATTRIBUTE_VALUE_RADIO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean assertImage(final WebElement element) {
        return assertTag(element, ELEMENT_TYPE_IMAGE);
    }

    /**
     * Gets a driver for running tests.
     *
     * @return A driver for running tests.
     */
    WebDriver getDriver() {
        return driverSupplier.get();
    }

    /**
     * Asserts that an element is an input of a specified type, e.g. text box,
     * hidden, radio button, checkbox, etc.
     *
     * @param element The element to check.
     * @param type The expected type of the element.
     *
     * @return {@code true} if the element is an input and is of the specified
     * type, {@code false} otherwise.
     */
    private boolean assertInputType(final WebElement element, final String type) {
        return assertTag(element, ELEMENT_TYPE_INPUT)
            && type.equalsIgnoreCase(element.getAttribute(ATTRIBUTE_TYPE));
    }

    /**
     * Asserts that an element has a specified tag.
     *
     * @param element The element to check.
     * @param tagName The name of the tag that the element must have.
     *
     * @return {@code true} if the element has the specified tag,
     * {@code false} otherwise.
     */
    private boolean assertTag(final WebElement element, final String tagName) {
        return element != null
            && tagName.equalsIgnoreCase(element.getTagName());

    }
}
