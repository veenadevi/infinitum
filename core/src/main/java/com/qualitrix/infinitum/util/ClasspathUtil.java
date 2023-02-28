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

package com.qualitrix.infinitum.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Optional;

/**
 * Provides methods for working with resources available on the runtime
 * classpath.
 */
public final class ClasspathUtil {
    /**
     * Deliberately hidden to prevent direct instantiation.
     */
    private ClasspathUtil() {
    }

    /**
     * Reads a resource from the runtime classpath.
     *
     * @param path Path to the resource to read, e.g. {@code config.yml},
     * {@code reporting/integration/external/config.xml}, etc.
     *
     * @return A {@link URL} pointing to the resource if it is found on the
     * runtime application classpath, {@code null} otherwise.
     */
    public static URL getResource(final String path) {
        return ClasspathUtil.class.getClassLoader()
                                  .getResource(path);
    }

    /**
     * Reads a resource from the runtime classpath as a file.
     *
     * @param path Path to the resource to read, e.g. {@code config.yml},
     * {@code reporting/integration/external/config.xml}, etc.
     *
     * @return A {@link File} corresponding to the resource if it is found
     * on the runtime application classpath, {@code null} otherwise.
     */
    public static File getResourceFile(final String path) {
        return Optional.ofNullable(getResource(path))
                       .map(ClasspathUtil::toURI)
                       .map(File::new)
                       .orElse(null);
    }

    /**
     * <p>
     * Gets an input stream for reading content of a file whose path has been
     * provided. If the provided path is a fully-qualified filesystem path,
     * a {@link FileInputStream} is returned. Otherwise, the file is searched
     * on the runtime classpath and a resource-specific {@link InputStream} is
     * returned.
     * </p>
     *
     * <p>
     * It is the caller's responsibility to close the returned input stream
     * once data have been read from the stream in order to ensure that system
     * resources are freed correctly.
     * </p>
     *
     * @param path Path to the resource to read, e.g. {@code config.yml},
     * {@code reporting/integration/external/config.xml}, etc.
     *
     * @return An {@link InputStream} for reading the resource if it is found
     * on the runtime application classpath, {@code null} otherwise.
     *
     * @throws FileNotFoundException if the specified file name is a
     * fully-qualified filesystem path but no file exists at the specified
     * location.
     */
    public static InputStream getResourceStream(final String path) throws FileNotFoundException {
        // Prepare to check if the specified file name is a fully-qualified
        // path to a file.
        final File file = new File(path);

        // Return a stream for the file if it was found, otherwise attempt to
        // load the resource from the runtime classpath.
        return file.exists()
               ? new FileInputStream(file)
               : ClasspathUtil.class.getClassLoader()
                                    .getResourceAsStream(path);
    }

    /**
     * Converts a URL to a URI.
     *
     * @param url The URL to convert.
     *
     * @return A URI if the URL corresponds to a valid URI, {@code null}
     * otherwise.
     */
    static URI toURI(final URL url) {
        try {
            return url.toURI();
        }
        catch (final Exception e) {
            return null;
        }
    }
}
