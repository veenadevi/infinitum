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

package com.qualitrix.infinitum.data.json;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.qualitrix.infinitum.data.DataFormat;
import com.qualitrix.infinitum.data.DataReader;
import com.qualitrix.infinitum.logging.Logger;
import com.qualitrix.infinitum.logging.LoggingServiceLocator;
import com.qualitrix.infinitum.util.ClasspathUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * <p>
 * Reads structured data from JSON files.
 * </p>
 *
 * <p>
 * Input data are read from files that must be readable and available on the
 * runtime application classpath.
 * </p>
 */
class JSONDataReader implements DataReader {
    private static final List<DataFormat> FORMATS = Collections.singletonList(JavascriptDataFormat.JSON);

    private static final Logger LOGGER = LoggingServiceLocator.getInstance().getLoggingService().getLogger(JSONDataReader.class);

    static final JSONDataReader INSTANCE = new JSONDataReader();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataFormat> getSupportedFormats() {
        return FORMATS;
    }

    /**
     * <p>
     * Reads data from a file and performs conversion to objects of required
     * type. Some examples are given below:
     * </p>
     *
     * <ol>
     *     <li>{@code read("Products.json")} will look for a file named
     *     {@code Products.json} on the application's runtime classpath and read
     *     data from it if the file exists, is readable and is in JSON format.</li>
     *     <li>{@code read("file:///var/etc/customers.json")} will look for a
     *     file named {@code customers.json} under the folder {@code /var/etc/}
     *     on the filesystem and read data from it if the file exists, is
     *     readable and is in JSON format.</li>
     * </ol>
     *
     * <p>
     * The file must contain a JSON array and each element of the array must
     * map to a Java class.
     * </p>
     *
     * @param fileName The name of the file from which data must be read. The
     * file must be readable and available on the runtime classpath.
     * @param type The type of objects to read.
     * @param <T> The type of data to read.
     *
     * @return A {@link List} of objects containing data read from the specified
     * file. The returned list is never {@code null}. If the specified source
     * is not found, cannot be read by this reader, contains data unsuitable
     * for this reader, or is empty, the returned list will be empty. Otherwise,
     * it will contain objects matching the data read from the source.
     */
    @Override
    public <T> List<T> read(final String fileName, final Class<T> type) {
        // Attempt to load the specified file.
        try (final InputStream stream = ClasspathUtil.getResourceStream(fileName)) {
            if (stream == null) {
                throw new FileNotFoundException(String.format("File [%s] not found.", fileName));
            }

            // Prepare to read data from the file.
            try (final Reader reader = new InputStreamReader(stream)) {
                // Read data from the file in buffered mode for best
                // read performance.
                try (final Reader bufferedReader = new BufferedReader(reader)) {
                    final Gson gson = new Gson();

                    return StreamSupport.stream(JsonParser.parseReader(bufferedReader)
                                                          .getAsJsonArray()
                                                          .spliterator(), false)
                                        .map(element -> gson.fromJson(element, type))
                                        .collect(Collectors.toList());
                }
            }
        }
        catch (final Exception e) {
            LOGGER.error(e, String.format("Unable to read JSON data from [%s].", fileName));
        }

        return Collections.emptyList();
    }
}
