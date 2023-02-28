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

import java.util.List;

/**
 * Contract for reading structured data.
 */
public interface DataReader {
    /**
     * Gets a list of formats supported by this reader, e.g. {@code CSV}.
     *
     * @return A list of formats supported by this reader.
     */
    List<DataFormat> getSupportedFormats();

    /**
     * Reads data from a named source and performs conversion to objects of
     * required type.
     *
     * @param source The source from where data must be read. This can be a
     * file name, a fully-qualified filesystem path, a URL, etc.
     * @param type The type of objects to read.
     * @param <T> The type of data to read.
     *
     * @return A {@link List} of objects containing data read from the specified
     * name. The returned list is never {@code null}. If the specified source
     * is not found, cannot be read by this reader, contains data unsuitable
     * for this reader, or is empty, the returned list will be empty. Otherwise,
     * it will contain objects matching the data read from the source.
     */
    <T> List<T> read(String source, Class<T> type);
}
