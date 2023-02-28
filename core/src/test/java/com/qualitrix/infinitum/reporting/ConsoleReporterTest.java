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

import java.io.OutputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link ConsoleReporter}.
 */
@Author(name = "Qualitrix")
public class ConsoleReporterTest extends PrintStreamReporterTest {
    /**
     * {@inheritDoc}
     */
    @Override
    PrintStreamReporter getReporter(final OutputStream stream) {
        // Set the system console to write to a specified output stream so
        // that reporting messages written to the console can be examined.
        System.setOut(new PrintStream(stream));

        final Test test = mock(Test.class);
        when(test.testName()).thenReturn("getReporter");

        return new ConsoleReporter(test
            , ConsoleReporterTest.class.getAnnotation(Author.class));
    }
}
