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

package com.qualitrix.infinitum.data.delimited;

import com.qualitrix.infinitum.data.DataFormat;
import com.qualitrix.infinitum.data.DataReader;
import com.qualitrix.infinitum.logging.Logger;
import com.qualitrix.infinitum.logging.LoggingServiceLocator;
import com.qualitrix.infinitum.util.ClasspathUtil;
import com.univocity.parsers.common.processor.BeanListProcessor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * Reads structured data from delimited text formats like comma-separated values
 * (CSV), tab-separated values (TSV) or fixed-width values. Delimited text
 * formats are useful for presenting data in a tabular form, which makes the
 * data easy to read and easy to parse.
 * </p>
 *
 * <p>
 * Input data are read from files that must be readable and available on the
 * runtime application classpath.
 * </p>
 */
abstract class DelimitedDataReader implements DataReader {
    private static final Logger LOGGER = LoggingServiceLocator.getInstance()
                                                              .getLoggingService()
                                                              .getLogger(DelimitedDataReader.class);

    private final List<DataFormat> format;

    /**
     * Creates a reader for reading data stored in a particular delimited
     * format.
     *
     * @param format The format for the data to read.
     */
    DelimitedDataReader(final DataFormat format) {
        this.format = Collections.singletonList(format);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataFormat> getSupportedFormats() {
        return format;
    }

    /**
     * <p>
     * Reads data from a file and performs conversion to objects of required
     * type. Some examples are given below:
     * </p>
     *
     * <ol>
     *     <li>{@code read("Products.csv")} will look for a file named
     *     {@code Products.csv} on the application's runtime classpath and read
     *     data from it if the file exists, is readable and contains delimited
     *     data.</li>
     *     <li>{@code read("file:///var/etc/customers.txt")} will look for a
     *     file named {@code customers.txt} under the folder {@code /var/etc/}
     *     on the filesystem and read data from it if the file exists, is
     *     readable and contains delimited data.</li>
     * </ol>
     *
     * @param fileName The name of the file from which data must be read. The
     * file must be readable and available on the runtime classpath.
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
                    final BeanListProcessor<T> processor = new BeanListProcessor<>(type);

                    return read(bufferedReader, processor);
                }
            }
        }
        catch (final Exception e) {
            LOGGER.error(e, String.format("Unable to read delimited data from [%s].", fileName));
        }

        return Collections.emptyList();
    }

    /**
     * Reads data from a delimited file and performs conversion to objects of
     * required type.
     *
     * @param reader A reader for reading data from the delimited file.
     * @param processor A processor for converting data into Java objects.
     * @param <T> The type of data to read.
     *
     * @return A {@link List} of objects containing data read from the specified
     * name. The returned list is never {@code null}. If the specified source
     * is not found, cannot be read by this reader, contains data unsuitable
     * for this reader, or is empty, the returned list will be empty. Otherwise,
     * it will contain objects matching the data read from the source.
     */
    abstract <T> List<T> read(final Reader reader, final BeanListProcessor<T> processor);
}
