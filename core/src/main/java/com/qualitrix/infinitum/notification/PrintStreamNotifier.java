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

package com.qualitrix.infinitum.notification;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Writes notification messages to a {@link PrintStream}.
 */
abstract class PrintStreamNotifier implements Notifier {
    private final Set<String> recipients;

    private final PrintStream sink;

    /**
     * Creates a notifier for writing reporting messages to a
     * {@link PrintStream}.
     *
     * @param stream A {@link PrintStream}.
     */
    protected PrintStreamNotifier(final PrintStream stream) {
        sink = stream;

        recipients = new HashSet<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Notifier addRecipients(final String... recipients) {
        this.recipients.addAll(Arrays.asList(recipients));

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Notifier clearRecipients() {
        recipients.clear();

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean notify(final String message) {
        if (recipients.isEmpty()) {
            return false;
        }

        final StringBuilder buffer = new StringBuilder();

        buffer.append("[To: ")
              .append(recipients)
              .append("] ")
              .append(message);

        sink.printf("%s%n", buffer);

        return true;
    }
}
