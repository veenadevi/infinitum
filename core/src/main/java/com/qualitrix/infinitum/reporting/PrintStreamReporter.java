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

package com.qualitrix.infinitum.reporting;

import com.qualitrix.infinitum.annotation.Author;
import org.testng.annotations.Test;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * Writes reporting messages to a {@link PrintStream}.
 */
abstract class PrintStreamReporter implements Reporter {
    private final Collection<String> context;

    private final PrintStream sink;

    private String category;

    private String device;

    /**
     * Creates a reporter for writing reporting messages to a {@link PrintStream}.
     *
     * @param stream A {@link PrintStream}.
     * @param test The test for which the reporting messages should be written.
     * @param author Optional details of the test author.
     */
    protected PrintStreamReporter(final PrintStream stream
        , final Test test
        , final Author author) {
        sink = stream;

        context = new ArrayList<>();

        Optional.ofNullable(test)
                .map(Test::testName)
                .ifPresent(context::add);
        Optional.ofNullable(test)
                .map(Test::description)
                .ifPresent(context::add);
        Optional.ofNullable(author)
                .map(Author::name)
                .ifPresent(context::add);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Reporter assignCategory(final String category) {
        this.category = category;

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Reporter assignDevice(final String device) {
        this.device = device;

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(final String message) {
        write("[ERROR]", message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fail(final String message) {
        write("[FAIL]", message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(final String message) {
        write("[INFO]", message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pass(final String message) {
        write("[PASS]", message);
    }

    /**
     * Writes a message to the print stream, adding a prefix to it.
     *
     * @param prefix The prefix to add to the message.
     * @param message The message to write.
     */
    private void write(final String prefix, final String message) {
        final StringJoiner buffer = new StringJoiner(" ");

        // Add message prefix to the message.
        buffer.add(prefix);

        // Add any contextual information available to the message.
        context.stream()
               .map(value -> String.format("[%s]", value))
               .forEach(buffer::add);

        // Add additional context, if available.
        Optional.ofNullable(category)
                .ifPresent(buffer::add);
        Optional.ofNullable(device)
                .ifPresent(buffer::add);

        // Add the message.
        buffer.add(message);

        // Write the message.
        sink.printf("%s%n", buffer);
    }
}
