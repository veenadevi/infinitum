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

import com.qualitrix.infinitum.Service;
import com.qualitrix.infinitum.util.StringUtil;
import org.openqa.selenium.WebElement;

import java.util.Optional;

/**
 * Contract for handling user's interactions with the elements of a user
 * interface such as a web page or an application screen.
 *
 * @param <E> Type of elements expected on the user interface.
 */
public interface GestureService<E extends WebElement>
    extends Service {
    String ATTRIBUTE_INNER_TEXT = "innerText";

    String ATTRIBUTE_VALUE = "value";

    /**
     * Performs click on an element.
     *
     * @param element The element to click.
     *
     * @return {@code true} if the element is clicked successfully,
     * {@code false} if an error occurs.
     */
    default boolean click(final E element) {
        try {
            Optional.ofNullable(element)
                    .ifPresent(WebElement::click);

            return true;
        }
        catch (final Exception e) {
            return false;
        }
    }

    /**
     * Performs click on an element.
     *
     * @param id Unique identifier of the element to click.
     *
     * @return {@code true} if the element is clicked successfully,
     * {@code false} if an error occurs.
     */
    default boolean click(final String id) {
        return click(findById(id));
    }

    /**
     * Checks if a given element exists on the user interface.
     *
     * @param element The element to check.
     *
     * @return {@code true} if the element exists on the user interface,
     * {@code false} otherwise.
     */
    default boolean exists(final E element) {
        return element != null
            && element.isDisplayed();
    }

    /**
     * Checks if an element with a given identifier exists on the user
     * interface.
     *
     * @param id Unique identifier of the element to check.
     *
     * @return {@code true} if an element with the given identifier exists on
     * the user interface, {@code false} otherwise.
     */
    default boolean exists(String id) {
        return exists(findById(id));
    }

    /**
     * Finds an element with a specified identifier.
     *
     * @param id The identifier of the element to find.
     *
     * @return An element with the specified identifier if one exists on the
     * user interface, {@code null} otherwise.
     */
    E findById(String id);

    /**
     * Finds an element using a xpath expression.
     *
     * @param path Xpath expression to use for finding the element.
     *
     * @return An element if one is found using the expression, {@code null}
     * otherwise.
     */
    E findByXpath(String path);

    /**
     * Gets the value of a given element. Examines different attributes of the
     * element such as {@code value} and {@code innerText} to get the value.
     *
     * @param element An element.
     * @param <T> The type of element.
     *
     * @return The element value.
     */
    default <T extends WebElement> String getValue(final T element) {
        return Optional.ofNullable(element)
                       .map(source -> {
                           // Attempt 1: Check the element text.
                           String value = element.getText();

                           if (StringUtil.isNotEmpty(value)) {
                               return value;
                           }

                           // Attempt 2: Check the element value.
                           value = element.getAttribute(ATTRIBUTE_VALUE);

                           if (StringUtil.isNotEmpty(value)) {
                               return value;
                           }

                           // Attempt 2: Check the element inner text.
                           return element.getAttribute(ATTRIBUTE_INNER_TEXT);
                       })
                       .orElse(null);
    }

    /**
     * Performs scroll to the bottom of the current page or screen.
     *
     * @return {@code true} if scroll is performed successfully, {@code false}
     * if an error occurs.
     */
    boolean scrollBottom();

    /**
     * Performs vertical scroll in the down direction by a specified distance.
     *
     * @param distance The distance to scroll in native device units - e.g.
     * pixels on a web page.
     *
     * @return {@code true} if scroll is performed successfully, {@code false}
     * if an error occurs.
     */
    boolean scrollDown(int distance);

    /**
     * Performs scroll to the top of the current page or screen.
     *
     * @return {@code true} if scroll is performed successfully, {@code false}
     * if an error occurs.
     */
    boolean scrollTop();

    /**
     * Performs vertical scroll in the up direction by a specified distance.
     *
     * @param distance The distance to scroll in native device units - e.g.
     * pixels on a web page.
     *
     * @return {@code true} if scroll is performed successfully, {@code false}
     * if an error occurs.
     */
    boolean scrollUp(int distance);

    /**
     * Sets a specified value for an element.
     *
     * @param element The element for which value needs to be set.
     * @param value The value to set.
     * @param <T> The type of element for which value needs to be set.
     *
     * @return {@code true} if the value is set on the element successfully,
     * {@code false} if the value cannot be set or an error occurs while
     * attempting to set the value. When the value is not set, check the
     * element to ensure that it allows its value to be set. For example,
     * text labels (not text input fields) may have fixed values on some devices
     * and may not allow those values to be set so trying to set their values
     * will not work on those devices.
     */
    default <T extends WebElement> boolean setValue(final T element, final String value) {
        try {
            Optional.ofNullable(element)
                    .ifPresent(target -> {
                        target.clear();
                        target.sendKeys(value);
                    });

            return true;
        }
        catch (final Exception e) {
            return false;
        }
    }
}
