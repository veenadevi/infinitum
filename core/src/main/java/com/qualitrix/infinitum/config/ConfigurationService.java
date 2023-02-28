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

import com.qualitrix.infinitum.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * Contract for reading configuration from a configuration source. Allows
 * having different ways of storing and retrieving configuration required
 * to initialize and run the application.
 * </p>
 *
 * <p>
 * Only one source of configuration is supported per application. If more than
 * one configuration source is configured deliberately or accidentally for the
 * same application, only one of them will be used at runtime. The actual
 * source used is determined by the priority determined by
 * {@link Service#getPriority()}.
 * </p>
 *
 * <p>
 * Configuration information for the application is generally stored in a
 * file. The full file name is dependent on the type of configuration source.
 * However, regardless of the actual source, the file always has a prefix and
 * a file extension. The file prefix is read with
 * {@link ConfigurationNameProvider}, while the extension is intrinsic to the
 * actual source.
 * </p>
 *
 * <p>
 * The basic method for reading a configuration parameter is
 * {@link #getString(String)}, which accepts the parameter name and returns its
 * value if the configuration source contains a parameter with that name, or
 * {@code null} otherwise. The alternative {@link #getString(String, String)}
 * method attempts to read a parameter and returns a default value specified as
 * the second argument, instead of returning {@code null}.
 * </p>
 *
 * <p>
 * Hierarchical configuration is supported, which means that nested
 * configuration elements can be read as {@code parent.child.grandchild},
 * etc. Implementations must make sure to provide support for hierarchical
 * configuration keys.
 * </p>
 *
 * <p>
 * Also provided are convenience methods for reading data for common types like
 * integer, long, short, double, boolean, etc.
 * </p>
 */
public interface ConfigurationService extends Service {
    /**
     * Gets whether the configuration contains a specified configuration key.
     * For example, calling {@code containsKey("foo")} will return {@code true}
     * if and only if the configuration contains a key named {@code foo}.
     *
     * @param key A configuration key.
     *
     * @return {@code true} if the configuration contains the specified key,
     * {@code false} otherwise.
     */
    boolean containsKey(String key);

    /**
     * Gets a map of configuration values corresponding to keys having the
     * same prefix. For example, let's support the configuration contains keys
     * {@code database.host}, {@code database.port}, {@code database.user}
     * and {@code database.password}. Then, calling {@code getMap("database.")}
     * will return a {@link Map} containing each of these four keys and their
     * corresponding values.
     *
     * @param prefix The common prefix for the configuration keys to find.
     *
     * @return A {@link Map} containing all keys having the specified prefix
     * and their corresponding values.
     */
    Map<String, String> getMap(String prefix);

    /**
     * Gets a string associated with the given configuration key.
     *
     * @param key A configuration key.
     *
     * @return The associated string if key is found, {@code null} otherwise.
     */
    String getString(String key);

    /**
     * Gets a {@link BigDecimal} associated with the given configuration key.
     *
     * @param key A configuration key.
     *
     * @return The associated {@link BigDecimal} if key is found and the value is
     * a valid decimal, {@code null} otherwise.
     */
    default BigDecimal getBigDecimal(final String key) {
        try {
            return new BigDecimal(getString(key));
        }
        catch (final Exception e) {
            return null;
        }
    }

    /**
     * Gets a {@link BigDecimal} associated with the given configuration key or
     * a default value if the key is not found.
     *
     * @param key A configuration key.
     * @param defaultValue The default value.
     *
     * @return The associated {@link BigDecimal} if key is found and the value
     * is a valid decimal, the default value otherwise.
     */
    default BigDecimal getBigDecimal(final String key, final BigDecimal defaultValue) {
        return Optional.ofNullable(getBigDecimal(key))
                       .orElse(defaultValue);
    }

    /**
     * Gets a {@link BigInteger} associated with the given configuration key.
     *
     * @param key A configuration key.
     *
     * @return The associated {@link BigInteger} if key is found and the value
     * is a valid integer, {@code null} otherwise.
     */
    default BigInteger getBigInteger(final String key) {
        try {
            return new BigInteger(getString(key));
        }
        catch (final Exception e) {
            return null;
        }
    }

    /**
     * Gets a {@link BigInteger} associated with the given configuration key or
     * a default value if the key is not found.
     *
     * @param key A configuration key.
     * @param defaultValue The default value.
     *
     * @return The associated {@link BigInteger} if key is found and the value
     * is a valid integer, the default value otherwise.
     */
    default BigInteger getBigInteger(final String key, final BigInteger defaultValue) {
        return Optional.ofNullable(getBigInteger(key))
                       .orElse(defaultValue);
    }

    /**
     * Gets a boolean associated with the given configuration key.
     *
     * @param key A configuration key.
     *
     * @return The associated boolean if key is found and the value is binary,
     * {@code false} otherwise.
     */
    default boolean getBoolean(final String key) {
        return Boolean.parseBoolean(getString(key));
    }

    /**
     * Gets a {@link Double} associated with the given configuration key.
     *
     * @param key A configuration key.
     *
     * @return The associated {@link Double} if key is found and the value is
     * a valid decimal, {@code null} otherwise.
     */
    default Double getDouble(final String key) {
        try {
            return Double.parseDouble(getString(key));
        }
        catch (final Exception e) {
            return null;
        }
    }

    /**
     * Gets a {@link Double} associated with the given configuration key or a
     * default value if the key is not found.
     *
     * @param key A configuration key.
     * @param defaultValue The default value.
     *
     * @return The associated {@link Double} if key is found and the value is
     * a valid decimal, the default value otherwise.
     */
    default Double getDouble(final String key, final Double defaultValue) {
        return Optional.ofNullable(getDouble(key))
                       .orElse(defaultValue);
    }

    /**
     * Gets an enumerated value of a given type, corresponding to a particular
     * enumeration constant.
     *
     * @param enumType The type of enumeration whose value is required.
     * @param key A configuration key.
     * @param <T> The type of enumeration whose value is required.
     *
     * @return The enumeration constant of the specified type if the specified
     * key is found and has a value matching one of the enumeration constants
     * of the specified type, {@code null} otherwise.
     */
    default <T extends Enum<T>> T getEnum(final Class<T> enumType, final String key) {
        try {
            return Enum.valueOf(enumType, getString(key));
        }
        catch (final Exception ignored) {
            return null;
        }
    }

    /**
     * Gets an enumerated value of a given type, corresponding to a particular
     * enumeration constant or a default value.
     *
     * @param enumType The type of enumeration whose value is required.
     * @param key A configuration key.
     * @param defaultValue The default value.
     * @param <T> The type of enumeration whose value is required.
     *
     * @return The enumeration constant of the specified type if the specified
     * key is found and has a value matching one of the enumeration constants
     * of the specified type, the default value otherwise.
     */
    default <T extends Enum<T>> T getEnum(final Class<T> enumType, final String key, final T defaultValue) {
        return Optional.ofNullable(getEnum(enumType, key))
                       .orElse(defaultValue);
    }

    /**
     * Gets an {@link Integer} associated with the given configuration key.
     *
     * @param key A configuration key.
     *
     * @return The associated {@link Integer} if key is found and the value is
     * a valid integer, {@code null} otherwise.
     */
    default Integer getInteger(final String key) {
        try {
            return Integer.parseInt(getString(key));
        }
        catch (final Exception e) {
            return null;
        }
    }

    /**
     * Gets an {@link Integer} associated with the given configuration key or a
     * default value if the key is not found.
     *
     * @param key A configuration key.
     * @param defaultValue The default value.
     *
     * @return The associated {@link Integer} if key is found and the value is
     * a valid integer, the default value otherwise.
     */
    default Integer getInteger(final String key, final Integer defaultValue) {
        return Optional.ofNullable(getInteger(key))
                       .orElse(defaultValue);
    }

    /**
     * Gets a {@link Long} associated with the given configuration key.
     *
     * @param key A configuration key.
     *
     * @return The associated {@link Long} if key is found and the value is
     * a valid long integer, {@code null} otherwise.
     */
    default Long getLong(final String key) {
        try {
            return Long.parseLong(getString(key));
        }
        catch (final Exception e) {
            return null;
        }
    }

    /**
     * Gets a {@link Long} associated with the given configuration key or a
     * default value if the key is not found.
     *
     * @param key A configuration key.
     * @param defaultValue The default value.
     *
     * @return The associated {@link Long} if key is found and the value is
     * a valid long integer, the default value otherwise.
     */
    default Long getLong(final String key, final Long defaultValue) {
        return Optional.ofNullable(getLong(key))
                       .orElse(defaultValue);
    }

    /**
     * Gets a {@link Short} associated with the given configuration key.
     *
     * @param key A configuration key.
     *
     * @return The associated {@link Short} if key is found and the value is
     * a valid short integer, {@code null} otherwise.
     */
    default Short getShort(final String key) {
        try {
            return Short.parseShort(getString(key));
        }
        catch (final Exception e) {
            return null;
        }
    }

    /**
     * Gets a {@link Short} associated with the given configuration key or a
     * default value if the key is not found.
     *
     * @param key A configuration key.
     * @param defaultValue The default value.
     *
     * @return The associated {@link Short} if key is found and the value is
     * a valid short integer, the default value otherwise.
     */
    default Short getShort(final String key, final Short defaultValue) {
        return Optional.ofNullable(getShort(key))
                       .orElse(defaultValue);
    }

    /**
     * Gets a string associated with the given configuration key or a
     * default value if the key is not found. For example, if
     * {@code getString("foo", "bar")} is called, the value associated with the
     * key {@code foo} will be returned if the configuration contains the said
     * key, otherwise the default value specified {@code bar} will be returned.
     *
     * @param key A configuration key.
     * @param defaultValue The default value.
     *
     * @return The associated string if key is found, the default value otherwise.
     */
    default String getString(final String key, final String defaultValue) {
        return Optional.ofNullable(getString(key))
                       .orElse(defaultValue);
    }
}
