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

package com.qualitrix.infinitum;

import com.qualitrix.infinitum.security.RandomNumberGenerator;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import static org.mockito.Mockito.RETURNS_DEFAULTS;
import static org.mockito.Mockito.withSettings;

/**
 * Contract and utility methods for unit tests.
 */
public interface UnitTest {
    /**
     * Gets a random BigDecimal between {@code 1} and {@code 10}, both inclusive.
     *
     * @return A random BigDecimal between {@code 1} and {@code 10}.
     */
    default BigDecimal getBigDecimal() {
        return BigDecimal.valueOf(getDouble());
    }

    /**
     * Gets {@code true} or {@code false} randomly, with approximately equal
     * probability.
     *
     * @return {@code true} or {@code false}.
     */
    default boolean getBoolean() {
        return RandomNumberGenerator.nextBoolean();
    }

    /**
     * Gets a random date between {@code January 1, 1 AD} and the current date.
     *
     * @return A random date between {@code January 1, 1 AD} and the current date.
     */
    default Date getDate() {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, getInt(1, calendar.get(Calendar.YEAR)));
        calendar.set(Calendar.DAY_OF_YEAR, getInt(1, calendar.getActualMaximum(Calendar.DAY_OF_YEAR)));

        return calendar.getTime();
    }

    /**
     * Gets a currency that should be nominally treated as the default currency
     * for the application.
     *
     * @return A {@link Currency}.
     */
    default Currency getDefaultCurrency() {
        return Currency.getInstance(getDefaultLocale());
    }

    /**
     * Gets a locale that should be nominally treated as the default locale for
     * the application.
     *
     * @return A {@link Locale}.
     */
    default Locale getDefaultLocale() {
        return new Locale("en", "IN");
    }

    /**
     * Gets a random double value between {@code 1} and {@code 10}, both
     * inclusive.
     *
     * @return A random double value between {@code 1} and {@code 10}.
     */
    default double getDouble() {
        return getDouble(1, 10);
    }

    /**
     * Gets a random double value between a minimum and a maximum value,
     * including the specified maximum and minimum values.
     *
     * @param minimum The minimum value.
     * @param maximum The maximum value.
     *
     * @return A random value between the {@code minimum} and {@code maximum},
     * both inclusive.
     */
    default double getDouble(final double minimum, final double maximum) {
        return minimum + (maximum - minimum + 1) * RandomNumberGenerator.nextDouble();
    }

    /**
     * Gets a random float value between {@code 1} and {@code 10}, both
     * inclusive.
     *
     * @return A random float value between {@code 1} and {@code 10}.
     */
    default float getFloat() {
        return getFloat(1, 10);
    }

    /**
     * Gets a random float value between a minimum and a maximum value,
     * including the specified maximum and minimum values.
     *
     * @param minimum The minimum value.
     * @param maximum The maximum value.
     *
     * @return A random value between the {@code minimum} and {@code maximum},
     * both inclusive.
     */
    default float getFloat(final float minimum, final float maximum) {
        return minimum + (maximum - minimum + 1) * RandomNumberGenerator.nextFloat();
    }

    /**
     * Gets a random integer between {@code 1} and {@code 10}, both inclusive.
     *
     * @return A random integer between {@code 1} and {@code 10}.
     */
    default int getInt() {
        return getInt(1, 10);
    }

    /**
     * Gets a random integer between a minimum and a maximum value, including the
     * specified maximum and minimum values.
     *
     * @param minimum The minimum value.
     * @param maximum The maximum value.
     *
     * @return A random integer between the {@code minimum} and {@code maximum},
     * both inclusive.
     */
    default int getInt(final int minimum, final int maximum) {
        return minimum + RandomNumberGenerator.nextInt(maximum - minimum + 1);
    }

    /**
     * Gets a random long value between {@code 1} and {@link 999,999}, both
     * inclusive.
     *
     * @return A random long value between {@code 1} and {@link 999,999}.
     */
    default long getLong() {
        return getLong(1, 999999);
    }

    /**
     * Gets a random long value between a minimum and a maximum value,
     * including the specified maximum and minimum values.
     *
     * @param minimum The minimum value.
     * @param maximum The maximum value.
     *
     * @return A random value between the {@code minimum} and {@code maximum},
     * both inclusive.
     */
    default long getLong(final long minimum, final long maximum) {
        return getInt((int) minimum, (int) maximum);
    }

    /**
     * Gets a random short value between {@code 1} and {@code 10}, both
     * inclusive.
     *
     * @return A random short value between {@code 1} and {@code 10}.
     */
    default short getShort() {
        return getShort((short) 1, (short) 10);
    }

    /**
     * Gets a random short value between a minimum and a maximum value,
     * including the specified maximum and minimum values.
     *
     * @param minimum The minimum value.
     * @param maximum The maximum value.
     *
     * @return A random value between the {@code minimum} and {@code maximum},
     * both inclusive.
     */
    default short getShort(final short minimum, final short maximum) {
        return (short) (minimum + (maximum - minimum + 1) * RandomNumberGenerator.nextInt());
    }

    /**
     * Gets a {@link String} generated from 128 random bits.
     *
     * @return A randomly generated {@link String}.
     */
    default String getString() {
        return getString(128);
    }

    /**
     * Gets a {@link String} generated from a specified number of random bits.
     *
     * @param bits The number of bits in the {@link String} to generate.
     *
     * @return A randomly generated {@link String}.
     */
    default String getString(final int bits) {
        return RandomNumberGenerator.nextBigInteger(bits).toString(16);
    }

    /**
     * Gets a {@link UUID}.
     *
     * @return A {@link UUID}.
     */
    default UUID getUUID() {
        return UUID.randomUUID();
    }

    /**
     * Creates mock object of given class or interface, ensuring the mocking
     * operation is lenient. Callers <u>cannot invoke</u> private or static
     * methods on the mock object.
     *
     * @param classToMock Class or interface to mock.
     *
     * @return Mock object.
     */
    default <T> T mock(final Class<T> classToMock) {
        return mock(classToMock, RETURNS_DEFAULTS);
    }

    /**
     * Creates mock with a specified strategy for its answers to interactions.
     * Callers <u>cannot invoke</u> private or static methods on the mock
     * object.
     *
     * @param classToMock Class or interface to mock.
     * @param defaultAnswer Default answer for methods that have not been
     * mocked.
     *
     * @return Mock object.
     */
    default <T> T mock(final Class<T> classToMock, final Answer<Object> defaultAnswer) {
        return Mockito.mock(classToMock, withSettings().lenient().defaultAnswer(defaultAnswer));
    }
}
