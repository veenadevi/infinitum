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

package com.qualitrix.infinitum.config;

import com.qualitrix.infinitum.util.ClasspathUtil;

import java.io.File;
import java.net.URL;

/**
 * Reads configuration information from a file available on the runtime
 * application classpath.
 */
public abstract class FileConfigurationService implements ConfigurationService {
    private final File file;

    /**
     * Creates configuration for reading information from a file with a
     * specified name. The specified file must be readable, available on the
     * runtime application classpath and loadable using
     * {@link ClassLoader#getResource(String)}.
     *
     * @param fileName The name of the file to read configuration from - e.g.
     * {@code infinitum.properties}, {@code config.yml}, etc.
     *
     * @throws Exception if the specified file is not found on the runtime
     * of the application classpath, is not readable or cannot be loaded
     * (possibly due to a security constraint).
     */
    protected FileConfigurationService(final String fileName) throws Exception {
        final URL resource = ClasspathUtil.getResource(fileName);

        file = resource != null
               ? new File(resource.toURI())
               : null;
    }

    /**
     * Gets whether the file from which configuration should be read is
     * available for reading configuration.
     *
     * @return {@code true} if the file from which configuration should be read
     * is available on the runtime application classpath and is readable,
     * {@code false} otherwise.
     */
    @Override
    public boolean isAvailable() {
        return getFile() != null
            && getFile().exists()
            && getFile().canRead();
    }

    /**
     * Gets the file to read configuration from.
     *
     * @return A {@link File}.
     */
    protected File getFile() {
        return file;
    }
}
