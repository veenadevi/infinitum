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

import com.qualitrix.infinitum.UnitTest;
import org.mockito.ArgumentCaptor;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link BaseGestureService}.
 */
abstract class BaseGestureServiceTest<D extends WebDriver, E extends WebElement>
    implements UnitTest {
    /**
     * Tests that an element can be clicked.
     */
    @Test
    public void testClick() {
        assertTrue(getGestureService().click(getElement()));
    }

    /**
     * Tests that an error encountered on clicking an element is detected
     * correctly.
     */
    @Test
    public void testClickWithError() {
        final E element = getElement();
        doThrow(new RuntimeException()).when(element).click();

        assertFalse(getGestureService().click(element));
    }

    /**
     * Tests that an element can be clicked after locating it with its
     * identifier.
     */
    @Test
    public void testClickWithID() {
        final E element = getElement();

        when(getGestureService().getDriver().findElement(any())).thenReturn(element);

        assertTrue(getGestureService().click(getString()));
    }

    /**
     * Tests that a hidden element is not detected on the user interface.
     */
    @Test
    public void testExistsWithHidden() {
        final E element = getElement();
        when(element.isDisplayed()).thenReturn(false);

        assertFalse(getGestureService().exists(element));
    }

    /**
     * Tests that an element can be detected using its identifier.
     */
    @Test
    public void testExistsWithID() {
        final E element = getElement();
        when(element.isDisplayed()).thenReturn(true);

        when(getGestureService().getDriver().findElement(any())).thenReturn(element);

        assertTrue(getGestureService().exists(getString()));
    }

    /**
     * Tests that an unknown element is not detected on the user interface.
     */
    @Test
    public void testExistsWithNull() {
        assertFalse(getGestureService().exists((E) null));
    }

    /**
     * Tests that a visible element is detected on the user interface.
     */
    @Test
    public void testExistsWithVisible() {
        final E element = getElement();
        when(element.isDisplayed()).thenReturn(true);

        assertTrue(getGestureService().exists(element));
    }

    /**
     * Tests that an element can be located using an xpath expression.
     */
    @Test
    public void testFindByXpath() {
        final E element = getElement();

        when(getGestureService().getDriver().findElement(any())).thenReturn(element);

        assertNotNull(getGestureService().findByXpath(getString()));
    }

    /**
     * Tests that a driver that does not support Javascript cannot be used to
     * execute Javascript operations.
     */
    @Test
    public void testGetJavascriptExecutorWithIncompatibleDriver() {
        final WebDriver driver = mock(WebDriver.class);

        final BaseGestureService<?, ?> subject = mock(BaseGestureService.class);
        doReturn(driver).when(subject).getDriver();
        when(subject.getJavascriptExecutor()).thenCallRealMethod();

        assertNull(subject.getJavascriptExecutor());
    }

    /**
     * Tests that an element's inner text is retrieved as its value.
     */
    @Test
    public void testGetValueWithInnerText() {
        final String value = getString();

        final E element = getElement();
        when(element.getAttribute(GestureService.ATTRIBUTE_INNER_TEXT)).thenReturn(value);

        assertEquals(value, getGestureService().getValue(element));
    }

    /**
     * Tests that an element's text is retrieved as its value.
     */
    @Test
    public void testGetValueWithText() {
        final String value = getString();

        final E element = getElement();
        when(element.getText()).thenReturn(value);

        assertEquals(value, getGestureService().getValue(element));
    }

    /**
     * Tests that an element's value is retrieved as its value.
     */
    @Test
    public void testGetValueWithValue() {
        final String value = getString();

        final E element = getElement();
        when(element.getAttribute(GestureService.ATTRIBUTE_VALUE)).thenReturn(value);

        assertEquals(value, getGestureService().getValue(element));
    }

    /**
     * Tests that a service for handling gestures is available when a driver is
     * available for running tests on the concerned device.
     */
    @Test
    public void testIsAvailable() {
        assertTrue(getGestureService().isAvailable());
    }

    /**
     * Tests that user can scroll to the bottom of the user interface.
     */
    @Test
    public void testScrollBottom() {
        assertTrue(getGestureService().scrollBottom());
    }

    /**
     * Tests that user can scroll down on the user interface.
     */
    @Test
    public void testScrollDown() {
        assertTrue(getGestureService().scrollDown(getInt()));
    }

    /**
     * Tests that user can scroll to the top of the user interface.
     */
    @Test
    public void testScrollTop() {
        assertTrue(getGestureService().scrollTop());
    }

    /**
     * Tests that user can scroll up on the user interface.
     */
    @Test
    public void testScrollUp() {
        assertTrue(getGestureService().scrollUp(-getInt()));
    }

    /**
     * Tests that an error encountered on scrolling is detected correctly.
     */
    @Test
    public void testScrollWithError() {
        final JavascriptExecutor javascriptExecutor = mock(JavascriptExecutor.class);
        doThrow(new RuntimeException()).when(javascriptExecutor).executeScript(any());

        final BaseGestureService<D, E> subject = spy(getGestureService());
        doReturn(javascriptExecutor).when(subject).getJavascriptExecutor();

        assertFalse(subject.scroll(getString()));
    }

    /**
     * Tests that the text value of an element can be set correctly.
     */
    @Test
    public void testSetValue() {
        // Prepare an element.
        final E element = getElement();

        // Prepare an element value.
        final String value = getString();

        // Set the element value.
        assertTrue(getGestureService().setValue(element, value));

        // Prepare to capture the value sent to the element.
        final ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);

        // Ensure that the value was set correctly.
        verify(element).sendKeys(valueCaptor.capture());
        assertEquals(value, valueCaptor.getValue());
    }

    /**
     * Tests that an error encountered while attempting to set an element's
     * value is detected correctly.
     */
    @Test
    public void testSetValueWithError() {
        final E element = getElement();
        doThrow(new RuntimeException()).when(element).sendKeys(any());

        assertFalse(getGestureService().setValue(element, getString()));
    }

    /**
     * Gets an element for running a test.
     *
     * @return An element.
     */
    protected final E getElement() {
        return mock(getElementType());
    }

    /**
     * Gets the type of elements on the user interface under test.
     *
     * @return The type of elements on the user interface under test.
     */
    protected abstract Class<E> getElementType();

    /**
     * Gets a service for running tests.
     *
     * @return A {@link BaseGestureService}.
     */
    protected abstract BaseGestureService<D, E> getGestureService();
}
