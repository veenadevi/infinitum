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

package com.qualitrix.infinitum.config.yaml;

import com.qualitrix.infinitum.config.ConfigurationNameProvider;
import com.qualitrix.infinitum.config.FileConfigurationService;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * Reads configuration information from a YAML file. By default, configuration
 * is read from a file named {@code infinitum.yml}, which should be available on
 * the runtime application classpath. Applications can provide a custom
 * configuration name, if required - see {@link ConfigurationNameProvider} for
 * details on using a custom configuration name instead of the default.
 * </p>
 *
 * <p>
 * In a typical Maven project, the configuration file should be kept under
 * {@code src/main/resources} or {@code src/test/resources}, e.g.
 * {@code src/test/resources/infinitum.yml}. If the file is available on the
 * runtime application classpath, this class gets activated automatically
 * and no additional work is required to read configuration from the said file.
 * </p>
 *
 * <p>
 * Configuration parameters can be read from the file using property names
 * as keys. For example, if the file contains the configuration
 * {@code parent.child.grandchild: configuration-value}, calling
 * {@code configuration.getString("parent.child.grandchild")} will return
 * {@code configuration-value}.
 * </p>
 *
 * <p>
 * <b>Note:</b> Requires the <code>SnakeYML</code> library on the runtime
 * classpath.
 * </p>
 */
public class YAMLConfigurationService extends FileConfigurationService {
    private static final String FILE_EXTENSION = "yml";

    private final Map<String, Object> source;

    /**
     * Creates configuration for reading information from a
     * {@code .yml} file. The specified file must be readable, available
     * on the runtime application classpath and loadable using
     * {@link ClassLoader#getResource(String)}.
     *
     * @throws Exception if the specified file is not found on the runtime
     * classpath, is not readable or cannot be loaded or cannot be read as a
     * YAML file.
     */
    public YAMLConfigurationService() throws Exception {
        super(String.format("%s.%s"
            , ConfigurationNameProvider.getInstance().getConfigurationName()
            , FILE_EXTENSION));

        if (getFile() != null) {
            try (final FileInputStream fileStream = new FileInputStream(getFile())) {
                try (final BufferedInputStream bufferedStream = new BufferedInputStream(fileStream)) {
                    // Read the YAML file as a Map of key-value pairs.
                    source = new Yaml().load(bufferedStream);

                    // Flatten the map to support easily accessing parameter values with
                    // hierarchical "a.b.c.d" key format.
                    flatten();
                }
            }
        }
        else {
            source = Collections.emptyMap();
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
    public Map<String, String> getMap(final String prefix) {
        return source.keySet()
                     .stream()
                     .filter(Objects::nonNull)
                     .map(Object::toString)
                     .filter(key -> key.startsWith(prefix))
                     .collect(Collectors.toMap(key -> key
                         , key -> Optional.ofNullable(source.get(key))
                                          .map(Objects::toString)
                                          .orElse(null)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getString(final String key) {
        return Optional.ofNullable(source.get(key))
                       .map(Object::toString)
                       .orElse(null);
    }

    /**
     * <p>
     * Flattens the map of values.
     * </p>
     *
     * <p>
     * In order to understand why this is needed, it is important to remember
     * that YAML supports nested structures. This means,
     * </p>
     *
     * <pre>{@code
     *     a:
     *         b:
     *             c:
     *                 d: value
     * }</pre>
     *
     * <p>
     * gets read as:
     * </p>
     *
     * <pre>{@code
     *    [ "a" = [
     *                "b" = [
     *                          "c" = [ "d" = "value" ]
     *                      ]
     *            ]
     *    ]
     * }</pre>
     *
     * <p>
     * where, {@code [ ... ]} represents a map. This means, the resulting Java
     * representation consists of a nested hierarchy of maps. With this
     * representation, the key "a.b.c.d" will never be found, since no
     * such key exists in the top-level map (only "a" exists in the top-level
     * map).
     * </p>
     *
     * <p>
     * The representation therefore needs to be flattened to support the
     * "a.b.c.d" key format.
     * </p>
     */
    private void flatten() {
        flatten(source, true);
    }

    /**
     * Flattens a map containing nested maps.
     *
     * @param map The map to flatten.
     * @param updateAllowed Whether the provided map should be updated as part
     * of the process, or the flattened entries must be returned in a separate
     * map.
     *
     * @return A flattened version of the map.
     */
    private Map<String, Object> flatten(final Map<String, Object> map, final boolean updateAllowed) {
        final Map<String, Object> result = new LinkedHashMap<>();

        final Set<Map.Entry<String, Object>> entries = new LinkedHashSet<>(map.entrySet());

        // Iterate over the map, preserving the order of map keys.
        for (final Map.Entry<String, Object> entry : entries) {
            final Object element = entry.getValue();

            if (element instanceof Map) {
                // If the current map element is a map itself, flatten it as well.
                final Map<String, Object> flattened = flatten((Map<String, Object>) element, false);

                for (final Map.Entry<String, Object> child : new LinkedHashSet<>(flattened.entrySet())) {
                    if (updateAllowed) {
                        // Update the original map if updates are allowed.
                        map.put(String.format("%s.%s", entry.getKey(), child.getKey()), child.getValue());
                    }
                    else {
                        // Add the flattened element to the result, otherwise.
                        result.put(String.format("%s.%s", entry.getKey(), child.getKey()), child.getValue());
                    }
                }

                // Remove the original element, as it has been flattened.
                map.remove(entry.getKey());
            }
            else {
                // Retain the current element as it is.
                result.put(entry.getKey(), entry.getValue());
            }
        }

        return result;
    }
}
