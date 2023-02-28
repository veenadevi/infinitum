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

import org.openqa.selenium.WebElement;

/**
 * Contract for asserting common conditions encountered while running a test on
 * a web browser.
 */
public interface WebAssertionService extends AssertionService {
    /**
     * Asserts that a response to a web request contains a cookie with a
     * specified name.
     *
     * @param name The name of the cookie to check.
     */
    boolean assertCookieExists(String name);

    /**
     * Asserts that an element is a hidden input.
     *
     * @param element The element to check.
     * @return {@code true} if the element is a hidden input, {@code false}
     * otherwise.
     */
    boolean assertHiddenInput(WebElement element);

    /**
     * Asserts that an element is a radio button.
     *
     * @param element The element to check.
     * @return {@code true} if the element is a radio button, {@code false}
     * otherwise.
     */
    boolean assertRadio(WebElement element);

    /**
     * Asserts that an element is an image.
     *
     * @param element The element to check.
     * @return {@code true} if the element is an image, {@code false}
     * otherwise.
     *
     */
    boolean assertImage(WebElement element);
}
