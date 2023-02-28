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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * <p>
 * Reads configuration information from a Java {@code properties} file. By
 * default, configuration is read from a file named
 * {@code infinitum.properties}, which should be available on the runtime
 * classpath of the application. Applications can provide a custom configuration
 * name, if required - see {@link ConfigurationNameProvider} for details on
 * using a custom configuration name instead of the default.
 * </p>
 *
 * <p>
 * In a typical Maven project, the configuration file should be kept under
 * {@code src/main/resources} or {@code src/test/resources}, e.g.
 * {@code src/test/resources/infinitum.properties}. If the file is available on
 * the runtime application classpath, this class gets activated automatically
 * and no additional work is required to read configuration from the said file.
 * </p>
 *
 * <p>
 * Configuration parameters can be read from the file using property names
 * as keys. For example, if the file contains the configuration
 * {@code parent.child.grandchild=configuration-value}, calling
 * {@code configuration.getString("parent.child.grandchild")} will return
 * {@code configuration-value}.
 * </p>
 *
 * <p>
 * This is the preferred implementation of {@code Configuration}, which means
 * that if this implementation is available and other implementations are also
 * available at runtime, this one will still be picked for the application,
 * having precedence over all others.
 * </p>
 */
public class PropertiesConfigurationService extends FileConfigurationService {
    private static final String FILE_EXTENSION = "properties";

    private final Properties source;

    /**
     * Creates configuration for reading information from a
     * {@code .properties} file. The specified file must be readable, available
     * on the runtime application classpath and loadable using
     * {@link ClassLoader#getResource(String)}.
     *
     * @throws Exception if the specified file is not found on the runtime
     * classpath, is not readable or cannot be loaded or cannot be read as a
     * properties file.
     */
    public PropertiesConfigurationService() throws Exception {
        super(String.format("%s.%s"
            , ConfigurationNameProvider.getInstance().getConfigurationName()
            , FILE_EXTENSION));

        source = new Properties();

        if (getFile() != null) {
            try (final FileInputStream fileStream = new FileInputStream(getFile())) {
                try (final BufferedInputStream bufferedStream = new BufferedInputStream(fileStream)) {
                    source.load(bufferedStream);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsKey(final String key) {
        return source.containsKey(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPriority() {
        return Integer.MAX_VALUE + Integer.MIN_VALUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> getMap(final String prefix) {
        return source.keySet()
                     .stream()
                     .filter(Objects::nonNull)
                     .map(Object::toString)
                     .filter(key -> key.startsWith(prefix))
                     .collect(Collectors.toMap(key -> key, source::getProperty));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getString(final String key) {
        return source.getProperty(key);
    }
}
