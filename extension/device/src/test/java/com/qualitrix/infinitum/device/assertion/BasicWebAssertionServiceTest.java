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

import com.qualitrix.infinitum.device.driver.WebDriverService;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link BasicWebAssertionService}.
 */
public class BasicWebAssertionServiceTest extends BaseAssertionServiceTest {
    private BasicWebAssertionService subject;

    /**
     * Initializes an assertion service.
     */
    @BeforeMethod
    public void setup() {
        final WebDriver driver = mock(WebDriver.class);

        final WebDriverService service = mock(WebDriverService.class);
        doReturn(driver).when(service).getDriver();

        subject = new BasicWebAssertionService(service);
    }

    /**
     * Tests that a cookie available in the response can be retrieved
     * successfully.
     */
    @Test
    public void testAssertCookieExistsWithExistingCookie() {
        final String cookieName = getString();

        final Options options = mock(Options.class);
        when(options.getCookieNamed(cookieName)).thenReturn(mock(Cookie.class));

        final WebDriver driver = mock(WebDriver.class);
        when(driver.manage()).thenReturn(options);

        final WebDriverService service = mock(WebDriverService.class);
        doReturn(driver).when(service).getDriver();

        final BasicWebAssertionService subject = new BasicWebAssertionService(service);

        assertTrue(subject.assertCookieExists(cookieName));
    }

    /**
     * Tests that a cookie not available in the response cannot be retrieved.
     */
    @Test
    public void testAssertCookieExistsWithNonExistentCookie() {
        final Options options = mock(Options.class);
        when(options.getCookieNamed(any())).thenReturn(null);

        final WebDriver driver = mock(WebDriver.class);
        when(driver.manage()).thenReturn(options);

        final WebDriverService service = mock(WebDriverService.class);
        doReturn(driver).when(service).getDriver();

        final BasicWebAssertionService subject = new BasicWebAssertionService(service);

        assertFalse(subject.assertCookieExists(getString()));
    }

    /**
     * Tests that a cookie cannot be retrieved without specifying its name.
     */
    @Test
    public void testAssertCookieExistsWithoutName() {
        assertFalse(subject.assertCookieExists(null));
    }

    /**
     * Tests that an HTML {@code input} element of type {@code hidden} is
     * identified as a hidden input.
     */
    @Test
    public void testAssertHiddenInputWithHiddenInput() {
        assertTrue(subject.assertHiddenInput(getHiddenInput()));
    }

    /**
     * Tests that an unknown element is not identified as a hidden input.
     */
    @Test
    public void testAssertHiddenInputWithNull() {
        assertFalse(subject.assertHiddenInput(null));
    }

    /**
     * Tests that an HTML {@code input} element of type {@code radio} is
     * not identified as a hidden input.
     */
    @Test
    public void testAssertHiddenInputWithRadioButton() {
        assertFalse(subject.assertHiddenInput(getRadioButton()));
    }

    /**
     * Tests that an HTML {@code select} element is not identified as a hidden
     * input.
     */
    @Test
    public void testAssertHiddenInputWithSelectList() {
        assertFalse(subject.assertHiddenInput(getSelect()), "Element is not a hidden input.");
    }

    /**
     * Tests that an HTML {@code input} element of type {@code text} is not
     * identified as a hidden input.
     */
    @Test
    public void testAssertHiddenInputWithTextInput() {
        assertFalse(subject.assertHiddenInput(getTextInput()), "Element is not a hidden input.");
    }

    /**
     * Tests that an HTML {@code image} element is identified as an image.
     */
    @Test
    public void testAssertImageWithImage() {
        assertTrue(subject.assertImage(getImage()));
    }

    /**
     * Tests that an HTML {@code input} element is not identified as an image.
     */
    @Test
    public void testAssertImageWithInput() {
        assertFalse(subject.assertImage(getTextInput()), "Element is not an image.");
    }

    /**
     * Tests that an unknown element is not identified as an image.
     */
    @Test
    public void testAssertImageWithNull() {
        assertFalse(subject.assertImage(null), "Element is not an image.");
    }

    /**
     * Tests that an unknown element is not identified as a radio button.
     */
    @Test
    public void testAssertRadioWithNull() {
        assertFalse(subject.assertRadio(null));
    }

    /**
     * Tests that an HTML {@code input} element of type {@code radio} is
     * identified as a radio button.
     */
    @Test
    public void testAssertRadioWithRadioButton() {
        assertTrue(subject.assertRadio(getRadioButton()));
    }

    /**
     * Tests that an HTML {@code select} element is not identified as a radio
     * button.
     */
    @Test
    public void testAssertRadioWithSelectList() {
        assertFalse(subject.assertRadio(getSelect()), "Element is not a radio button.");
    }

    /**
     * Tests that an HTML {@code input} element of type {@code text} is not
     * identified as a radio button.
     */
    @Test
    public void testAssertRadioWithTextInput() {
        assertFalse(subject.assertRadio(getTextInput()), "Element is not a radio button.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BaseAssertionService getService() {
        return subject;
    }

    /**
     * Gets a hidden input.
     *
     * @return A hidden input.
     */
    private WebElement getHiddenInput() {
        final WebElement element = mock(WebElement.class);
        when(element.getTagName()).thenReturn("input");
        when(element.getAttribute("type")).thenReturn("hidden");

        return element;
    }

    /**
     * Gets an image.
     *
     * @return An image.
     */
    private WebElement getImage() {
        final WebElement element = mock(WebElement.class);
        when(element.getTagName()).thenReturn("img");

        return element;
    }

    /**
     * Gets a radio button.
     *
     * @return A radio button.
     */
    private WebElement getRadioButton() {
        final WebElement element = mock(WebElement.class);
        when(element.getTagName()).thenReturn("input");
        when(element.getAttribute("type")).thenReturn("radio");

        return element;
    }

    /**
     * Gets a select list.
     *
     * @return A select list.
     */
    private WebElement getSelect() {
        final WebElement element = mock(WebElement.class);
        when(element.getTagName()).thenReturn("select");

        return element;
    }

    /**
     * Gets a text input.
     *
     * @return A text input.
     */
    private WebElement getTextInput() {
        final WebElement element = mock(WebElement.class);
        when(element.getTagName()).thenReturn("input");
        when(element.getAttribute("type")).thenReturn("text");

        return element;
    }
}
