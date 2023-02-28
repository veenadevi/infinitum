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

/**
 * Provides utility methods for working with {@link String}s.
 */
public final class StringUtil {
    /**
     * Deliberately hidden to prevent direct instantiation.
     */
    private StringUtil() {
    }

    /**
     * Gets whether a text string is blank, that is, it does not contain any
     * printable characters.
     *
     * @param text The string to validate.
     *
     * @return <code>blank</code> if the string is blank, {@literal false}
     * otherwise.
     */
    public static boolean isBlank(final String text) {
        return (text == null) || "".equals(text.trim());
    }

    /**
     * Gets whether a text string is empty, that is, it does not contain any
     * characters.
     *
     * @param text The string to validate.
     *
     * @return <code>blank</code> if the string is empty, {@literal false}
     * otherwise.
     */
    public static boolean isEmpty(final String text) {
        return (text == null) || "".equals(text);
    }

    /**
     * Gets whether a text string is not blank, that is, it contains at least
     * one printable character.
     *
     * @param text The string to validate.
     *
     * @return <code>blank</code> if the string is not blank, {@literal false}
     * otherwise.
     */
    public static boolean isNotBlank(final String text) {
        return !isBlank(text);
    }

    /**
     * Gets whether a text string is not empty, that is, it contains at least
     * one character.
     *
     * @param text The string to validate.
     *
     * @return <code>blank</code> if the string is not empty, {@literal false}
     * otherwise.
     */
    public static boolean isNotEmpty(final String text) {
        return !isEmpty(text);
    }
}
