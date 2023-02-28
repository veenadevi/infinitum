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

import com.qualitrix.infinitum.FileAwareUnitTest;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.Test;

import java.net.URISyntaxException;
import java.net.URL;

import static com.qualitrix.infinitum.util.ClasspathUtil.getResource;
import static com.qualitrix.infinitum.util.ClasspathUtil.getResourceFile;
import static com.qualitrix.infinitum.util.ClasspathUtil.getResourceStream;
import static com.qualitrix.infinitum.util.ClasspathUtil.toURI;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.fail;

/**
 * Unit tests for {@link ClasspathUtil}.
 */
@PrepareForTest(URL.class)
public class ClasspathUtilTest
    extends PowerMockTestCase
    implements FileAwareUnitTest {
    /**
     * Tests that a resource known to exist on the runtime application classpath
     * can be loaded successfully.
     */
    @Test
    public void testGetResourceWithKnownPath() {
        assertNotNull(getResource(SAMPLE_FILE_PATH));
    }

    /**
     * Tests that a resource that does not exist on the runtime application
     * classpath cannot be loaded successfully.
     */
    @Test
    public void testGetResourceWithUnknownPath() {
        assertNull(getResource(getRandomPath()));
    }

    /**
     * Tests that a resource known to exist on the runtime application classpath
     * can be loaded successfully.
     */
    @Test
    public void testGetResourceFileWithKnownPath() {
        assertNotNull(getResourceFile(SAMPLE_FILE_PATH));
    }

    /**
     * Tests that a resource that does not exist on the runtime application
     * classpath cannot be loaded successfully.
     */
    @Test
    public void testGetResourceFileWithUnknownPath() {
        assertNull(getResourceFile(getRandomPath()));
    }

    /**
     * Tests that a resource known to exist on the runtime application classpath
     * can be loaded successfully.
     */
    @Test
    public void testGetResourceStreamWithClasspathResource() {
        try {
            assertNotNull(getResourceStream(SAMPLE_FILE_PATH));
        }
        catch (final Exception e) {
            fail(e.getMessage(), e);
        }
    }

    /**
     * Tests that a resource can be loaded successfully by specifying its
     * fully-qualified path on the filesystem.
     */
    @Test
    public void testGetResourceStreamWithFullyQualifiedPath() {
        try {
            // Find the fully-qualified path of the file on the filesystem.
            final URL url = getClass().getClassLoader().getResource(SAMPLE_FILE_PATH);
            final String path = url.getFile();

            assertNotNull(getResourceStream(path));
        }
        catch (final Exception e) {
            fail(e.getMessage(), e);
        }
    }

    /**
     * Tests that a resource that does not exist on the runtime application
     * classpath cannot be loaded successfully.
     */
    @Test
    public void testGetResourceStreamWithUnknownPath() {
        try {
            assertNull(getResourceStream(getRandomPath()));
        }
        catch (final Exception e) {
            fail(e.getMessage(), e);
        }
    }

    /**
     * Tests that a resource having a malformed path cannot be loaded.
     */
    @Test
    public void testToURIWithMalformedResource() throws URISyntaxException {
        final URL url = PowerMockito.mock(URL.class);
        when(url.toURI()).thenThrow(new URISyntaxException("input", "reason"));

        assertNull(toURI(url));
    }

    /**
     * Gets a randomly-generated path.
     *
     * @return A randomly-generated path.
     */
    private String getRandomPath() {
        return String.format("%s/%s/%s/%s.%s"
            , getString()
            , getString()
            , getString()
            , getString()
            , getString());
    }
}
