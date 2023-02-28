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

package com.qualitrix.infinitum.data;

import com.qualitrix.infinitum.Service;

import java.util.List;

/**
 * <p>
 * Contract for reading data while running tests.
 * </p>
 *
 * <p>
 * Data might be used for running different test cases. For example, let us
 * say an application requires users to log in before they can use application
 * features. Testing user login functionality may involve then different test
 * cases like valid username + valid password, valid username + invalid
 * password, unknown username, inactive username, and so on. Checking these
 * test cases would then require different combinations of username and
 * password, one for each test case.
 * </p>
 *
 * <p>
 * Data to be read may be available in different formats. However, during
 * testing, it is always useful to have the data available in a strong-typed,
 * consistent format. For instance, considering the example above, data
 * required for testing user login will always include a username and a
 * password, regardless of whether the data are stored in a Microsoft Excel 97
 * workbook, Google Sheets or a text file containing comma-separated values.
 * </p>
 *
 * <p>
 * This contract provides a uniform interface for testers to load test data
 * from different sources.
 * </p>
 */
public interface DataReaderService extends Service {
    /**
     * Gets a reader for reading data available in a given format, as objects
     * of a particular type.
     *
     * @param format The format in which data are available and from which they
     * must be read.
     *
     * @return A reader for reading data. May be {@code null} if the requested
     * format is not supported by this service.
     */
    DataReader getDataReader(DataFormat format);

    /**
     * Gets a list of formats supported by this service, e.g. {@code CSV}.
     *
     * @return A list of formats supported by this service.
     */
    List<DataFormat> getSupportedFormats();
}
