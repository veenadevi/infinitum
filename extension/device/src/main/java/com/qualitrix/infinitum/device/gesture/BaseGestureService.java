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

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Handles gestures on a device.
 *
 * @param <D> Type of driver required to run automation tests on the device.
 * @param <E> Type of elements expected on the user interface.
 */
abstract class BaseGestureService<D extends WebDriver, E extends WebElement>
    implements GestureService<E> {
    private static final String JAVASCRIPT_SCROLL_BOTTOM = "window.scrollTo(0, document.body.scrollHeight)";

    private static final String JAVASCRIPT_SCROLL_TOP = "window.scrollTo(0, 0)";

    private static final String JAVASCRIPT_SCROLL_VERTICAL = "window.scroll(0, %d)";

    private final Supplier<D> driverSupplier;

    /**
     * Creates a gesture service.
     *
     * @param driverSupplier Supplies a driver for running tests on the
     * concerned device.
     */
    BaseGestureService(final Supplier<D> driverSupplier) {
        this.driverSupplier = driverSupplier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E findById(final String id) {
        return (E) getDriver().findElement(By.id(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E findByXpath(final String path) {
        return (E) getDriver().findElement(By.xpath(path));
    }

    /**
     * Gets whether a driver is available for this service to perform gestures.
     *
     * @return {@code true} if a driver is available for this service,
     * {@code false} otherwise.
     */
    @Override
    public boolean isAvailable() {
        return true;//getDriver() != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean scrollBottom() {
        return scroll(JAVASCRIPT_SCROLL_BOTTOM);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean scrollDown(final int distance) {
        return scrollVertically(distance);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean scrollTop() {
        return scroll(JAVASCRIPT_SCROLL_TOP);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean scrollUp(final int distance) {
        return scrollVertically(distance);
    }

    /**
     * Gets a driver for running tests on the concerned device.
     *
     * @return A driver for running tests on the concerned device.
     */
    protected D getDriver() {
        return driverSupplier.get();
    }

    /**
     * Gets a {@link JavascriptExecutor} for running Javascript.
     *
     * @return A {@link JavascriptExecutor}.
     */
    JavascriptExecutor getJavascriptExecutor() {
        final D driver = getDriver();

        return Optional.ofNullable(driver)
                       .map(source -> JavascriptExecutor.class.isAssignableFrom(source.getClass()))
                       .orElse(false)
               ? (JavascriptExecutor) driver
               : null;
    }

    /**
     * Performs scroll using Javascript.
     *
     * @param script Javascript for performing scroll.
     *
     * @return {@code true} if scroll is performed successfully, {@code false}
     * if an error occurs.
     */
    boolean scroll(final String script) {
        try {
            return Optional.ofNullable(getJavascriptExecutor())
                           .map(executor -> executor.executeScript(script))
                           .orElse(null) != null;
        }
        catch (final Exception e) {
            return false;
        }
    }

    /**
     * Performs vertical scroll by a specified distance.
     *
     * @param distance The distance to scroll. If the distance is zero, no
     * scroll is performed, if it is greater than zero, scroll is performed
     * in the down direction, and if it is lesser than zero, scroll is performed
     * in the up direction.
     *
     * @return {@code true} if scroll is performed successfully, {@code false}
     * if an error occurs.
     */
    private boolean scrollVertically(final int distance) {
        return scroll(String.format(JAVASCRIPT_SCROLL_VERTICAL, distance));
    }
}
