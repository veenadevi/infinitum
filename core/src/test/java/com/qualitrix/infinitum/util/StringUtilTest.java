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

import com.qualitrix.infinitum.UnitTest;
import org.testng.annotations.Test;

import java.util.UUID;

import static com.qualitrix.infinitum.util.StringUtil.isBlank;
import static com.qualitrix.infinitum.util.StringUtil.isEmpty;
import static com.qualitrix.infinitum.util.StringUtil.isNotBlank;
import static com.qualitrix.infinitum.util.StringUtil.isNotEmpty;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link StringUtil}.
 */
public class StringUtilTest implements UnitTest {
    /**
     * Tests to ensure that {@link StringUtil#isBlank(String)} returns
     * {@literal true} with a blank string.
     */
    @Test
    public void testIsBlankWithBlank() {
        assertTrue(isBlank("   "));
    }

    /**
     * Tests to ensure that {@link StringUtil#isBlank(String)} returns
     * {@literal true} with an empty input.
     */
    @Test
    public void testIsBlankWithEmpty() {
        assertTrue(isBlank(""));
    }

    /**
     * Tests to ensure that {@link StringUtil#isBlank(String)} returns
     * {@literal false} with non-blank input.
     */
    @Test
    public void testIsBlankWithNonBlank() {
        assertFalse(isBlank(UUID.randomUUID().toString()));
    }

    /**
     * Tests to ensure that {@link StringUtil#isBlank(String)} returns
     * {@literal true} with {@literal null} input.
     */
    @Test
    public void testIsBlankWithNull() {
        assertTrue(isBlank(null));
    }

    /**
     * Tests to ensure that {@link StringUtil#isEmpty(String)} returns
     * {@literal false} with a blank string.
     */
    @Test
    public void testIsEmptyWithBlank() {
        assertFalse(isEmpty("   "));
    }

    /**
     * Tests to ensure that {@link StringUtil#isEmpty(String)} returns
     * {@literal true} with an empty input.
     */
    @Test
    public void testIsEmptyWithEmpty() {
        assertTrue(isEmpty(""));
    }

    /**
     * Tests to ensure that {@link StringUtil#isEmpty(String)} returns
     * {@literal false} with non-blank input.
     */
    @Test
    public void testIsEmptyWithNonBlank() {
        assertFalse(isEmpty(UUID.randomUUID().toString()));
    }

    /**
     * Tests to ensure that {@link StringUtil#isEmpty(String)} returns
     * {@literal true} with {@literal null} input.
     */
    @Test
    public void testIsEmptyWithNull() {
        assertTrue(isEmpty(null));
    }

    /**
     * Tests to ensure that {@link StringUtil#isNotBlank(String)} returns
     * {@literal false} with a blank string.
     */
    @Test
    public void testIsNotBlankWithBlank() {
        assertFalse(isNotBlank("   "));
    }

    /**
     * Tests to ensure that {@link StringUtil#isNotBlank(String)} returns
     * {@literal false} with an empty input.
     */
    @Test
    public void testIsNotBlankWithEmpty() {
        assertFalse(isNotBlank(""));
    }

    /**
     * Tests to ensure that {@link StringUtil#isNotBlank(String)} returns
     * {@literal true} with non-blank input.
     */
    @Test
    public void testIsNotBlankWithNonBlank() {
        assertTrue(isNotBlank(UUID.randomUUID().toString()));
    }

    /**
     * Tests to ensure that {@link StringUtil#isNotBlank(String)} returns
     * {@literal false} with {@literal null} input.
     */
    @Test
    public void testIsNotBlankWithNull() {
        assertFalse(isNotBlank(null));
    }

    /**
     * Tests to ensure that {@link StringUtil#isNotEmpty(String)} returns
     * {@literal true} with a blank string.
     */
    @Test
    public void testIsNotEmptyWithBlank() {
        assertTrue(isNotEmpty("   "));
    }

    /**
     * Tests to ensure that {@link StringUtil#isNotEmpty(String)} returns
     * {@literal false} with an empty input.
     */
    @Test
    public void testIsNotEmptyWithEmpty() {
        assertFalse(isNotEmpty(""));
    }

    /**
     * Tests to ensure that {@link StringUtil#isNotEmpty(String)} returns
     * {@literal true} with non-blank input.
     */
    @Test
    public void testIsNotEmptyWithNonBlank() {
        assertTrue(isNotEmpty(UUID.randomUUID().toString()));
    }

    /**
     * Tests to ensure that {@link StringUtil#isNotEmpty(String)} returns
     * {@literal false} with {@literal null} input.
     */
    @Test
    public void testIsNotEmptyWithNull() {
        assertFalse(isNotEmpty(null));
    }
}
