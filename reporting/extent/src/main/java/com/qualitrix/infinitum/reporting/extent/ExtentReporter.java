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

package com.qualitrix.infinitum.reporting.extent;

import com.aventstack.extentreports.ExtentTest;
import com.qualitrix.infinitum.reporting.Reporter;

import java.util.Optional;

/**
 * Writes reporting messages to an Extent report.
 */
final class ExtentReporter implements Reporter {
    private final ExtentTest backend;

    /**
     * Creates a reporter for writing reporting messages to {@link ExtentTest}.
     *
     * @param backend An {@link ExtentTest}.
     */
    ExtentReporter(final ExtentTest backend) {
        this.backend = backend;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Reporter assignCategory(final String category) {
        Optional.ofNullable(category)
                .ifPresent(backend::assignCategory);

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Reporter assignDevice(final String device) {
        Optional.ofNullable(device)
                .ifPresent(backend::assignDevice);

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(final String message) {
        backend.error(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fail(final String message) {
        backend.fail(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(final String message) {
        backend.info(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pass(final String message) {
        backend.pass(message);
    }
}