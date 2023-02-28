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

import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.temporal.Temporal;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

/**
 * Unit tests for {@link ClassUtil}.
 */
public class ClassUtilTest {
    /**
     * Tests that a JDK proxy can be created when an interface is specified.
     */
    @Test
    public void testCreateDummyProxy() {
        final Temporal subject = ClassUtil.createDummyProxy(Temporal.class);

        assertNotNull(subject);
        assertNotEquals(LocalDate.now(), subject);
        assertEquals(subject.hashCode(), subject.hashCode());
        assertNull(subject.toString());
    }

    /**
     * Tests that a JDK proxy cannot be created when a concrete type is
     * specified.
     */
    @Test
    public void testCreateDummyProxyWithConcreteType() {
        assertNull(ClassUtil.createDummyProxy(String.class));
    }

    /**
     * Tests that a JDK proxy cannot be created when the type is not specified.
     */
    @Test
    public void testCreateDummyProxyWithNull() {
        assertNull(ClassUtil.createDummyProxy(null));
    }
}
