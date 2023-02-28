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
import com.qualitrix.infinitum.data.DataReaderService;

import java.util.Arrays;
import java.util.List;

/**
 * Allows reading data from delimited text files available on the runtime
 * classpath.
 */
public class DelimitedDataReaderService implements DataReaderService {
    private static final List<DataFormat> SUPPORTED_FORMATS = Arrays.asList(CommaDelimitedDataReader.FORMAT
        , FixedWidthTextDataReader.FORMAT
        , TabDelimitedDataReader.FORMAT);

    /**
     * Gets a reader for reading data available in delimited text format, as
     * objects of a particular type.
     *
     * @param format The format in which data are available and from which they
     * must be read.
     *
     * @return A reader for reading data. May be {@code null} if the requested
     * format is not supported by this service, or if this service is not
     * capable of reading data into objects of the specified type.
     */
    @Override
    public DataReader getDataReader(final DataFormat format) {
        if (format == null) {
            return null;
        }

        if (DelimitedDataFormat.CSV.equals(format)) {
            return CommaDelimitedDataReader.INSTANCE;
        }

        if (DelimitedDataFormat.TSV.equals(format)) {
            return TabDelimitedDataReader.INSTANCE;
        }

        if (DelimitedDataFormat.TXT.equals(format)) {
            return FixedWidthTextDataReader.INSTANCE;
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataFormat> getSupportedFormats() {
        return SUPPORTED_FORMATS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAvailable() {
        return true;
    }
}
