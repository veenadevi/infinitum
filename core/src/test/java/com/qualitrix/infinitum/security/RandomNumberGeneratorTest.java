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

package com.qualitrix.infinitum.security;

import com.qualitrix.infinitum.UnitTest;
import org.testng.annotations.Test;

import java.math.BigInteger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link RandomNumberGenerator}.
 */
public class RandomNumberGeneratorTest implements UnitTest {
    /**
     * Tests that a random {@link BigInteger} cannot be generated with
     * a negative number of bits.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void nextBigIntegerWithNegativeBits() {
        RandomNumberGenerator.nextBigInteger(-1);
    }

    /**
     * Tests that a random {@link BigInteger} can be generated with
     * a positive number of bits.
     */
    @Test
    public void nextBigIntegerWithPositiveBits() {
        assertNotNull(RandomNumberGenerator.nextBigInteger(64));
    }

    /**
     * Tests that a random {@link BigInteger} can be generated with
     * zero bits.
     */
    @Test
    public void testNextBigIntegerWithZeroBits() {
        assertEquals(BigInteger.ZERO, RandomNumberGenerator.nextBigInteger(0));
    }

    /**
     * Tests that a boolean can be generated randomly.
     */
    @Test
    public void testNextBoolean() {
        int falseCount = 0, trueCount = 0;

        // Generate boolean values randomly a large number of times.
        final int count = getInt(1001, 2000);
        for (int i = 0; i < count; ++i) {
            if (RandomNumberGenerator.nextBoolean()) {
                ++trueCount;
            }
            else {
                ++falseCount;
            }
        }

        assertNotEquals(0, falseCount);
        assertNotEquals(0, trueCount);
        assertEquals(count, falseCount + trueCount);
    }

    /**
     * Tests that a random number of bytes can be generated successfully.
     */
    @Test
    public void testNextBytes() {
        final byte[] buffer = new byte[256];

        RandomNumberGenerator.nextBytes(buffer);

        // Ensure that the generated bytes are sufficiently random.
        assertTrue(getStatisticalBitDistribution(buffer) < 0.333333333);
    }

    /**
     * Tests that a double can be generated randomly.
     */
    @Test
    public void testNextDouble() {
        assertNotEquals(RandomNumberGenerator.nextDouble(), RandomNumberGenerator.nextDouble());
    }

    /**
     * Tests that a float can be generated randomly.
     */
    @Test
    public void testNextFloat() {
        assertNotEquals(RandomNumberGenerator.nextFloat(), RandomNumberGenerator.nextFloat());
    }

    /**
     * Tests that an integer can be generated randomly.
     */
    @Test
    public void testNextInt() {
        assertNotEquals(RandomNumberGenerator.nextInt(), RandomNumberGenerator.nextInt());
    }

    /**
     * Tests that an integer up to an upper bound can be generated randomly.
     */
    @Test
    public void testNextIntWithBound() {
        assertNotEquals(RandomNumberGenerator.nextInt(1000000), RandomNumberGenerator.nextInt(1000000));
    }

    /**
     * Tests that a long can be generated randomly.
     */
    @Test
    public void testNextLong() {
        assertNotEquals(RandomNumberGenerator.nextLong(), RandomNumberGenerator.nextLong());
    }

    /**
     * <p>
     * Gets the absolute sum of individual bits of a byte array, using the bit
     * {@code 1} as itself, but replacing the bit {@code 0} with {@code -1}.
     * </p>
     *
     * <p>
     * For a byte array with equal number of zeroes and ones, the monobit sum will
     * be zero, because the zeroes will cancel out the ones. This indicates that
     * the bits are sufficiently random. However, an array with the same byte in
     * all positions, but having a zero monobit sum, could lead to a false
     * conclusion of randomness.
     * </p>
     *
     * @param bytes The byte array for which the monobit sum should be calculated.
     *
     * @return The monobit sum for the specified byte array.
     */
    private int getMonobitSum(final byte[] bytes) {
        int sum = 0;
        for (final byte b : bytes) {
            sum += getMonobitSum(b);
        }

        return Math.abs(sum);
    }

    /**
     * <p>
     * Gets the sum of individual bits of a byte, using the bit {@code 1} as
     * itself, but replacing the bit {@code 0} with {@code -1}.
     * </p>
     *
     * <p>
     * For example, the monobit sum for the byte {@code 10110101} will be
     * {@code 1 + (-1) + 1 + 1 + (-1) + 1 + (-1) + 1 = 1 - 1 + 1 + 1 -1 + 1 - 1 + 1 = 2}.
     * </p>
     *
     * <p>
     * For a byte with equal number of zeroes and ones, the monobit sum will be
     * zero, because the zeroes will cancel out the ones. This indicates that the
     * bits are sufficiently random.
     * </p>
     *
     * @param b The byte for which the monobit sum should be calculated.
     *
     * @return The monobit sum for the specified byte.
     */
    private int getMonobitSum(final byte b) {
        return ((b & 0b00000001) == 1 ? 1 : -1)
            + (((b & 0b00000010) >> 1) == 1 ? 1 : -1)
            + (((b & 0b00000100) >> 2) == 1 ? 1 : -1)
            + (((b & 0b00001000) >> 3) == 1 ? 1 : -1)
            + (((b & 0b00010000) >> 4) == 1 ? 1 : -1)
            + (((b & 0b00100000) >> 5) == 1 ? 1 : -1)
            + (((b & 0b01000000) >> 6) == 1 ? 1 : -1)
            + (((b & 0b10000000) >> 7) == 1 ? 1 : -1);
    }

    /**
     * Gets the statistical reference distribution of bits in an array of bytes.
     * This is computed as {@code s / n}, {@code s} is the absolute value of
     * the monobit sum of bits in the array, and {@code n} is the number of bits
     * in the array (that is, the number of bytes in the array, multiplied by
     * eight).
     *
     * @param bytes The byte array for which the statistical bit distribution
     * should be calculated.
     *
     * @return The statistical bit distribution for the specified byte array.
     */
    private double getStatisticalBitDistribution(final byte[] bytes) {
        return getMonobitSum(bytes) / (8d * bytes.length);
    }
}