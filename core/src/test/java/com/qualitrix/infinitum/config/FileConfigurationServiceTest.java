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

import org.testng.annotations.Test;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link FileConfigurationService}.
 */
public abstract class FileConfigurationServiceTest extends ConfigurationServiceTest {
    /**
     * Tests that a non-existent key cannot be read.
     */
    @Test
    public void testContainsKeyWithInvalidKey() {
        assertFalse(getConfiguration().containsKey(getString()));
    }

    /**
     * Tests that a key known to exist in the configuration can be read
     * successfully.
     */
    @Test
    public void testContainsKeyWithValidKey() {
        assertTrue(getConfiguration().containsKey("name"));
    }

    /**
     * Tests that a default decimal value can be obtained if a non-existent key
     * is specified.
     */
    @Test
    public void testGetBigDecimalWithDefault() {
        assertEquals(BigDecimal.TEN, getConfiguration().getBigDecimal(getString(), BigDecimal.TEN));
    }

    /**
     * Tests that a non-existent key cannot be read.
     */
    @Test
    public void testGetBigDecimalWithInvalidKey() {
        assertNull(getConfiguration().getBigDecimal("invalid-bigdecimal"));
    }

    /**
     * Tests that a key known to exist in the configuration with a valid
     * decimal value can be read successfully.
     */
    @Test
    public void testGetBigDecimalWithValidKeyAndValue() {
        assertNotNull(getConfiguration().getBigDecimal("valid-bigdecimal"));
    }

    /**
     * Tests that a default integer value can be obtained if a non-existent key
     * is specified.
     */
    @Test
    public void testGetBigIntegerWithDefault() {
        assertEquals(BigInteger.TEN, getConfiguration().getBigInteger(getString(), BigInteger.TEN));
    }

    /**
     * Tests that a non-existent key cannot be read.
     */
    @Test
    public void testGetBigIntegerWithInvalidKey() {
        assertNull(getConfiguration().getBigInteger("invalid-biginteger"));
    }

    /**
     * Tests that a key known to exist in the configuration with a valid
     * integer value can be read successfully.
     */
    @Test
    public void testGetBigIntegerWithValidKeyAndValue() {
        assertNotNull(getConfiguration().getBigInteger("valid-biginteger"));
    }

    /**
     * Tests that a non-existent key cannot be read.
     */
    @Test
    public void testGetBooleanWithInvalidKey() {
        assertFalse(getConfiguration().getBoolean("invalid-boolean"));
    }

    /**
     * Tests that a key known to exist in the configuration with a valid
     * binary value can be read successfully.
     */
    @Test
    public void testGetBooleanWithValidKeyAndValue() {
        assertTrue(getConfiguration().getBoolean("valid-boolean"));
    }

    /**
     * Tests that a default double value can be obtained if a non-existent key
     * is specified.
     */
    @Test
    public void testGetDoubleWithDefault() {
        assertEquals(1.3d, getConfiguration().getDouble(getString(), 1.3d), 0.0);
    }

    /**
     * Tests that a non-existent key cannot be read.
     */
    @Test
    public void testGetDoubleWithInvalidKey() {
        assertNull(getConfiguration().getDouble("invalid-double"));
    }

    /**
     * Tests that a key known to exist in the configuration with a valid
     * double value can be read successfully.
     */
    @Test
    public void testGetDoubleWithValidKeyAndValue() {
        assertNotNull(getConfiguration().getDouble("valid-double"));
    }

    /**
     * Tests that a default enumeration constant can be obtained if a
     * non-existent key is specified.
     */
    @Test
    public void testGetEnumWithDefault() {
        Arrays.stream(ElectricalSupplyType.values())
              .forEach(state -> assertEquals(state, getConfiguration().getEnum(ElectricalSupplyType.class, getString(), state)));
    }

    /**
     * Tests that a key known to exist in the configuration but with a value
     * that cannot be mapped to an enumeration constant cannot be read
     * successfully.
     */
    @Test
    public void testGetEnumWithInvalidValue() {
        assertNull(getConfiguration().getEnum(ElectricalSupplyType.class, "invalid-supply-type"));
    }

    /**
     * Tests that a key known to exist in the configuration with a valid
     * enumeration constant can be read successfully.
     */
    @Test
    public void testGetEnumWithValidKeyAndValue() {
        assertNotNull(getConfiguration().getEnum(ElectricalSupplyType.class, "valid-supply-type"));
    }

    /**
     * Tests that a default integer value can be obtained if a non-existent key
     * is specified.
     */
    @Test
    public void testGetIntegerWithDefault() {
        assertEquals(new Integer(10), getConfiguration().getInteger(getString(), 10));
    }

    /**
     * Tests that a non-existent key cannot be read.
     */
    @Test
    public void testGetIntegerWithInvalidKey() {
        assertNull(getConfiguration().getInteger("invalid-integer"));
    }

    /**
     * Tests that a key known to exist in the configuration with a valid
     * integer value can be read successfully.
     */
    @Test
    public void testGetIntegerWithValidKeyAndValue() {
        assertNotNull(getConfiguration().getInteger("valid-integer"));
    }

    /**
     * Tests that a default long value can be obtained if a non-existent key is
     * specified.
     */
    @Test
    public void testGetLongWithDefault() {
        assertEquals(new Long(10), getConfiguration().getLong(getString(), 10L));
    }

    /**
     * Tests that a non-existent key cannot be read.
     */
    @Test
    public void testGetLongWithInvalidKey() {
        assertNull(getConfiguration().getLong("invalid-long"));
    }

    /**
     * Tests that a key known to exist in the configuration with a valid
     * long value can be read successfully.
     */
    @Test
    public void testGetLongWithValidKeyAndValue() {
        assertNotNull(getConfiguration().getLong("valid-long"));
    }

    /**
     * Tests that configuration values having keys with the same prefix can be
     * read as a key-value map.
     */
    @Test
    public void testGetMap() {
        final String prefix = "z.y.x";

        getConfiguration().getMap(prefix)
                          .entrySet()
                          .forEach(entry -> {
                              assertNotNull(entry);
                              assertNotNull(entry.getKey());
                              assertNotNull(entry.getValue());
                              assertTrue(entry.getKey().startsWith(prefix));
                          });
    }

    /**
     * Tests that the priority of the service is non-zero.
     */
    @Test
    public void testGetPriority() {
        assertNotEquals(0, getConfiguration().getPriority());
    }

    /**
     * Tests that a default short value can be obtained if a non-existent key is
     * specified.
     */
    @Test
    public void testGetShortWithDefault() {
        assertEquals(new Short((short) 10), getConfiguration().getShort(getString(), (short) 10));
    }

    /**
     * Tests that a non-existent key cannot be read.
     */
    @Test
    public void testGetShortWithInvalidKey() {
        assertNull(getConfiguration().getShort("invalid-short"));
    }

    /**
     * Tests that a key known to exist in the configuration with a valid
     * short value can be read successfully.
     */
    @Test
    public void testGetShortWithValidKeyAndValue() {
        assertNotNull(getConfiguration().getShort("valid-short"));
    }

    /**
     * Tests that a default value can be obtained if a non-existent key is
     * specified.
     */
    @Test
    public void testGetStringWithDefault() {
        assertEquals("default", getConfiguration().getString(getString(), "default"));
    }

    /**
     * Tests that a key with a hierarchical format such as
     * {@code parent.child.grandchild} and known to exist in the configuration
     * can be read successfully.
     */
    @Test
    public void testGetStringWithHierarchicalKey() {
        assertNotNull(getConfiguration().getString("z.y.x.a"));
        assertNotNull(getConfiguration().getString("z.y.x.b"));
        assertNotNull(getConfiguration().getString("z.y.x.c"));
        assertNotNull(getConfiguration().getString("z.y.x.d"));
        assertNotNull(getConfiguration().getString("z.y.x.e"));
    }

    /**
     * Tests that a non-existent key cannot be read.
     */
    @Test
    public void testGetStringWithInvalidKey() {
        assertNull(getConfiguration().getString(getString()));
    }

    /**
     * Tests that a key known to exist in the configuration can be read
     * successfully.
     */
    @Test
    public void testGetStringWithValidKey() {
        assertNotNull(getConfiguration().getString("name"));
    }

    /**
     * Tests that configuration cannot be read when the configuration file is
     * unspecified.
     */
    @Test
    public void testIsAvailableWithNonExistentFile() {
        final File file = mock(File.class);
        when(file.exists()).thenReturn(false);

        final FileConfigurationService subject = mock(FileConfigurationService.class);
        when(subject.getFile()).thenReturn(file);
        when(subject.isAvailable()).thenCallRealMethod();

        assertFalse(subject.isAvailable());
    }

    /**
     * Tests that configuration can be read from a file available on the
     * runtime application classpath.
     */
    @Test
    public void testIsAvailableWithReadableFile() {
        assertTrue(getConfiguration().isAvailable());
    }

    /**
     * Tests that configuration cannot be read from a file not available on the
     * runtime application classpath.
     */
    @Test
    public void testIsAvailableWithUnavailableFile() {
        // Configure the system properties to use a randomly-generated
        // configuration name.
        injectCustomConfigurationName();

        assertFalse(getConfiguration().isAvailable());
    }

    /**
     * Tests that configuration cannot be read when the configuration file is
     * unreadable.
     */
    @Test
    public void testIsAvailableWithUnreadableFile() {
        final File file = mock(File.class);
        when(file.exists()).thenReturn(true);
        when(file.canRead()).thenReturn(false);

        final FileConfigurationService subject = mock(FileConfigurationService.class);
        when(subject.getFile()).thenReturn(file);
        when(subject.isAvailable()).thenCallRealMethod();

        assertFalse(subject.isAvailable());
    }

    /**
     * Gets a {@link ConfigurationService} for running tests.
     *
     * @return A {@link ConfigurationService}.
     */
    protected abstract ConfigurationService getConfiguration();

    /**
     * Represents a type of electrical supply.
     */
    private enum ElectricalSupplyType {
        SINGLE_PHASE, DUAL_PHASE, TRIPLE_PHASE
    }
}
